import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

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

android {
    buildFeatures {
        viewBinding = true
    }
}

apply {
    from("../../basic_build.gradle")
}

dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-data")))
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    kapt(Hilt.compilerHilt)
    kapt(OtherLibs.epoxyCompiler)
    implementation(OtherLibs.byBinding)
}