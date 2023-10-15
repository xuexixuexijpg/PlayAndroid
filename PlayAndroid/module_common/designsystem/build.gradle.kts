plugins {
    alias(libs.plugins.playandroid.android.library)
    alias(libs.plugins.playandroid.android.library.compose)
    alias(libs.plugins.playandroid.android.library.jacoco)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lint {
        checkDependencies = true
    }
    namespace = "com.dragon.common.designsystem"
}

dependencies {
    api(project(":module_common:widgets"))
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.accompanist.systemuicontroller)
}