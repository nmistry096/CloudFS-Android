#Bitcasa-cloudfs-sdk-android 
  Bitcasa-cloudfs-sdk-android is a library project to enable developers integrate Bitcasa CloudFS file system into their applications for storage.
  
#Dependencies
  android-support-v7-appcompat (version 19.0.1 or above)
  
#Documentation
  - BitcasaSDKfs - bitcasa cloudfs android sdk
  - BitcasaSDKfsTest - unit test against BitcasaSDKfs
  - MyStorage - sample app; MyStorage gives basic demo of how to authorized through Bitcasa Cloudfs, upload a file to Bitcasa file system, download first file from Bitcasa file system.
  - detail api document - https://www.bitcasa.com/cloudfs-api-docs/api/Introduction.html
  
#Setup Instructions 
  1. download ZIP, and then unzip the package
  2. Open Eclipse, go to File Menu -> Import
  3. On Import dialog, select Android->Existing Android Code Into Workspace, then click "Next" button
  4. click "Browse..." button and select the unzipped "bitcasa-cloudfs-sdk-android-master" folder; then you will see 4 projects as      below, check them all, then click "Finish" button<br />
	- android-v7-appcompat
	- BitcasaSDKfs
	- BitcasaSDKfsTest
	- MyStorage
  5. Fix sample app "MyStorage" problem: right click on "MyStorage" project under Package Explorer, select "Properties", and click      on "Android" tab, add follow 2 libraries:<br />
    - android-support-v7-appcompat
    - BitcasaSDKfs
<br />then click "OK" button to exit the menu.
