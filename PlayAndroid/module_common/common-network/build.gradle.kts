@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("playandroid.android.library")
    id("playandroid.android.library.jacoco")
    kotlin("kapt")
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.dragon.common.network"
}

dependencies {
    implementation(libs.coil.kt)

    //通知栏监听网络日志
    implementation("com.github.chuckerteam.chucker:library:3.5.2")
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.net)
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    api("com.github.liangjingkanji:Net:3.5.2")

    //https://github.com/liujingxing/rxhttp/blob/master/README_zh.md
//    api("com.github.liujingxing.rxhttp:rxhttp:$rxhttp_version")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
