A basic template for Android Projects.
[Under construction- not recommended to use as is.]

To update the dependencies version catalog, run command:
./gradlew versionCatalogUpdate

Setting up Fastlane:
Fastlane is a ruby app, and gem is the package manager for ruby apps. A gemfile contains the info
needed by Fastlane. The Android build command of Ruby has a nice syntax for passing project
properties to the build, which gradle can use when compiling (like version code).

The QA branch is for running Firebase App Distribution builds. Any push onto QA will trigger the
build, and release to the list of qa testers.
To get set up on Firebase distribution, create a tester group, add emails of testers, then go to
google console for the project, by selecting advanced settings in Firebase settings. Then add a
service account, for Firebase admin. A key needs to be added to the service account. Then base64
encode the json file, and add the string to Github actions as a secret. Then grab the firebase app
id for the specific Android app, and add this as github actions secret.
This depends on a google services config file. For each specific firebase app (like prod/dev), you
get separate file, and you place each one in the respective flavor folder.
Fastlane also needs a plugin to be installed for firebase app distribution:
In terminal, run:
gem install fastlane-plugin-firebase_app_distribution 

App signing:
The info for signing should come from Github actions as secrets




