### Completed

Completed items.

[UP-1] setup CVS lib to read sample files  
Status: DONE

- load data into basic objects
- handle bad or missing data
- print out results after loading, verify data matches source

[UP-7] add test for CSV file input  
Status: DONE

- tests to verify the Reading class correctly handles validation
- run test on build
- results should show in command line

[UP-3] complete AWS IoT Thing setup  
Status: DONE

- follow steps noted in README.md "Connecting to the AWS IoT Thing" section

[UP-5] implement basic connect to AWS  
Status: DONE

- programatically connect or login to AWS
- print results of successful connection

[UP-2] Provide a user configurable way to set the filename to use as input.  
Status: DONE

- command line option for filename
- verify different files can be loaded
- print results

[UP-8] Provide a user configurable way to set the sample size window  
Status: DONE

- specifies the number of readings included in each diagnostics report
- (Ex. last 4 readings)
- print param when run

[UP-6] report readings to AWS  
Status: DONE

- report all readings in JSON to AWS
- verify reading was accepted
- print results

[UP-4] submit "diagnostics report"  
Status: DONE

- create diagnostics object
- create report based on readings, report interval and sample window size
- submit diagnostics reports to AWS IoT
- print out readings to verify correct diag report algorithm

[UP-10] correct app hanging post AWS upload  
STATUS: DONE
- app is hanging post upload, despite connections being successfully closed
- seeing an error in AWS "Access to the resource ASWIotData is denied", could be related

[UP-9] clean up code and instructions  
Status: NOT STARTED

- move readme to instructions file
- update readme on build and run process
- info on params