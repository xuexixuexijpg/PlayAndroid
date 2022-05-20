object BuildConfig {

     const val minSdkVersion = 21
     const val buildToolsVersion = "30.0.3"
     const val compileSdkVersion = 31
     const val targetSdkVersion = 31

    //version
     const val versionCode = 1
     const val versionName = "1.0"


    const val isAppMode = false


    /**
     * 环境版本
     * 开发版本：DEVELOP
     * 测试版本：RELEASE
     * 正式版本：MASTER
     */
    object Variants {
        //开发版本
        const val DEVELOP = "DEVELOP"
        //测试版本
        const val RELEASE = "RELEASE"
        //正式版本
        const val MASTER = "MASTER"
    }


    object TestLib {
        const val testImpl = "junit:junit:4.13.2"
        const val testJunitImpl2 = "androidx.test.ext:junit:1.1.3"
        const val testEspresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}