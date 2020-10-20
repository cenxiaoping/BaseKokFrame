package com.example.basekokframe.base;

import com.example.basekokframe.api.Api;
import com.example.basekokframe.api.CallBack;
import com.example.basekokframe.bean.BaseBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseModel {

    private BaseModel() {
    }

    private static class BaseModelHolder {
        private static final BaseModel sInstance = new BaseModel();
    }

    public static BaseModel getInstance() {
        return BaseModelHolder.sInstance;
    }


    /**
     * @param cls
     * @return
     */
    public <K> K request(Class<K> cls) {
        return Api.getInstance().create(cls);
    }

    /**
     * 发起网络请求
     *
     * @param observable
     * @param <T>
     */
    public <T> void execute(Observable<BaseBean<T>> observable, final CallBack<BaseBean<T>> callBack) {
//        if (!mView.isShowing()) {
//            mView.showLoading();
//        }

        Observer<BaseBean<T>> observer = new Observer<BaseBean<T>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseBean<T> t) {
                if (callBack != null) {
                    if (t.getCode() == 1) {
                        callBack.onSuccess(t);
                    } else {
                        callBack.onFaile(t.getMessage(), t.getCode(), t);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                if (callBack != null) {
                    callBack.onFaile("网络请求出现错误", 500, null);
                }
            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

            }
        }).subscribe(observer);

    }

//    /**
//     * 发起网络请求
//     * 控制是否显示loading
//     *
//     * @param observable
//     * @param <T>
//     */
//    public <T> void execute(Observable<T> observable, boolean isShowLoadding, final CallBack callBack) {
//        if (isShowLoadding) {
//            execute(observable, callBack);
//        } else {
//            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new YsObserver<T>(mView, callBack));
//        }
//    }
}
