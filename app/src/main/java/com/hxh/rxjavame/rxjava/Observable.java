package com.hxh.rxjavame.rxjava;

/**
 * Created by HXH at 2019/6/6
 * 被观察者
 */
public abstract class Observable<T> {

    /**
     * 订阅
     */
    public abstract void subscribe(Observer<T> observer);

    /**
     * 变换原理
     */
    public Observable<T> nullMap() {
        return new Observable<T>() {
            @Override
            public void subscribe(final Observer<T> observerC) {
                Observer<T> observerB = new Observer<T>() {
                    @Override
                    public void onNext(T t) {
                        observerC.onNext(t);
                    }

                    @Override
                    public void onComplete() {
                        observerC.onComplete();
                    }
                };
                Observable.this.subscribe(observerB);
            }
        };
    }

    /**
     * 变换
     */
    public <R> Observable<R> map(final Function<T, R> function) {
        return new Observable<R>() {
            @Override
            public void subscribe(final Observer<R> observer) {
                Observable.this.subscribe(new Observer<T>() {
                    @Override
                    public void onNext(T t) {
                        R r = function.apply(t);
                        observer.onNext(r);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
            }
        };
    }

    /**
     * 线程变换
     */
    public Observable<T> subscribeOn() {
        return new Observable<T>() {
            @Override
            public void subscribe(final Observer<T> observer) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Observable.this.subscribe(observer);
                    }
                }.start();
            }
        };
    }

    public <R> Observable<R> flatMap(final Function<T, Observable<R>> function) {
        return new Observable<R>() {
            @Override
            public void subscribe(final Observer<R> observer) {
                Observable.this.subscribe(new Observer<T>() {
                    @Override
                    public void onNext(T t) {
                        Observable<R> observable = function.apply(t);
                        observable.subscribe(observer);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
            }
        };
    }

    /**
     * 创建被观察者
     */
    public static <T> Observable<T> create(Observable<T> observable) {
        return observable;
    }
}