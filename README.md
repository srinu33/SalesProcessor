# SalesProcessor

This project is to process the sales and generate a report.
For this i have created SpringBoot application with one POST rest api (/sales) 

Steps to setup the project

1) Clone this project to your local, go to project root directory (salesprocessor).
2) Import ptoject as Existing Maven project to view the code
3) Run the mail class SalesProcessorApplication to start the service

Steps to Test the service:

1) Use any REST client to post a request.
2)Request POST --  http://localhost:8080/sales
3) please use one of the inut json provided in 'testdata' folder.
3) You can see the generated report in the console.
