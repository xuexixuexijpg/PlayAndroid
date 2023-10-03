plugins {
    id("playandroid.android.library")
    id("playandroid.android.library.compose")
    id("playandroid.android.library.jacoco")
}
android {
    namespace = "com.dragon.common.ui"
}
dependencies {
//    implementation(project(":core-designsystem"))
//    implementation(project(":core-model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)

    // TODO : Remove these dependency once we upgrade to Android Studio Dolphin b/228889042
    // These dependencies are currently necessary to render Compose previews
    debugImplementation(libs.androidx.customview.poolingcontainer)
    debugImplementation(libs.androidx.lifecycle.viewModelCompose)
    debugImplementation(libs.androidx.savedstate.ktx)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)
    api(libs.androidx.lifecycle.runtimeCompose)
    api(libs.androidx.metrics)
    api(libs.androidx.tracing.ktx)
    api(libs.lottie)
    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    api(libs.androidx.constraintlayout.compose)
}