package com.isidorefarm.uploader;

import software.amazon.awssdk.crt.CRT;
import software.amazon.awssdk.crt.CrtRuntimeException;
import software.amazon.awssdk.crt.io.ClientBootstrap;
import software.amazon.awssdk.crt.io.EventLoopGroup;
import software.amazon.awssdk.crt.io.HostResolver;
import software.amazon.awssdk.crt.mqtt.MqttClientConnection;
import software.amazon.awssdk.crt.mqtt.MqttClientConnectionEvents;
import software.amazon.awssdk.crt.mqtt.MqttMessage;
import software.amazon.awssdk.crt.mqtt.QualityOfService;
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Connector {

    private String PATH = "../../certificates/";
    private String clientId = "thing-for-tmert1012";
    private String rootCaPath = PATH + "AmazonRootCA1.pem";
    private String certPath = PATH + "thing-for-tmert1012.crt";
    private String keyPath = PATH + "thing-for-tmert1012.key";
    private String endpoint = "a3u38y6be3pa0r-ats.iot.us-east-1.amazonaws.com";

    /*
     This is basically sample code I thinned out from AWS doc. I tried splitting out the connection as to reuse it for both calls (readings, diagnostics) but it was hanging the application.
     I'm new to this library so I assume the countdown/await logic was still waiting before lazily disconnecting.
     I ended up sticking with the sample code to save time.
     https://github.com/aws/aws-iot-device-sdk-java-v2/blob/main/samples/BasicPubSub/src/main/java/pubsub/PubSub.java
     */

    public Connector(String topic, List<String> items) {

        MqttClientConnectionEvents callbacks = new MqttClientConnectionEvents() {
            @Override
            public void onConnectionInterrupted(int errorCode) {
                if (errorCode != 0) {
                    System.out.println("Connection interrupted: " + errorCode + ": " + CRT.awsErrorString(errorCode));
                }
            }

            @Override
            public void onConnectionResumed(boolean sessionPresent) {
                System.out.println("Connection resumed: " + (sessionPresent ? "existing session" : "clean session"));
            }
        };

        try (EventLoopGroup eventLoopGroup = new EventLoopGroup(1);
            HostResolver resolver = new HostResolver(eventLoopGroup);
            ClientBootstrap clientBootstrap = new ClientBootstrap(eventLoopGroup, resolver);
            AwsIotMqttConnectionBuilder builder = AwsIotMqttConnectionBuilder.newMtlsBuilderFromPath(certPath, keyPath)) {

            if (rootCaPath != null) {
                builder.withCertificateAuthorityFromPath(null, rootCaPath);
            }

            builder.withBootstrap(clientBootstrap)
                    .withConnectionEventCallbacks(callbacks)
                    .withClientId(clientId)
                    .withEndpoint(endpoint)
                    .withCleanSession(true);

            try (MqttClientConnection connection = builder.build()) {

                CompletableFuture<Boolean> connected = connection.connect();
                try {
                    boolean sessionPresent = connected.get();
                    System.out.println("Connected to " + (!sessionPresent ? "new" : "existing") + " session!");
                } catch (Exception ex) {
                    throw new RuntimeException("Exception occurred during connect", ex);
                }

                CountDownLatch countDownLatch = new CountDownLatch(items.size());

                CompletableFuture<Integer> subscribed = connection.subscribe(topic, QualityOfService.AT_LEAST_ONCE, (message) -> {
                    String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                    System.out.println("MESSAGE: " + payload);
                    countDownLatch.countDown();
                });

                subscribed.get();

                for (String i : items) {
                    CompletableFuture<Integer> published = connection.publish(new MqttMessage(topic, i.getBytes(), QualityOfService.AT_LEAST_ONCE, false));
                    published.get();
                    Thread.sleep(1000);
                }

                countDownLatch.await();

                CompletableFuture<Void> disconnected = connection.disconnect();
                disconnected.get();
                System.out.println("connection closed.");

            }

        } catch (CrtRuntimeException | InterruptedException | ExecutionException ex) {
            System.out.println("Exception encountered: " + ex.toString());
        }
    }

}
