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
    implementation("androidx.startup:startup-runtime:1.1.1")

    //关于kotlin的扩展工具类
    //https://github.com/DylanCaiCoding/Longan
    api("com.github.DylanCaiCoding.Longan:longan:1.0.5")
    // Optional
    api("com.github.DylanCaiCoding.Longan:longan-design:1.0.5")
}
