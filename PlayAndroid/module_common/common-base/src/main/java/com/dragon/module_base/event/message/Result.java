package com.dragon.module_base.event.message;

import com.dragon.module_base.event.ProtectedUnPeekLiveData;

public class Result <T> extends ProtectedUnPeekLiveData<T> {

    public Result(T value) {
        super(value);
    }

    public Result() {
        super();
    }

}
