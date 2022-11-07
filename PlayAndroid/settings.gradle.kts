pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
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

rootProject.name = "PlayAndroid"
include (":app")
include (":module_common")
//include (":module_common:common-base")
//include (":module_common:common-utils")
//include (":module_service")
include (":module_ui")

//include (":module_service:service-di-app")

//main
//include (":module_ui:ft-main")

include (":module_common:common-imageloading")
include (":module_common:common-network")


include(":module_common:common-navigation")
include(":module_ui:ft-home")
include(":module_common:common-ui")
include(":module_common:common-base")
include(":module_ui:ft-mine")
include(":module_common:common-designsystem")
include(":module_ui:ft-search")
include(":module_ui:ft-main")

include(":core:data")
include(":core:database")
