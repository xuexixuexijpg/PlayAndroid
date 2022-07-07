
plugins {
    if (BuildConfig.isAppMode) {
        id("com.android.application")
    } else {
        id("com.android.library")
    }
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
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
    implementation (Window.windowManager)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-utils")))
    implementation(project(mapOf("path" to ":module_common:common-data")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_common:common-web-browser")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-home")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-mine")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-square")))
    implementation(project(mapOf("path" to ":module_ui:ft-search")))
    kapt(Hilt.compilerHilt)

    implementation(OtherLibs.byBinding)
}