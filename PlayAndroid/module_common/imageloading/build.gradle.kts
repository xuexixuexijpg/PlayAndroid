

plugins {
    id("playandroid.android.library")
    id("playandroid.android.library.jacoco")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.dragon.common.imageloading"
}

dependencies {

    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    implementation(libs.hilt.android)
    implementation(project(mapOf("path" to ":module_common:base")))
    kapt(libs.hilt.compiler)
}
