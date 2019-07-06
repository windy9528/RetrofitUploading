package bwie.com.retrofitupdate1.common;

import bwie.com.retrofitupdate1.entity.Result;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public interface ImplView<T> {

    void success(Result data);

    void fail(Result result);

}
