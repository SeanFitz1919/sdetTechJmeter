!/bin/bash

echo "Starting Script"

echo "Running Setup"

#apt-get install git
#apt-get install java

#brew install git
#brew install java

rm -rf Reports

echo "Downloading sdetTechJmeter project"

git clone https://github.com/SeanFitz1919/sdetTechJmeter.git

echo "Running sdetTechJmeter project"

cd sdetTechJmeter
./gradlew clean build

echo "Testing complete"

echo "Extracting reports"

cd ..
mkdir Reports
cp -r sdetTechJmeter/build/reports/tests/test Reports

echo "Cleaning up..."

rm -rf sdetTechJmeter

echo "Done. Please see reports in Reports folder."
echo "Note attempting to open report in local browser"
open ./Reports/test/classes/GitRepoSearchAPITest.html 

echo "End of Script"