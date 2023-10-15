
plugins {
    `kotlin-dsl`
}

group = "com.dragon.playandroid.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "playandroid.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "playandroid.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "playandroid.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "playandroid.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "playandroid.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "playandroid.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "playandroid.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidTest") {
            id = "playandroid.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }

        //这个用来添加声明的
//        register("spotless") {
//            id = "playandroid.spotless"
//            implementationClass = "SpotlessConventionPlugin"
//        }

        //hilt
        register("androidHilt") {
            id = "playandroid.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        /*jvm*/
        register("jvmLibrary") {
            id = "playandroid.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
