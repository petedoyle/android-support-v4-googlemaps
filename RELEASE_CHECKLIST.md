# Release Checklist
* Make sure branch is updated (i.e. compatibility-r3)
* Build jar file using Ant.
* Make sure Eclipse project (`.project`) and build path (`.classpath`) are also updated.
* Upload compiled jar to downloads section.  Sample name: `android-support-v4-r4-googlemaps.jar`.
* Tag the branch with r{n}-build{m}, where n is the ACL release version and m is the build number for this version.
* Merge newest release into master branch
* Push both `master` and `compatibility-rN` branches to Github
* Push tags (`git push origin master --tags`)
