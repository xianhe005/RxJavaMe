package com.hxh.rxjavame.rxjava;

/**
 * Created by HXH at 2019/6/6
 * 变换
 */
public interface Function<T, R> {
    R apply(T t);
}
