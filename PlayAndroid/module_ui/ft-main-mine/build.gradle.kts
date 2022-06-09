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
apply {
    from("../../basic_build.gradle")
}

android{
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-data")))
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation("com.github.liangjingkanji:BRV:1.3.75")
    implementation(OtherLibs.byBinding)
    kapt(Hilt.compilerHilt)
}