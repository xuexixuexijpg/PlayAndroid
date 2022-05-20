package com.dragon.common_utils.ext

import androidx.fragment.app.Fragment
import java.util.*
import kotlin.reflect.KClass

typealias SimpleFunction = () -> Unit
typealias ErrorFunction = (Exception?) -> Unit
typealias FragClazz = KClass<out Fragment>
