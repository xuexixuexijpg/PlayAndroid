plugins{
    id("playandroid.android.application")
    id("playandroid.android.application.compose")
    id("playandroid.android.application.jacoco")
    kotlin("kapt")
    id("jacoco")
    id("playandroid.android.hilt")
//    id("nowinandroid.spotless")
}

private object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"
    const val DEV = "dev"
}

android {
    defaultConfig {
        applicationId = "com.dragon.playcomposeandroid"
        versionCode =  1
        versionName = "0.0.1"

        vectorDrawables {
            useSupportLibrary = true
        }

        signingConfigs {
            //signingConfigs打包正式版本时才需要放开
            create(BuildTypes.RELEASE) {
                keyAlias = "wanandroid.keystore"
                keyPassword = "wanandroid"
                storeFile =  file("wanandroid.keystore")
                storePassword =  "wanandroid"
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = false
            }
        }
    }

    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = false
            isDebuggable = true
//            applicationIdSuffix = ".${BuildTypes.DEV}"
            versionNameSuffix = "-${BuildTypes.DEV.uppercase()}"
        }

        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = false // 开启代码混淆
            isShrinkResources = false // 移除未被使用的资源
            isDebuggable = false
            ndk {
                abiFilters.apply {
                    add("arm64-v8a")
                    add("armeabi-v7a")
                }
            }
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(BuildTypes.RELEASE)
        }
    }

    //多渠道打包 https://github.com/Amifeng/MVVM-Project-Hilt/blob/Groovy-To-KTS/app/build.gradle.kts
    //https://blog.51cto.com/u_15477127/4891204
    flavorDimensions += com.dragon.playandroid.FlavorDimension.contentType.name
    productFlavors {
        com.dragon.playandroid.Flavor.values().forEach {
            create(it.name) {
//                applicationId =
//                buildConfigField()
//                resValue()
//                manifestPlaceholders.apply {
//                    "app_name" to "谷歌app"
//                }
                dimension = it.dimension.name
                if (it.applicationIdSuffix != null) {
                    applicationIdSuffix = it.applicationIdSuffix
                }
            }
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.dragon.playandoird"
}

dependencies {
//    implementation(project(mapOf("path" to ":flutter")))
    implementation(project(mapOf("path" to ":module_common:navigation")))
    //    implementation(project(mapOf("path" to ":module_common:common-base")))
//    implementation(project(mapOf("path" to ":module_ui:ft-main")))

    implementation(project(mapOf("path" to ":module_common:base")))
    implementation(project(mapOf("path" to ":module_common:imageloading")))
    implementation(project(mapOf("path" to ":module_common:network")))
    implementation(project(mapOf("path" to ":module_common:widgets")))
    implementation(project(mapOf("path" to ":module_common:designsystem")))
    implementation(project(mapOf("path" to ":module_common:utils")))

    implementation(project(mapOf("path" to ":module_ui:ft-home")))
    implementation(project(mapOf("path" to ":module_ui:ft-mine")))
    implementation(project(mapOf("path" to ":module_ui:ft-main")))
    implementation(project(mapOf("path" to ":module_ui:ft-search")))
    implementation(project(mapOf("path" to ":module_ui:ft-setting")))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.window.manager)
    implementation(libs.material3)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.process)

//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
    //内存泄露检测
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")
}