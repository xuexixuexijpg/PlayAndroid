
plugins {

        id("com.android.library")

    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":module_common:common-base")))
//    implementation(project(mapOf("path" to ":module_common:common-utils")))
//    implementation(project(mapOf("path" to ":module_common:common-imageloading")))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}