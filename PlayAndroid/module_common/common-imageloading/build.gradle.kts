

plugins {
    id("playandroid.android.library")
    id("playandroid.android.library.jacoco")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}


dependencies {

    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
