package com.dragon.module_base.support.callback

class UnPeekLiveData<T> : ProtectedUnPeekLiveData<T>() {

    //使用方式
    /*
    UnPeekLiveData<Moment> test =
                    new UnPeekLiveData.Builder<Moment>()
                    .setAllowNullValue(false)
                    .create();
    * */

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
            val liveData = UnPeekLiveData<T>()
            liveData.isAllowNullValue = isAllowNullValue
            return liveData
        }
    }
}