object BuildConfig {

     const val minSdkVersion = 26
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
}