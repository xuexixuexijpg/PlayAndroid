package config

import extensions.*
import org.gradle.api.artifacts.dsl.DependencyHandler

/*
* Adds required dependencies to app module
* */
fun DependencyHandler.appModuleDeps() {
    // Libraries
    implementation(project(Modules.common))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    // Navigation Component
    implementation(Deps.AndroidX.Navigation.ui)
    implementation(Deps.AndroidX.Navigation.fragment)
    implementation(Deps.AndroidX.Navigation.dynamicFeaturesFragment)

    api(Deps.Android.material)
    api(Deps.AndroidX.appcompat)
    api(Deps.AndroidX.Constraint.constraintLayout)

    // Hilt
    implementation(Deps.Dagger.hiltAndroid)
    kapt(Deps.Dagger.hiltAndroidCompiler)
}

/*
* Adds required dependencies to home module
* */
fun DependencyHandler.homeModuleDeps() {

    // Libraries
    implementation(project(Modules.common))
    implementation(project(Modules.app))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    // Navigation components
    implementation(Deps.AndroidX.Navigation.fragment)

    // Hilt
    implementation(Deps.Dagger.hiltAndroid)
    kapt(Deps.Dagger.hiltAndroidCompiler)
}

/*
* Add required dependencies to favorite module
* */
fun DependencyHandler.favoriteModuleDeps() {

    // Navigation components
    implementation(Deps.AndroidX.Navigation.fragment)

    // Hilt
    implementation(Deps.Dagger.hiltAndroid)
    kapt(Deps.Dagger.hiltAndroidCompiler)
}

/*
* Add required dependencies to account module
* */
fun DependencyHandler.accountModuleDeps() {
    // AndroidX
    api(Deps.Android.material)
    api(Deps.AndroidX.appcompat)
    api(Deps.AndroidX.Constraint.constraintLayout)

    // Hilt
    implementation(Deps.Dagger.hiltAndroid)
    kapt(Deps.Dagger.hiltAndroidCompiler)
}

/*
* Add required dependencies to common module
* */
fun DependencyHandler.commonModuleDeps() {
    // KTX
    api(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.Fragment.fragmentKtx)
    implementation(Deps.AndroidX.Activity.activityKtx)
    implementation(Deps.AndroidX.Navigation.commonKtx)

    // Coroutines
    api(Deps.Coroutines.core)
    api(Deps.Coroutines.android)

    // AndroidX Libs
    api(Deps.Android.material)
    api(Deps.AndroidX.appcompat)
    api(Deps.AndroidX.Constraint.constraintLayout)

    // Timber
    api(Deps.Timber.timber)
}

/*
* Add required dependencies to data module
* */
fun DependencyHandler.dataModuleDeps() {

}

/*
* Add required dependencies to domain module
* */
fun DependencyHandler.domainModuleDeps() {

}

/*
* Add Unit test dependencies
* */
fun DependencyHandler.unitTestDeps() {
    // (Required) writing and executing Unit Tests on the JUnit Platform
    testImplementation(TestDeps.JUnit.junit)

    // AndroidX Test - JVM testing
    testImplementation(TestDeps.AndroidX.coreKtx)

    // Coroutines Test
    testImplementation(TestDeps.Coroutines.coroutines)

    // MockWebServer
    testImplementation(TestDeps.MockWebServer.mockwebserver)

    // MocKK
    testImplementation(TestDeps.MockK.mockK)

    // Truth
    testImplementation(TestDeps.truth)
}

/*
* 测试依赖
* */
fun DependencyHandler.androidTestDeps() {
    // AndroidX Test - Instrumented testing
    androidTestImplementation(TestDeps.AndroidX.androidX_jUnit)
    androidTestImplementation(TestDeps.AndroidX.coreTesting)

    // Espresso
    androidTestImplementation(TestDeps.espressoCore)

    // Navigation Testing
    androidTestImplementation(TestDeps.AndroidX.navigationTest)

    // Coroutines Test
    androidTestImplementation(TestDeps.Coroutines.coroutines)

    // MockWebServer
    androidTestImplementation(TestDeps.MockWebServer.mockwebserver)

    // MockK
    androidTestImplementation(TestDeps.MockK.mockK)

    // Truth
    androidTestImplementation(TestDeps.truth)
}