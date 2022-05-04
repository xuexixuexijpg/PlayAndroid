

plugins {
    id("com.android.library")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}


dependencies {
    implementation (Hilt.implHilt)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(Hilt.compilerHilt)
    kapt("com.android.support:multidex:1.0.3")
}