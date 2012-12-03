# Release Checklist
* Create branch `compatibility-r#`:
    * `git checkout -b compatibility-r#`
* *Replace* the `support` folder with newly downloaded release from `ANDROID_HOME\extras\android\support`.
* Update `build.xml` and eclipse build path to add any new source folders (e.g. r11 added `support\v4\src\jellybean-mr1`)
* Update Android jars (`lib/android-##`  and `lib/maps-##`)
* Re-customize FragmentActivity
* Edit version number in ivy.xml (e.g. r11 = 11.0.0).
* Build (`ant`)
* Test
* `ant release_local -Dupload.user=admin -Dupload.password=admin123`
* Update README.md
* Tag with `r#-build1`
* Merge `compatibility-r#` with `master`
* Push `compatibilty-r#`, `master`
* Push Tags: `git push --tags origin`
