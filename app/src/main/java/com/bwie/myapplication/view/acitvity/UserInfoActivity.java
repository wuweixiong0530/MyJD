package com.bwie.myapplication.view.acitvity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.myapplication.R;
import com.bwie.myapplication.model.bean.UpLoadPicBean;
import com.bwie.myapplication.model.bean.UserInfoBean;
import com.bwie.myapplication.presenter.UpLoadPicPresenter;
import com.bwie.myapplication.presenter.UserInfoPresenter;
import com.bwie.myapplication.util.CommonUtil;
import com.bwie.myapplication.utils.Constant;
import com.bwie.myapplication.view.iview.UpLoadActivityInter;
import com.bwie.myapplication.view.iview.UserInforInter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener, UpLoadActivityInter, UserInforInter {

    private ImageView detail_image_back;
    private ImageView setting_icon;
    private LinearLayout linear_upload;
    //拍照保存的路径任意
    private String path = "/sdcard/myHead/";// sd路径 ;
    private Bitmap head;// 头像Bitmap
    private UpLoadPicPresenter upLoadPicPresenter;
    private UserInfoPresenter userInfoPresenter;
    private Bitmap bt;
    private String fileName;
    private Bitmap.CompressFormat jpeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        detail_image_back = findViewById(R.id.detail_image_back);
        setting_icon = findViewById(R.id.setting_icon);
        linear_upload = findViewById(R.id.linear_upload);


        detail_image_back.setOnClickListener(this);
        linear_upload.setOnClickListener(this);

        initData();

        //设置点击事件
        linear_upload.setOnClickListener(this);

        // 从SD卡中找头像，转换成Bitmap
        bt = BitmapFactory.decodeFile(path + "head.jpg");
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            setting_icon.setImageDrawable(drawable);

        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }

    }

    private void initData() {

        //mvp
        upLoadPicPresenter = new UpLoadPicPresenter(this);
        userInfoPresenter = new UserInfoPresenter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_image_back:

                finish();
                break;
            case R.id.linear_upload://只做一个弹出的动作..popupWindown
                showTypeDialog();
                break;

        }
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.upload_pop_layout, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);

                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开

                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                    File fileBt = new File(String.valueOf(data.getData()));

                    upLoadPicPresenter.uploadPic(Constant.UPLOAD_ICON_URL,fileBt,CommonUtil.getString("uid"));

                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片

                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        setting_icon.setImageBitmap(head);// 用ImageButton显示出来

                        File btFile = new File(String.valueOf(bt));

                        upLoadPicPresenter.uploadPic(Constant.UPLOAD_ICON_URL,btFile,CommonUtil.getString("uid"));

                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        // 图片名字
        fileName = path + "head.jpg";
        try {
            b = new FileOutputStream(fileName);
            jpeg = Bitmap.CompressFormat.JPEG;
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

            userInfoPresenter.getUserInfo(Constant.USER_INFO_URL, CommonUtil.getString("uid"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重写获取用户信息
     * @param responseBody
     */
    @Override
    public void onUserInforSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            UserInfoBean userInfoBean = new Gson().fromJson(string, UserInfoBean.class);
            if ("0".equals(userInfoBean.getCode())) {

                //获取用户信息成功....拿到icon在服务器上的路径,然后加载显示头像
//                GlideImgManager.glideLoader(UserInfoActivity.this, userInfoBean.getData().getIcon(), R.drawable.user, R.drawable.user, setting_icon, 0);
                String icon = userInfoBean.getData().getIcon();
                //并且需要保存icon的新路径
                CommonUtil.saveString("iconUrl", userInfoBean.getData().getIcon());
                Toast.makeText(UserInfoActivity.this,userInfoBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(UserInfoActivity.this,userInfoBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写上传头像
     * @param responseBody
     */
    @Override
    public void uploadPicSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            UpLoadPicBean upLoadPicBean = new Gson().fromJson(string, UpLoadPicBean.class);
            if ("0".equals(upLoadPicBean.getCode())) {
                Toast.makeText(UserInfoActivity.this, upLoadPicBean.getMsg(), Toast.LENGTH_SHORT).show();
                //上传成功之后需要获取用户信息,,,

            }else {
                Toast.makeText(UserInfoActivity.this,upLoadPicBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
