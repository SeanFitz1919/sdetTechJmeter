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

