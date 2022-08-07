import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply {
    from("../../base_library.gradle")
}
dependencies {
    implementation (Hilt.implHilt)
    implementation(OtherLibs.coil)

    //通知栏监听网络日志
    implementation("com.github.chuckerteam.chucker:library:3.5.2" )
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    api(OtherLibs.net_lib)
    kapt(Hilt.compilerHilt)
}
