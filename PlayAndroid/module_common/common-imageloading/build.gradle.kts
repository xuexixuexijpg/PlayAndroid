

plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
}

apply {
    from("../../base_library.gradle")
}
dependencies {
    implementation (Hilt.implHilt)
    api(OtherLibs.coil)
    implementation(project(mapOf("path" to ":module_common:common-base")))
    kapt(Hilt.compilerHilt)
}
