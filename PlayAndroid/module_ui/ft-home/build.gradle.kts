plugins {
    id("playandroid.android.library")
    id("playandroid.android.feature")
    id("playandroid.android.library.compose")
    id("playandroid.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    id ("kotlinx-serialization")
}


dependencies {
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.accompanist.flowlayout)

    implementation(libs.kotlinx.serialization.json)

    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_common:common-designsystem")))
    implementation(project(mapOf("path" to ":core:data")))
}