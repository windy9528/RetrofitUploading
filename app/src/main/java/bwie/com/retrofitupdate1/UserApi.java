package bwie.com.retrofitupdate1;

import com.bumptech.glide.annotation.GlideModule;

import bwie.com.retrofitupdate1.common.Constant;
import bwie.com.retrofitupdate1.entity.Result;
import bwie.com.retrofitupdate1.entity.UserInfo;
import bwie.com.retrofitupdate1.entity.UserPic;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * date:2019/7/6
 * name:windy
 * function:
 */
public interface UserApi {

    @Multipart
    @POST(Constant.UPLOAD_URL)
    Observable<Result> uploadHeadpic(@Header("userId") String userId,
                                     @Header("sessionId") String sessionId,
                                     @Part MultipartBody.Part file);

    @GET(Constant.USER_URL)
    Observable<Result<UserPic>> userPic(@Header("userId") String userId,
                                        @Header("sessionId") String sessionId);
}
