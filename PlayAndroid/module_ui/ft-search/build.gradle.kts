plugins {
    id("playandroid.android.library")
    id("playandroid.android.feature")
    id("playandroid.android.library.compose")
    id("playandroid.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    id ("kotlinx-serialization")
}
android {
    namespace = "com.dragon.ui.search"
}

dependencies {
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.accompanist.flowlayout)

    implementation(libs.kotlinx.serialization.json)
}