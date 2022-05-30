
plugins {
    if (BuildConfig.isAppMode) {
        id("com.android.application")
    } else {
        id("com.android.library")
    }
    id ("kotlin-android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}

dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-data")))
    kapt(Hilt.compilerHilt)
}