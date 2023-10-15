

// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
//@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.playandroid.android.library)
    alias(libs.plugins.playandroid.android.hilt)
    alias(libs.plugins.playandroid.android.library.jacoco)
    kotlin("kapt")
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.dragon.common.navigation"
}

dependencies {
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.navigation.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}