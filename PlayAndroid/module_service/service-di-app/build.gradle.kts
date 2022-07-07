

plugins {
    id("com.android.library")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}
//android {
//    buildTypes {
//        getByName("release") {
//            multiDexKeepProguard  = file("multidex-config.pro")
//        }
//        getByName("debug") {
//            multiDexKeepProguard  = file("multidex-config.pro")
//        }
//    }

//}


dependencies {
    implementation (Hilt.implHilt)
    kapt(Hilt.compilerHilt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    implementation(project(mapOf("path" to ":module_common:common-network")))
    implementation(project(mapOf("path" to ":module_common:common-imageloading")))
    implementation(project(mapOf("path" to ":module_common:common-web-browser")))

    implementation(project(mapOf("path" to ":module_ui:ft-login")))
    implementation(project(mapOf("path" to ":module_ui:ft-main")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-mine")))
    implementation(project(mapOf("path" to ":module_ui:ft-main-home")))
    implementation(project(mapOf("path" to ":module_ui:ft-search")))

}