plugins {
    id("playandroid.android.library")
    id("playandroid.android.hilt")
}
android {
    namespace = "com.dragon.common.base"
}
dependencies {
    api(libs.kotlinx.coroutines.android)
}
