
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
    //ui
    implementation(project(mapOf("path" to ":module_ui:ft-login")))
    implementation(project(mapOf("path" to ":module_ui:ft-main")))
    implementation(project(mapOf("path" to ":module_ui:ft-search")))


    implementation (Hilt.implHilt)
    implementation (Window.windowManager)
    implementation(project(mapOf("path" to ":module_common:common-utils")))
    implementation(project(mapOf("path" to ":module_common:common-data")))
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(Hilt.compilerHilt)

    //otherLibs
    implementation(OtherLibs.byBinding)
}