plugins {
    alias(libs.plugins.playandroid.android.library)
    alias(libs.plugins.playandroid.android.hilt)
    alias(libs.plugins.playandroid.android.feature)
    alias(libs.plugins.playandroid.android.library.compose)
    alias(libs.plugins.playandroid.android.library.jacoco)
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