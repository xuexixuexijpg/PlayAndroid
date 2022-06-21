import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
}

dependencies {
    implementation(libs.coil.kt)

    //通知栏监听网络日志
    implementation("com.github.chuckerteam.chucker:library:3.5.2" )
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.github.liangjingkanji:Net:3.4.10")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
