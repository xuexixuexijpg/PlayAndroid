pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven ( url =  "https://maven.aliyun.com/repository/public")
        maven (url = "https://maven.aliyun.com/repository/google")
        maven (url = "https://repo.huaweicloud.com/repository/maven")
        maven( url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven( url = "https://jitpack.io")
        maven{
            isAllowInsecureProtocol = true
            setUrl("http://download.flutter.io")
        }
    }
}

rootProject.name = "PlayAndroid"
include (":app")
include (":module_common")
//include (":module_common:common-base")
include (":module_common:utils")
//include (":module_service")
include (":module_ui")

//include (":module_service:service-di-app")

//main
//include (":module_ui:ft-main")

include (":module_common:imageloading")
include (":module_common:network")


include(":module_common:navigation")
include(":module_ui:ft-home")
include(":module_common:widgets")
include(":module_common:base")
include(":module_ui:ft-mine")
include(":module_common:designsystem")
include(":module_ui:ft-search")
include(":module_ui:ft-main")

include(":core:data")
include(":core:database")
include(":core:datastore")

//flutter 配置 https://juejin.cn/post/7246778558248058938
//include(":flutter_lib")
//apply { from("flutter_module_config.gradle") }
