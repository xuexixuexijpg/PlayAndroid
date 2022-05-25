
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

android {
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-home")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-mine")))
    implementation(project(mapOf("path" to ":module_service:service-base")))
    kapt(Hilt.compilerHilt)

    implementation(OtherLibs.navigator_core)
    implementation(OtherLibs.navigator_bottom)
    implementation(OtherLibs.navigator_rail)
    implementation(OtherLibs.byBinding)
}