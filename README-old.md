#Android Compatibility Lib + Google Maps Hack
## About
A port of the Android Compatibility package which makes FragmentActivity extend [MapActivity](http://code.google.com/android/add-ons/google-apis/reference/index.html?com/google/android/maps/MapActivity.html).  This is a hack to make it possible to use a [MapView](http://code.google.com/android/add-ons/google-apis/reference/index.html?com/google/android/maps/MapView.html) in a [Fragment](http://developer.android.com/reference/android/app/Fragment.html).

## Downloading
For pre-compiled .jar files, visit the [Downloads](https://github.com/petedoyle/android-support-v4-googlemaps/downloads) page.

## Building
You can build the jar using [Ant](http://ant.apache.org):

    git clone git://github.com/petedoyle/android-support-v4-googlemaps.git
    cd android-support-v4-googlemaps
    ant

The default Ant target will build the jar and place it in the `build/jar` folder.

Alternatively, the source also includes an Eclipse project which you can use to add it as a build dependency for your project.  Import it into Eclipse using `File > Import > Existing Projects into Workspace...`, then configure the build path of your project to require the `android-support-v4-googlemaps` project.

## Changelog
### December 3, 2012
**Revision 11**

Based on r11 of the Android Support Library.  Google's changelog available [here](http://developer.android.com/tools/extras/support-library.html#SettingUp).

This is likely the last release.  It is now recommended to use Google's official [MapFragment](http://android-developers.blogspot.com.br/2012/12/new-google-maps-android-api-now-part-of.html) (released today, 2012-12-03).

### September 18, 2012
**Revision 10**

Based on r10 of the Android Support Library.  Google's changelog available [here](http://developer.android.com/tools/extras/support-library.html#SettingUp).

This release also updates the build script so it'll be easier to keep in-sync with the official google releases.  The new process is very simple:

* Download the newest Android Support Library release using the Android SDK Manager.
* Delete and replace the `support/` folder with `$ANDROID_HOME/extras/android/support`
* Re-customize `FragmentActivity` to extend `MapActivity` ([example](https://github.com/petedoyle/android-support-v4-googlemaps/commit/c8e271698e762c46cacf47fe10b3495122e75a69))
* Build as usual, using '`ant`'

In addition, the fix for Android [bug #22226](http://code.google.com/p/android/issues/detail?id=22226) has been included in Google's official release.  Because of that, this release is once again the exact Google release with only [this change](https://github.com/petedoyle/android-support-v4-googlemaps/commit/c8e271698e762c46cacf47fe10b3495122e75a69).

### March 28, 2012
**Revision 7**

New release based on r7 of the Android Compatibility Library.

### December 16, 2011
**Revision 6**

Released `android-support-v4-r6-googlemaps.jar` and `android-support-v13-r6-googlemaps.jar` based on revision 6 of the Android Compatibility Library.  This also includes a fix for Android [bug #22226](http://code.google.com/p/android/issues/detail?id=22226).

This also includes the additions from Google's r5 release.  For the changelog of what changed in ACL r5 and r6, see http://developer.android.com/sdk/compatibility-library.html.

### October 27, 2011
**Revision 4**

Released `android-support-v4-r4-googlemaps.jar` and `android-support-v13-r4-googlemaps.jar` based on revision 4 of the Android Compatibility Library.  This release, like the official release from Google, splits the ACL into two jars (v4 and v13).  The v13 jar contains all the v4 classes _plus_ the classes in the `android.support.v13` package.

For the changelog of what changed in ACL r4, see http://developer.android.com/sdk/compatibility-library.html.

### July 22, 2011
**Revision 3**

Released `android-support-v4-r3-googlemaps.jar` based on revision 3 of the Android Compatibility Library (see downloads).  `FragmentPagerAdapter` and `FragmentStatePagerAdapter` are also included from the new `android.support.v13` package.

### May 24, 2011
**Revision 2**

Released `android-support-v4-r2-googlemaps.jar` based on revision 2 of the Android Compatibility Library (see downloads).

## Limitations
Currently, one downside is that ALL classes that extend `FragmentActivity` also extend `MapActivity`.  Its possible to make a separate class (i.e. FragmentMapActivity), but it requires some refactoring of the FragmentActivity code.

Feel free to file a bug to request it or fork this project to fix it.

## Getting Help
Need help?  Check out this project's [Google Group](http://groups.google.com/group/android-support-v4-googlemaps-support).