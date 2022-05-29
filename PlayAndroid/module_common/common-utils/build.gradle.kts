

plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
    id("kotlin-parcelize")
}

apply {
    from("../../base_library.gradle")
}
dependencies {
    api("com.tencent:mmkv-static:1.2.13")
    implementation("androidx.startup:startup-runtime:1.1.0")
}
