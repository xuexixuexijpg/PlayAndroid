package com.dragon.module_base.base.callback

/**
 *
 * 返回键拥有者，用于传递给回调异步调用
 */
interface BackPressedOwner {
    /**
     * 调用基类的返回键处理方法
     */
    fun invokeSuperBackPressed()
}