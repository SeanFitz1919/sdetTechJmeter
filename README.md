# SDET Developer Tech Interview
Intended for Workday - JR-84312 role

Goal of this was to design, implement and document a complete and scalable automated testing strategy to verify the fucntionality and behaviour
of the following github endpoint. 

https://api.github.com/search/repositories?

Noting Documentation - 
https://developer.github.com/v3/search/#search-repositories

----
##How to perform the tests. 

At the time of writing navigate to the "Scripts" folder and download the startup script according to the testing environment.

If on Linux or mac download the "linuxStartupScript.sh" and if on windows download the "windowsStartupScript.bat".

*Please note due to time contrains the "windowsStartupScript.bat" is not fully functional. Will discuss more regarding intended design
in the design section of this "README.md" file. However the linuxStartupScript is fuctional on mac and linux.

- Linux -
Open terminal and navigate to the folder which contains the linuxStartupScript.sh and run the following:

"chmod +x ./linuxStartupScript.sh; ./linuxStartupScript.sh"

- Windows -
Please open the cmd program in administrator mode navigate to the folder which contains the "windowsStartupScript.bat"
and run:

"./windowsStartupScript.bat"

After the script has completed you will have a "Reports" folder in the directory/folder the startup script ran from and additionally 
a browser window will pop up with the report presented of the 6 jmeter performance/functional tests ran. Noting which failed first and the passing tests.

--------------

By running the startup script for your testing environment it will install the required tools of git and java where applicable.
*Note these have been commented out in the scripts at the moment as the intent was to detect the os of the system, install via apt-get
if the linux distro or alternative package manager was applopiate or brew for mac. Alternatively the user would be advised to install 
git and java openjdk 21 into their terminal or command line where appropiate.

Next this project will be cloned down to your testing environment. The script will naviage to the project and run the gradle build.

This will download the appropiate dependencies listed in the build.gradle which is mainly junit and jmeter-java-dsl dependencies
which are all currently version pinned. 

Next the 6 tests will run. 

getRepoSearchRepoCountWithJava()
getRepoSearchRepoCountWithJavaFailingTest()
getRepoSearchRepoCountWithPythonFilterByCreation()
getRepoCountWithPythonAndPerlSortedByCreation()
getReposForUser()
getReposWithMostStarsLimitedNumberResultsAndSortDecreasing()

A global Thread count and iteration count have been set for the moment as 1. 
However the intent of this is to be replaced by values set from a gradle.properties file which was intended for the future. 

Note out of the 6 tests 5 are intended to pass with the passing data for verification set at that time. 
1 "getRepoSearchRepoCountWithJavaFailingTest()" is set to fail on purpose. This is just for this project to represent what a failing test would look like
if all the others passed. Including the output on the report. 

After all tests are ran an folder "Reports" will be generated and a browser window will open which indicates the html report. 
Currently this is a basic junit style report indicating which tests passed and failed noting some error messages for the failed test. 
Note while this is basic I would have liked to extract the Jmeter assertion error reports as noted in the "resultsTreeVisualizer()" tool which indicates which 
jsonAssertion failed as well as noting the url request sent and returned to allow for more appropiate debugging. However due to time I was only able to note the
assertion error cound and not the specific details. 

------------------
# Design and thoughts about the assignment
After reading the assignment I begain thinking about the indended audiences, Upper management and local developers and what information would be useful to gain. 

I wanted to output two types of reports 1 for local developers which indicated the number of tests, brief descriptions of what the tests as well as the testing environment 
that the tests ran on and the development environment api ran against. For instance noting configurations, tools and dependencies versions used. The intent was to have this 
information easily searchable on a live dashboard or similar. Noting this information as json format or csv would be useful

