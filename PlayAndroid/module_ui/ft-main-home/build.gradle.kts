import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id ("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}

android {
    buildFeatures {
        viewBinding = true
    }

    val srcDirs = listOf(
        "src/main/res",
        "src/main/res/drawable/img",
        "src/main/res/drawable/background",
        "src/main/res/drawable/vector",
        "src/main/res/layout/views",
        "src/main/res/layout/layouts")

    for (src in srcDirs){
        sourceSets["main"].res.srcDirs(
            src
        )
    }

}

dependencies {
    implementation(Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-widgets")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-data")))
    kapt(Hilt.compilerHilt)
    kapt(OtherLibs.epoxyCompiler)
    implementation(OtherLibs.epoxyPaging)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation(OtherLibs.byBinding)

//    implementation("io.github.lapism:search:1.2.1")
}