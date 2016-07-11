package com.yizhui.oschina.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yizhui.oschina.AppContext;
import com.yizhui.oschina.R;
import com.yizhui.oschina.bean.User;
import com.yizhui.oschina.util.BitmapHelper;
import com.yizhui.oschina.util.DialogHelper;
import com.yizhui.oschina.widget.CircleImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yizhui on 2016/7/6.
 */
public class MyInformationDetailFragment extends Fragment {

    private static final int TYPE_CHANGE = 0;
    private static final int TYPE_LOOK_BIG = 1;

    private static final int TYPE_ALBUM = 0;
    private static final int TYPE_PHOTO = 1;

    private static final int REQUEST_CODE_IMAGE_PICK = 0;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_CROP=2;

    private static final int PICTURE_CROP_SIZE=200;

    private Uri mCameraPicUri;
    private Uri mCropPicUri;

    @InjectView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @InjectView(R.id.tv_username)
    TextView mUserName;
    @InjectView(R.id.tv_join_time)
    TextView mTvJoinTime;
    @InjectView(R.id.tv_location)
    TextView mTvLocation;
    @InjectView(R.id.tv_dev_platform)
    TextView mTvPlatform;
    @InjectView(R.id.tv_academic_focus)
    TextView mTvFocus;
    @InjectView(R.id.btn_logout)
    Button mBtnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information_detail, container, false);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }

    private void initView() {

        User user = AppContext.getInstance().getUserInfo();
        mUserName.setText(user.getName());
        mTvJoinTime.setText(user.getJointime());
        mTvLocation.setText(user.getLocation());
        mTvPlatform.setText(user.getDevplatform());
        mTvFocus.setText(user.getExpertise());

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContext.getInstance().logout();
                AppContext.showToastShort(R.string.toast_logout_success);
                getActivity().finish();
            }
        });

        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActionChooseDialog();
            }
        });
    }

    private void openActionChooseDialog() {
        DialogHelper.getSelectDialog(getActivity(), "选择操作", getResources().getStringArray(R.array.avatar_option),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case TYPE_CHANGE:
                                openPictureChooseDialog();
                                break;
                            case TYPE_LOOK_BIG:
                                //UIHelper.showUserAvatar();
                                break;
                            default:
                                return;
                        }
                    }
                }).show();
    }

    private void openPictureChooseDialog() {
        DialogHelper.getSelectDialog(getActivity(), "选择图片", getResources().getStringArray(R.array.choose_picture),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case TYPE_ALBUM:
                                startImagePick();
                                break;
                            case TYPE_PHOTO:
                                startTakePhoto();
                                break;
                            default:
                                return;
                        }
                    }
                }).show();
    }

    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19)
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        else
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_CODE_IMAGE_PICK);
    }

    private void startTakePhoto(){
        String filePath="";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filePath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+generatePictureName();
        }else{
            AppContext.showToastShort("无法保存照片,请检查SD卡是否挂载");
            return;
        }
        mCameraPicUri=Uri.parse(filePath);

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraPicUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_IMAGE_PICK:
                    startActionCrop(data.getData());
                    break;
                case REQUEST_CODE_CAMERA:
                    startActionCrop(mCameraPicUri);
                    break;
                case REQUEST_CODE_CROP:
                    try {
                        mIvAvatar.setImageBitmap(BitmapHelper.getAndRescaleBitmap(mCropPicUri.getPath(),200,200));
                    }catch (IOException e){
                        //AppContext.showToastShort("");
                    }
                    break;
                default:
                    return;
            }
        }
    }

    private String generatePictureName(){
        String timeStamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return "osc_"+timeStamp+".jpg";
    }

    private Uri generatePictureSavePath(){

        String filePath="";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filePath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+generatePictureName();
        }else{
            filePath=getActivity().getFilesDir().getAbsolutePath()+"/"+generatePictureName();
        }

        return Uri.parse(filePath);
    }

    private void startActionCrop(Uri data){
        mCropPicUri=generatePictureSavePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", mCropPicUri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", PICTURE_CROP_SIZE);// 输出图片大小
        intent.putExtra("outputY", PICTURE_CROP_SIZE);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边

        startActivityForResult(intent,REQUEST_CODE_CROP);
    }
}
