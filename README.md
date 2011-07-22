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
July 22, 2011 - Released `android-support-v4-r3-googlemaps.jar` based on revision 3 of the Android Compatibility Library (see downloads).  `FragmentPagerAdapter` and `FragmentStatePagerAdapter` are also included from the new `android.support.v13` package.

May 24, 2011 - Released `android-support-v4-r2-googlemaps.jar` based on revision 2 of the Android Compatibility Library (see downloads).

## Limitations
Currently, one downside is that ALL classes extending `FragmentActivity` are `MapActivity`s.  Its possible to make a separate class (i.e. FragmentMapActivity), but it requires some refactoring of the FragmentActivity code.

Feel free to file a bug to request it or fork this project to fix it.

## Getting Help
Need help?  Check out this project's [Google Group](http://groups.google.com/group/android-support-v4-googlemaps-support).
