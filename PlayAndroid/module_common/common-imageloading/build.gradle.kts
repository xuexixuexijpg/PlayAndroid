

plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
}


dependencies {

    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    implementation(libs.hilt.android)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(libs.hilt.compiler)
}