The second type of report would be for a higher level overview noting performance metrics of all api calls related to the development configuration that was set in a more graph based format
that would present real-time metrics visualization and historic data storage. Similar to "InfluxDB" and "Elasticsearch"
![image](https://github.com/SeanFitz1919/sdetTechJmeter/assets/157889810/cdfc92b2-a832-448a-a7a1-665c51de5bba)

After this I done a small spike on what technology to use, thinking of performance, functionality and scaling I went with "Jmeter dsl".
I also used gradle for dependency management as I had familarity with it and was intending if time permitted to include a gradle.properties file
to represent all the properites which could be used in the tests. 

Unfortualty while I was not able to put a gradle.properties file in time I will brifely show what I was intending to use it for:

remoteURL: - representing the environment url host tested against.
untiTestqueryValue1: - Pool of values which would be used to generate url calls to testing environment 
untiTestqueryValue2: - see above
untiTestqueryValue3: - see above
untiTestqueryValue4: - see above
untiTestqueryValue5: - see above
globalThreadCount: - Number to represent the thread count. Higher for performance testing
globalIterationCount: - Number to represent the Iterations count each test runs. Higher for performance testing
specificTestNameThreadCount: - If propertie is present it will overwrite the Thread count just for that test.
specificTestNameIterationCount: - If propertie is present it will overwrite the Thread count just for that test.
performanceResponseTestTime: - Vale of time to be set to determine if pass or fail in test. 
testScale: - By default set to "local" but also has four other values "BlazeMeter", "OctoPerf", "Azure" "remote" to represent the scale options that could be used. 
            Note: each test would have a check on the run() section on wheter to run locally or in a scalable environment. With the values depicting which scale type to use. 

reportOutput: - change output report location
projectRunDetails: - Add additional details to the reports. Such as
                  Project name, 
                  tooles used,
                  Date tested ran
                  Test ran by X
                  Api / Deployment tested against. 
                  Api / Deployment version
                  Api / Deployment configuration details *Note: I would like lables here so they can be searchable in the future however That would be alot for right now. Therefore more of a nice to have but not required for the moment. 
                  Api / Deployment Environment details
                  Testing Environment Details
                  CommunicationChannels - This would note teams, slack channels or emails on where results should be sent. 

The "gradle.properties" file could be changed in the ci/cd pipelines such as in jenkins on the fly.

----
Notes I would have also liked to update the junt html reports to include the following outputs from resultsTreeVisualizer()
Response -
![image](https://github.com/SeanFitz1919/sdetTechJmeter/assets/157889810/b0339208-e146-4dec-9d46-b65c50abb14d)
Assertion Failure Message -
![image](https://github.com/SeanFitz1919/sdetTechJmeter/assets/157889810/1a2cdd94-e3f4-4822-bd52-1178f28f9daa)


and Dashboard Visualizer
![image](https://github.com/SeanFitz1919/sdetTechJmeter/assets/157889810/91f3c9a8-2647-44ee-91e6-20a2e2475b30)

Documented screenshot
![image](https://github.com/SeanFitz1919/sdetTechJmeter/assets/157889810/a3dbd1fb-cb12-41fc-9b96-0428d576d9ea)

However the junit reports do successfully indicate which tests pass and fail. This is down to two different types of asserts.
1) Which checks the json response "jsonAssertion" which uses jpath query to check the response is correct. If the errors returned by jsonAssertion is zero the junit assert passes.
2) The second Assert checks that the performacne response of the request is less than 5 seconds response by default and would change based on the gradle properites file value to match the thread used and scale configuration. 


# Options for Scaling
Note: For running at scale see -- https://abstracta.github.io/jmeter-java-dsl/guide/#run-test-at-scale
        * By using runIn() four options are available for running the jmeter tests at scale.
Test name can be set, total number of users, threads set per engine and time out 
        

- BlazeMeter
Test name can be set, total number of users, threads set per engine and time out. Additonaly a dashboard can be presented showing real time information.
All that is required is an authenciation token.
  
- OctoPerf
  Similar features to BlazeMeter with similar requirements to setup however also has some addtional featueres such as ramping up users over time. 
            
  
- Azure Load Testing
Again similar features to the above OctoPerf and BlazeMeter with requireing AZURE_CREDS to authenicate. 
  
- JMeter remote testing 
Meter already provides means to run a test on several machines controlled by one master/client machine. This is referred as Remote Testing
JMeter remote testing requires setting up nodes in server/slave mode (using bin/jmeter-server JMeter script) with a configured keystore (usually rmi_keystore.jks, generated with bin/ JMeter script)
which will execute a test plan triggered in a client/master node. This allows for scaling with AWS and any other possible cloud based configuration or virtual machine
hosting service. Even directly into a custom docker kuberneties setup. 



