package com.dragon.module_base.event

/**
 * TODO 唯一可信源设计
 * 我们在 V6 中继续沿用从 V3 版延续下来的基于 "唯一可信源" 理念的设计，
 * 来确保 "事件" 的发送权牢牢握在可信的逻辑中枢单元手里，从而确保所有订阅者收到的信息都是可靠且一致的，
 *
 *
 * 如果这样说还不理解，可自行查阅《LiveData 唯一可信源 读写分离设计》的解析：
 * https://xiaozhuanlan.com/topic/2049857631
 *
 *
 * TODO 提供 Builder 选项的支持
 * 我们在 V6 中继续沿用从 V3 版延续下来的 Builder 选项设计，
 * 目前提供 "是否允许发送 null" 的选项。
 *
 *
 * Create by KunMinX at 2021/6/17
 */
class UnPeekLiveData<T> : ProtectedUnPeekLiveData<T>() {
    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    class Builder<T> {
        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false
        fun setAllowNullValue(allowNullValue: Boolean): Builder<T> {
            isAllowNullValue = allowNullValue
            return this
        }

        fun create(): UnPeekLiveData<T> {
            val liveData: UnPeekLiveData<T> = UnPeekLiveData<T>()
            liveData.isAllowNullValue = isAllowNullValue
            return liveData
        }
    }
}