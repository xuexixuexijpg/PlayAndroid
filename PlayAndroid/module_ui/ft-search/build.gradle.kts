
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
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(Hilt.compilerHilt)

    implementation(project(mapOf("path" to ":module_service:service-base")))
    implementation(OtherLibs.byBinding)
    implementation(OtherLibs.navigator_core)
}