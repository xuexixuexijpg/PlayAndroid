

plugins {
    id("com.android.library")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}
android {
    buildTypes {
        getByName("release") {
            multiDexKeepProguard  = file("multidex-config.pro")
        }
        getByName("debug") {
            multiDexKeepProguard  = file("multidex-config.pro")
        }
    }
}


dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(Hilt.compilerHilt)
}