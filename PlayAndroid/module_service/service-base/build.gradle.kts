plugins {
    id("com.android.library")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}
apply {
    from("../../basic_build.gradle")
}

dependencies {
    implementation (Hilt.implHilt)
    kapt(Hilt.compilerHilt)

    implementation(OtherLibs.navigator_core)
}