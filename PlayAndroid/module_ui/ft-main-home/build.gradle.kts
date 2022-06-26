import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id ("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}


android {
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

//    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
//    implementation(project(mapOf("path" to ":module_common:common-base")))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

//    implementation("io.github.lapism:search:1.2.1")
}