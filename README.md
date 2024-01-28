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




