
plugins {
    id("com.android.library")
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
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(Hilt.compilerHilt)

    //otherLibs
    implementation(OtherLibs.navigator_core)
    implementation(OtherLibs.navigator_extensions)
    implementation(OtherLibs.byBinding)
}