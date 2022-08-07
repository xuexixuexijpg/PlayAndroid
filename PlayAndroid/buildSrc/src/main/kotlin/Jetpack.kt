object Hilt {
    const val hiltVersion = "2.43.2"
    const val classPathHilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val implHilt =  "com.google.dagger:hilt-android:$hiltVersion"
    const val compilerHilt =  "com.google.dagger:hilt-android-compiler:$hiltVersion"

    private const val hiltJetPackVersion = "1.0.0-alpha01"
    const val implVmHilt = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltJetPackVersion"
    const val implWorkHilt =  "androidx.hilt:hilt-work:$hiltJetPackVersion"

    const val compilerJetPackHilt = "androidx.hilt:hilt-compiler:$hiltJetPackVersion"
}

object Navigation{

    //https://developer.android.google.cn/jetpack/androidx/releases/navigation?hl=zh-cn
    private const val nav_version = "2.5.0-rc01"

    // Kotlin
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    const val navUI ="androidx.navigation:navigation-ui-ktx:$nav_version"

    // Feature module Support
    const val navModule = "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"

    val navGroupImplements = listOf(navFragment, navUI, navModule)
}

object Compose{

}

object LifeCycle{
    //https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=zh-cn#groovy

    private const val lifecycle_version = "2.5.0-rc01"
    const val lifeVm = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    const val lifeService = "androidx.lifecycle:lifecycle-service:$lifecycle_version"
    const val lifeRt = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    // ViewModel utilities for Compose
    const val lifeVmCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
    // LiveData
    const val lifeData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    // Annotation processor
    const val lifeCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    const val lifeCompilerJ8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

}

object Window{
    const val windowManager = "androidx.window:window:1.1.0-alpha02"
}