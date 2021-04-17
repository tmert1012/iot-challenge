#!/usr/bin/env bash

mosquitto_pub --cafile "./certificates/AmazonRootCA1.pem" --cert "./certificates/thing-for-tmert1012.crt" --key "./certificates/thing-for-tmert1012.key"   -h "a3u38y6be3pa0r-ats.iot.us-east-1.amazonaws.com" -p 8883 -q 1 -i "thing-for-tmert1012" -d --tls-version tlsv1.2 -m "readings test" -t "things/thing-for-tmert1012/readings"

mosquitto_pub --cafile "./certificates/AmazonRootCA1.pem" --cert "./certificates/thing-for-tmert1012.crt" --key "./certificates/thing-for-tmert1012.key"   -h "a3u38y6be3pa0r-ats.iot.us-east-1.amazonaws.com" -p 8883 -q 1 -i "thing-for-tmert1012" -d --tls-version tlsv1.2 -m "diagnostics test" -t "things/thing-for-tmert1012/diagnostics"
