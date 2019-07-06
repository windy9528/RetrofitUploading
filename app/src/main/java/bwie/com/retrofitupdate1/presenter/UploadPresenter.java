package bwie.com.retrofitupdate1.presenter;

import bwie.com.retrofitupdate1.UserApi;
import bwie.com.retrofitupdate1.common.BaseModel;
import bwie.com.retrofitupdate1.common.ImplView;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public class UploadPresenter extends BaseModel {

    public UploadPresenter(ImplView implView) {
        super(implView);
    }

    @Override
    protected Observable getModel(UserApi userApi, Object... args) {
        return userApi.uploadHeadpic("954",
                "1562389740464954", (MultipartBody.Part) args[0]);
    }
}
