package com.hxh.rxjavame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hxh.rxjavame.rxjava.Function;
import com.hxh.rxjavame.rxjava.Observable;
import com.hxh.rxjavame.rxjava.Observer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test1();
        Log.i(TAG, "=======================================");
        test2();
        Log.i(TAG, "=======================================");
        test3();
        Log.i(TAG, "=======================================");
        test4();
        Log.i(TAG, "=======================================");
        test5();
        Log.i(TAG, "=======================================");
        test6();
    }

    private void test1() {
        new MyObservable().subscribe(new MyObserver());
    }

    private void test2() {
        Observable.create(new Observable<String>() {
            @Override
            public void subscribe(Observer<String> observer) {
                Log.i(TAG, "subscribe2: " + observer.getClass().getName());
                observer.onNext("hello");
                observer.onNext("world");
                observer.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext2: " + s);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete2: ");
            }
        });
    }

    private void test3() {
        Observable.create(new Observable<String>() {
            @Override
            public void subscribe(Observer<String> observer) {
                Log.i(TAG, "subscribe3: " + observer.getClass().getName());
                observer.onNext("hello");
                observer.onNext("world");
                observer.onComplete();
            }
        }).nullMap()
                .subscribe(new Observer<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext3: " + s);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }

    private void test4() {
        Observable.create(new Observable<String>() {
            @Override
            public void subscribe(Observer<String> observer) {
                Log.i(TAG, "subscribe4: " + observer.getClass().getName());
                observer.onNext("hello");
                observer.onNext("world");
                observer.onComplete();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext4: " + integer);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete4: ");
            }
        });
    }

    private void test5() {
        Observable.create(new Observable<String>() {
            @Override
            public void subscribe(Observer<String> observer) {
                Log.i(TAG, "thread: " + Thread.currentThread().getName());
                Log.i(TAG, "subscribe5: " + observer.getClass().getName());
                observer.onNext("hello");
                observer.onNext("world");
                observer.onComplete();
            }
        }).subscribeOn().subscribe(new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "thread onNext5: " + Thread.currentThread().getName());
                Log.i(TAG, "onNext5: " + s);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "thread onComplete5: " + Thread.currentThread().getName());
                Log.i(TAG, "onComplete5: ");
            }
        });
    }

    private void test6() {
        Observable.create(new Observable<String>() {
            @Override
            public void subscribe(Observer<String> observer) {
                Log.i(TAG, "Observer subscribe6: " + observer);
                Log.i(TAG, "subscribe6: " + observer.getClass().getName());
                observer.onNext("hello");
                observer.onNext("world");
                observer.onComplete();
            }
        }).flatMap(new Function<String, Observable<Integer>>() {
            @Override
            public Observable<Integer> apply(final String s) {
                return new Observable<Integer>() {
                    @Override
                    public void subscribe(Observer<Integer> observer) {
                        Log.i(TAG, "Observer onNext6: " + observer);
                        observer.onNext(s.length());
                    }
                };
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "Observer onNext6: " + this);
                Log.i(TAG, "onNext6: " + integer);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete6: ");
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // inner class
    ///////////////////////////////////////////////////////////////////////////
    private class MyObserver implements Observer<String> {

        @Override
        public void onNext(String s) {
            Log.i(TAG, "onNext1: " + s);
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "onComplete1: ");
        }
    }

    private class MyObservable extends Observable<String> {

        @Override
        public void subscribe(Observer<String> observer) {
            Log.i(TAG, "subscribe1: " + observer.getClass().getName());
            observer.onNext("hello");
            observer.onNext("world");
            observer.onComplete();
        }
    }
}
