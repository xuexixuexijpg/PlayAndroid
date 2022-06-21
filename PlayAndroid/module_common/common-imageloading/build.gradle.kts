

plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
}


dependencies {

    api(libs.coil.kt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation("androidx.startup:startup-runtime:1.0.0")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
