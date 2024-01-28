echo "Starting Script"

echo "Running Setup"

echo "Installing Chocolatly windows packagement management tool"


echo "Installing git and latest java openjdk"

choco install git -y
choco install openjdk -y

echo "Downloading sdetTechJmeter project"

git clone https://github.com/SeanFitz1919/sdetTechJmeter.git

echo "Running sdetTechJmeter project"

cd sdetTechJmeter
./gradlew.bat clean build

echo "Testing complete"

echo "Extracting reports"

cd ..
dir Reports
copy  sdetTechJmeter/build/reports/tests/test Reports

echo "Cleaning up..."

rmdir sdetTechJmeter

echo "Done. Please see reports in Reports folder."
echo "Note attempting to open report in local browser"
open ./Reports/test/classes/GitRepoSearchAPITest.html

echo "End of Script"
