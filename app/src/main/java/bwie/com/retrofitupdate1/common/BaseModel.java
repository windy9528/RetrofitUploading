package bwie.com.retrofitupdate1.common;

import bwie.com.retrofitupdate1.UserApi;
import bwie.com.retrofitupdate1.entity.Result;
import bwie.com.retrofitupdate1.util.RetrofitUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public abstract class BaseModel {

    private ImplView implView;

    public BaseModel(ImplView implView) {
        this.implView = implView;
    }

    public void requestData(Object... args) {

        UserApi userApi = RetrofitUtil.getInstance().create(UserApi.class);

        getModel(userApi, args)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable,Result>() {
                    @Override
                    public Result apply(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        return new Result();
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        if ("0000".equals(result.getStatus())) {
                            implView.success(result);
                        } else {
                            implView.fail(result);
                        }
                    }
                });

//        , new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                throwable.printStackTrace();
//                new Result();
//            }
//        }
    }

    protected abstract Observable getModel(UserApi userApi, Object... args);
}
