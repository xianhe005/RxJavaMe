package com.hxh.rxjavame.rxjava;

/**
 * Created by HXH at 2019/6/6
 * 观察者
 */
public interface Observer<T> {
    void onNext(T t);

    void onComplete();
}
