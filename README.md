#Android Compatibility Lib + Google Maps Hack
## About
A port of the Android Compatibility package, revision 1 which makes FragmentActivity extend [MapActivity](http://code.google.com/android/add-ons/google-apis/reference/index.html?com/google/android/maps/MapActivity.html).  This is a hack to make it possible to use a [MapView](http://code.google.com/android/add-ons/google-apis/reference/index.html?com/google/android/maps/MapView.html) in a [Fragment](http://developer.android.com/reference/android/app/Fragment.html).

## Building
You can build the jar (`android-support-v4-googlemaps.jar`) using [Ant](http://ant.apache.org).

    git clone git://github.com/petedoyle/android-support-v4-googlemaps.git
    cd android-support-v4-googlemaps
    ant

The default Ant target will build the jar and place it in the `android-support-v4-googlemaps/build/jar` folder.

Alternatively, the source also includes an Eclipse project which you can use to add it as a build dependency for your project.  Import it into Eclipse using `File > Import > Existing Projects into Workspace...`, then configure the build path of your project to require the `android-support-v4-googlemaps` project.

## Other changes
Besides making FragmentActivity extend MapActivity, the following has been changed from the original Android Compatibility Package r1:

 * Fix [Issue 15394](http://code.google.com/p/android/issues/detail?id=15394): Fragment.onActivityResult not called when requestCode != 0  (also see [this](https://groups.google.com/d/topic/android-developers/NiM_dAOtXQU/discussion) android-developers thread).

## Limitations
Currently, one downside is that ALL classes extending FragmentActivity are MapActivity's.  Its possible to make a separate class (i.e. FragmentMapActivity), but it requires some refactoring of the FragmentActivity code.

Feel free to file a bug to request it or fork this project to fix it.