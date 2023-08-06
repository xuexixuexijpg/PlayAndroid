

import com.android.build.gradle.LibraryExtension
import com.dragon.playandroid.configureFlavors
import com.dragon.playandroid.configureKotlinAndroid
import com.dragon.playandroid.configureKotlinAndroidToolchain
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            configureKotlinAndroidToolchain()
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
                configureFlavors(this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                configurations.configureEach {
                    resolutionStrategy {
//                        force(libs.findLibrary("junit4").get())
//                        // Temporary workaround for https://issuetracker.google.com/174733673
//                        force("org.objenesis:objenesis:2.6")
                    }
                }
            }
        }
    }

}
