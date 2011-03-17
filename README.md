#Android Compatibility Lib + Google Maps Hack
## About
A port of the Android Compatibility package, revision 1 which makes FragmentActivity extend [MapActivity](http://code.google.com/android/add-ons/google-apis/reference/index.html?com/google/android/maps/MapActivity.html).  This is a hack to make it possible to use a [MapView](http://code.google.com/android/add-ons/google-apis/reference/index.html?com/google/android/maps/MapView.html) in a [Fragment](http://developer.android.com/reference/android/app/Fragment.html).

## Building
You can build the jar (`android-support-v4-googlemaps.jar`) using [Ant](http://ant.apache.org).

```text
git clone git://github.com/petedoyle/android-support-v4-googlemaps.git
cd android-support-v4-googlemaps
ant
```

The default Ant target will build the jar and place it in the `android-support-v4-googlemaps/build/jar` folder.

Alternatively, the source also includes an Eclipse project which you can use to add it as a build dependency for your project.  Import it into Eclipse using `File > Import > Existing Projects into Workspace...`, then configure the build path of your project to require the `android-support-v4-googlemaps` project.