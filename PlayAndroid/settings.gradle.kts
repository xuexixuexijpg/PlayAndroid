dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://maven.aliyun.com/repository/public' }
        maven { url 'https://maven.aliyun.com/repository/google' }
        maven { url 'https://repo.huaweicloud.com/repository/maven' }
        maven{ url 'https://maven.aliyun.com/repository/gradle-plugin'}
        maven { url 'https://jitpack.io' }
    }
}
enableFeaturePreview('VERSION_CATALOGS')
//不使用buildSrc或ext统一管理版本;使用catalogs管理;附:有提示的可用dsl/kts
dependencyResolutionManagement {
    versionCatalogs {
        libs {
            from(files("libs.versions.toml"))
        }
    }
}

rootProject.name = "Play Android"
include ':app'
include ':module_common'
include ':module_common:common-base'
include ':module_common:common-widgets'
include ':module_common:common-web-browser'
include ':module_common:common-utils'
include ':module_service'
include ':module_ui'

include ':module_service:service-di-app'

//main
include ':module_ui:ft-main'
//search
include ':module_ui:ft-search'
include ':module_ui:ft-login'
include ':module_ui:ft-main-home'
include ':module_ui:ft-main-mine'
include ':module_common:common-imageloading'
include ':module_common:common-network'

include ':module_ui:ft-main-square'
include ':module_common:common-data'
