import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    if (BuildConfig.isAppMode) {
        id("com.android.application")
    } else {
        id("com.android.library")
    }
    id ("kotlin-android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}

android{
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-data")))
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-utils")))
    //如果使用这个BRV库结合Navigation的replace的状态保存，多切换还是会出现状态丢失的情况，比如滚动状态
    implementation(OtherLibs.BRV)
    implementation(OtherLibs.byBinding)
    kapt(Hilt.compilerHilt)
    kapt(OtherLibs.epoxyCompiler)
}