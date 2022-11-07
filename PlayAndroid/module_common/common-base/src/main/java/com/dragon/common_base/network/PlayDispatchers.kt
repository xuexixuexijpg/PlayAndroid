package com.dragon.common_base.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: PlayDispatchers)

enum class PlayDispatchers {
    IO
}
