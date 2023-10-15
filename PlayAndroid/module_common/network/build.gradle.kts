plugins {
    alias(libs.plugins.playandroid.android.library)
    alias(libs.plugins.playandroid.android.hilt)
    alias(libs.plugins.playandroid.android.library.jacoco)
    kotlin("kapt")
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
}
android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.dragon.common.network"
}

dependencies {
    implementation(libs.coil.kt)

    //通知栏监听网络日志
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.net)
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    //原来使用的网络封装框架 https://github.com/liangjingkanji/Net
    implementation(libs.okhttp.logging)
    api(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    //https://github.com/liujingxing/rxhttp/blob/master/README_zh.md
//    api("com.github.liujingxing.rxhttp:rxhttp:$rxhttp_version")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
