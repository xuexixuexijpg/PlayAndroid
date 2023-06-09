
@file:Suppress("unused")
package com.dragon.common_utils.mmkvutil

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV

/**
 * Set up MMKV on App startup. You can add the following code to customize MMKV's root directory.
 *
 * **Step 1. Add startup's dependency in `build.gradle`:**
 *
 * ```
 * implementation "androidx.startup:startup-runtime:1.1.0"
 * ```
 *
 * **Step 2. Remove MMKVInitializer in `AndroidManifest.xml`:**
 *
 * ```xml
 * <provider
 *   android:name="androidx.startup.InitializationProvider"
 *   android:authorities="${applicationId}.androidx-startup"
 *   android:exported="false"
 *   tools:node="merge">
 *   <meta-data
 *     android:name="com.dylanc.mmkv.MMKVInitializer"
 *     tools:node="remove" />
 * </provider>
 * ```
 *
 * **Then you can customize MMKV's root directory on App startup, for example:**
 *
 * ```kotlin
 * val dir = "${filesDir?.absolutePath}/mmkv_2"
 * MMKV.initialize(this, dir)
 * ```
 *
 * @author Dylan Cai
 */

/**
 * 用了 setUp延迟初始化操作
 */
class MMKVInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        MMKV.initialize(context)
    }

    override fun dependencies() = emptyList<Class<Initializer<*>>>()
}