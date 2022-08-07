plugins {
    id("com.android.library")
    id("kotlin-android")
    id ("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    id("dagger.hilt.android.plugin")
}

apply {
    from("../../base_library.gradle")
}

android {
    defaultConfig {
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
        }
    }
}

dependencies {

    val roomVersion = "2.4.2"

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //加密相关
    implementation("net.zetetic:android-database-sqlcipher:4.5.2")
    implementation("androidx.sqlite:sqlite-ktx:2.2.0")

    //Androidx Security
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    //Google Guava
    implementation("com.google.guava:guava:31.0.1-jre")

    //https://github.com/KeepSafe/ReLinker
    implementation("com.getkeepsafe.relinker:relinker:1.4.4")

    implementation(Kotlin.dateTime)

    implementation (Hilt.implHilt)
    kapt(Hilt.compilerHilt)

}