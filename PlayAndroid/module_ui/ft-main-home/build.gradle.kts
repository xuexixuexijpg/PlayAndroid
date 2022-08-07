import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id ("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}

android {
    buildFeatures {
        viewBinding = true
    }

    //由于报红色线 就不用了 暂无解决办法
//    val srcDirs = listOf(
//        "src/main/res",
//        "src/main/res/drawable/img",
//        "src/main/res/drawable/background",
//        "src/main/res/drawable/vector",
//        "src/main/res/layout/views",
//        "src/main/res/layout/layouts")
//
//    for (src in srcDirs){
//        sourceSets["main"].res.srcDirs(
//            src
//        )
//    }

}

dependencies {
    implementation(Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-widgets")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-data")))
    implementation(project(mapOf("path" to ":module_common:common-network")))
    implementation(project(mapOf("path" to ":module_common:common-utils")))
    implementation(project(mapOf("path" to ":module_common:common-database")))
    kapt(Hilt.compilerHilt)
//    kapt(OtherLibs.epoxyCompiler)
//    implementation(OtherLibs.epoxyPaging)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    implementation(OtherLibs.byBinding)
    kapt(OtherLibs.epoxyCompiler)

    implementation(OtherLibs.BRV)
    implementation(OtherLibs.banner)
    implementation(OtherLibs.bannerViewPager)

    implementation(Kotlin.dateTime)

//    implementation("io.github.lapism:search:1.2.1")
}