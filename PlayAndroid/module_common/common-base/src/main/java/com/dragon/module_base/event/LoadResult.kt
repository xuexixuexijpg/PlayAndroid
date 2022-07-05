package com.dragon.module_base.event

/**
 * 0 加载中 1 成功 2失败
 */
enum class LoadResult(val state:Int) {
    LOADING(0),
    SUCCESS(1),
    FAIL(2),

    NO_MORE_DATA(3)
}
