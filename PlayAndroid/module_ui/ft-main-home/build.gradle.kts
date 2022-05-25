import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-widgets")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_service:service-base")))
    kapt(Hilt.compilerHilt)
    kapt(OtherLibs.epoxyCompiler)

    implementation(OtherLibs.navigator_core)
    implementation(OtherLibs.byBinding)

//    implementation("io.github.lapism:search:1.2.1")
}