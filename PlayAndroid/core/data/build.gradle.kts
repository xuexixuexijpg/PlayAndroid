plugins {
    alias(libs.plugins.playandroid.android.library)
    alias(libs.plugins.playandroid.android.library.jacoco)
    alias(libs.plugins.playandroid.android.hilt)
    kotlin("kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.dragon.core.data"
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(project(mapOf("path" to ":module_common:network")))
    implementation(project(mapOf("path" to ":module_common:designsystem")))
    implementation(project(mapOf("path" to ":module_common:base")))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
}