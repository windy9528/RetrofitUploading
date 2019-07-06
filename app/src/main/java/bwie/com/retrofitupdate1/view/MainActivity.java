package bwie.com.retrofitupdate1.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import bwie.com.retrofitupdate1.R;
import bwie.com.retrofitupdate1.common.ImplView;
import bwie.com.retrofitupdate1.entity.Result;
import bwie.com.retrofitupdate1.entity.UserPic;
import bwie.com.retrofitupdate1.presenter.UploadPresenter;
import bwie.com.retrofitupdate1.presenter.UserPresenter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private RelativeLayout totalBar;
    private TextView tvCamera;
    private TextView tvAlbum;
    private TextView tvCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    /**
     * 初始化 头像
     */
    private void initData() {
        new UserPresenter(new ImplView() {
            @Override
            public void success(Result data) {
                UserPic userpic = (UserPic) data.getResult();
                String headPic = userpic.getHeadPic();
                if (headPic != null) {
                    Glide.with(MainActivity.this)
                            .load(headPic)
                            .into(img);
                }
            }

            @Override
            public void fail(Result result) {
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).requestData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        img = (ImageView) findViewById(R.id.img);
        totalBar = (RelativeLayout) findViewById(R.id.total_bar);
        tvCamera = (TextView) findViewById(R.id.tv_camera);
        tvAlbum = (TextView) findViewById(R.id.tv_album);
        tvCancle = (TextView) findViewById(R.id.tv_cancle);

        img.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img:
                clickImage();
                break;
            case R.id.tv_camera:
                clickCamera();
                break;
            case R.id.tv_album:
                clickAlbum();
                break;
            case R.id.tv_cancle:
                clickCancle();
                break;
        }
    }

    /**
     * 点击 调用相机功能
     */
    private void clickCamera() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(MainActivity.this)
                .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 点击调用相册
     */
    private void clickAlbum() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    /**
     * 点击取消按钮 隐藏
     */
    private void clickCancle() {
        totalBar.setVisibility(View.GONE);
    }

    /**
     * 点击图片
     */
    private void clickImage() {
        totalBar.setVisibility(View.VISIBLE);  //显示

        totalBar.setOnClickListener(this);
        tvCamera.setOnClickListener(this);
        tvAlbum.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList != null && selectList.size() > 0) {
                        final String compressPath = selectList.get(0).getCompressPath();
                        System.out.println("path==========" + compressPath);
                        //获取到地址后   转码 上传更新头像
                        File file = new File(compressPath);
                        //文件转换成内存对象
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        //
                        MultipartBody.Part part = MultipartBody.Part.createFormData(
                                "image", file.getName(), requestBody
                        );
                        //上传
                        new UploadPresenter(new ImplView() {
                            @Override
                            public void success(Result data) {
                                Glide.with(MainActivity.this)
                                        .load(compressPath)
                                        .into(img);
                                totalBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this,
                                        data.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void fail(Result result) {
                                Toast.makeText(MainActivity.this,
                                        result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).requestData(part);
                    }
                    break;
            }
        }
    }
}
