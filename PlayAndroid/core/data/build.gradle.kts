plugins {
    id("playandroid.android.library")
    id("playandroid.android.library.jacoco")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.dragon.core.data"
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(project(mapOf("path" to ":module_common:imageloading")))
    implementation(project(mapOf("path" to ":module_common:network")))
    implementation(project(mapOf("path" to ":module_common:designsystem")))
    implementation(project(mapOf("path" to ":module_common:base")))
}