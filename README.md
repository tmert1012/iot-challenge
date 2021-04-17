## IoT Code Challenge!

## Description
We have a customer that wants to gain insights from their fleet of weather sensors. These sensors currently don't have the ability
to connect to the internet. The sensors do have the ability to log data to `.csv` files, and the customer would like us
to build a tool to read these files, and send data readings and diagnostic data over a secure connection established with AWS IoT.
For this challenge, you will be building this tool.

## Requirements
- Java 8 or greater
- Gradle 5.4 or greater

## Build Instructions
`git clone git@github.com:tmert1012/iot-challenge`  
`cd iot-challenge`   
`gradle build`

Building also runs tests found under src/test. If tests pass, there are no errors. A test failure creates errors and a link to a HTML report.

## Run Instructions
`cd build/libs`  
`java -jar uploader.jar`  

OR with optional params, defaults show below.  

`java -jar uploader.jar --file readings.csv --sampleSize 4 --reportInterval 2`


## Review results in AWS IoT Console
1. In the AWS IoT Core console, select 'Test' in the left navigation list
1. Input the topic to watch 'things/thing-for-tmert1012/readings’ or 'things/thing-for-tmert1012/diagnostics’, start your application and if everything is connected, you will see your data
