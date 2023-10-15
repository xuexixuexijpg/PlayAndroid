plugins {
    alias(libs.plugins.playandroid.android.library)
    alias(libs.plugins.playandroid.android.hilt)
}
android {
    namespace = "com.dragon.common.base"
}
dependencies {
    api(libs.kotlinx.coroutines.android)
}
