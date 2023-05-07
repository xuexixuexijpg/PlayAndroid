buildscript {
    repositories {
        google()
        mavenCentral()
        maven ( url =  "https://maven.aliyun.com/repository/public")
        maven (url = "https://maven.aliyun.com/repository/google")
        maven (url = "https://repo.huaweicloud.com/repository/maven")
        maven( url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven( url = "https://jitpack.io")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
//    id("com.android.library") version "7.3.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
//    id("com.android.library") version "7.3.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}
