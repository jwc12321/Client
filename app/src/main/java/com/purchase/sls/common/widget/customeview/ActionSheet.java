package com.purchase.sls.common.widget.customeview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.BaseFragment;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.R;
import com.purchase.sls.common.unit.ImageSizeUtil;
import com.purchase.sls.common.unit.PermissionUtil;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ActionSheet extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ActionSheet";
    private static final int BG_ID = 0x0f;
    private static final String KEY_COMPRESS = "COMPRESS_OR_NOT";
    private static final String KEY_WIDTH = "COMPRESS_WIDTH";
    private static final String KEY_HEIGHT = "COMPRESS_HEIGHT";
    private static final String TEMP_FILE = "TEMPFILE";
    private static final int PERMISSION_CAMERA = 1;
    private static final int PERMISSION_READ_SD = 2;
    private LayoutInflater inflater;
    private View bgView;
    private View sheetView;
    private FrameLayout sheetWrapper;

    private static final int TRANSLATE_DURATION = 200;
    private static final int ALPHA_DURATION = 300;
    private ViewGroup decorView;
    private OnPictureChoseListener mOnPictureChoseListener;

    private static final int CODE_TAKE_PHOTO = 1;
    private static final int CODE_GALLERY = 2;

    private static final int CODE_CUT = 3;


    private String filePath = BuildConfig.PICTURE_PATH;
    private File tempFile;
    private boolean bottom;
    private Context context;
    private boolean mDismissed = true;
    private boolean compress;
    private int compressWidth;
    private int compressHeight;

    public static ActionSheet newInstance(boolean compress, int width, int height) {

        Bundle args = new Bundle();
        args.putBoolean(KEY_COMPRESS, compress);
        args.putInt(KEY_WIDTH, width);
        args.putInt(KEY_HEIGHT, height);
        ActionSheet fragment = new ActionSheet();
        fragment.setArguments(args);
        return fragment;
    }

    public static ActionSheet newInstance() {
        return newInstance(false, 480, 480);
    }


    //部分手机上如samsung就有问题~必须保存tempFile信息，方便恢复
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            tempFile = (File) savedInstanceState.getSerializable(TEMP_FILE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        Bundle args = getArguments();
        compress = args.getBoolean(KEY_COMPRESS);
        compressWidth = args.getInt(KEY_WIDTH);
        compressHeight = args.getInt(KEY_HEIGHT);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView() {
        if (inflater == null)
            inflater = LayoutInflater.from(getContext());
        sheetWrapper = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        sheetWrapper.setLayoutParams(params);
        sheetView = inflater.inflate(R.layout.action_sheet, null);
        bgView = new View(getContext());
        bgView.setId(BG_ID);
        bgView.setLayoutParams(params);
        bgView.setBackgroundColor(Color.argb(100, 256, 256, 256));
        FrameLayout.LayoutParams bottomParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        bottomParams.gravity = Gravity.BOTTOM;
        sheetView.setLayoutParams(bottomParams);
        decorView = (ViewGroup) getActivity().getWindow().getDecorView();
        sheetWrapper.addView(bgView);
        sheetWrapper.addView(sheetView);
        decorView.addView(sheetWrapper);
        bindAction();
        animateViewIn();
    }

    private void bindAction() {
        TextView gallery = (TextView) sheetView.findViewById(R.id.gallery_choose);
        TextView camera = (TextView) sheetView.findViewById(R.id.take_photo);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);
        bgView.setOnClickListener(this);
    }

    private void animateViewIn() {
        bgView.startAnimation(createAlphaInAnimation());
        sheetView.startAnimation(createTranslateAlphaInAnimation());
    }

    private void animateViewOut() {
        bgView.startAnimation(createAlphaOutAnimation());
        sheetView.startAnimation(createTranslateOutAnimation());
    }

    private Animation createTranslateOutAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
                0, type, 1);
        an.setDuration(TRANSLATE_DURATION);
        an.setFillAfter(true);
        return an;
    }

    private Animation createAlphaOutAnimation() {
        AlphaAnimation an = new AlphaAnimation(1, 0);
        an.setDuration(ALPHA_DURATION);
        an.setFillAfter(true);
        return an;
    }

    private Animation createTranslateAlphaInAnimation() {
        Animation alphaOutAnimation = createAlphaInAnimation();
        Animation translationOutAnimation = createTranslationInAnimation();
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaOutAnimation);
        animationSet.addAnimation(translationOutAnimation);
        return animationSet;
    }

    private Animation createTranslationInAnimation() {
        int type = Animation.RELATIVE_TO_SELF;
        Animation an = new TranslateAnimation(type, 0, type, 0, type, 0, type, 0);
        return an;
    }


    /**
     * 淡入动画
     *
     * @return
     */
    private Animation createAlphaInAnimation() {
        AlphaAnimation an = new AlphaAnimation(0, 1);
        an.setDuration(ALPHA_DURATION);
        return an;
    }


    @Override
    public void onDestroyView() {
        animateViewOut();
        decorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                decorView.removeView(sheetWrapper);
                decorView = null;
            }
        }, Math.max(ALPHA_DURATION, TRANSLATE_DURATION));
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void dismiss() {
       /* if(context instanceof BaseActivity) {
            BaseActivity ba = (BaseActivity) context;
            FragmentManager fm = ba.getSupportFragmentManager();
            fm.popBackStack();
        }*/
        if (mDismissed)
            return;
        FragmentManager fm = getFragmentManager();
//        fm.popBackStack();
//        fm.executePendingTransactions();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(this).commit();
        mDismissed = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPictureChoseListener)
            mOnPictureChoseListener = (OnPictureChoseListener) context;
    }

    public void show(Context context) {
        if (!mDismissed)
            return;
        this.context = context;
        if (context instanceof BaseActivity && !isAdded()) {
            BaseActivity ba = (BaseActivity) context;
            FragmentManager fm = ba.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(this, null);
            ft.addToBackStack(null);
            ft.commitAllowingStateLoss();
            mDismissed = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:
                selectPictureViaTakingPhoto();
                break;
            case R.id.gallery_choose:
                chooseFromGallery();
                break;
            default:
                dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_READ_SD:
                for (int gra :grantResults){
                    if (gra != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                }
                //打开相册选择图片
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY);
                break;
            case PERMISSION_CAMERA:
                for (int gra :grantResults){
                    if (gra != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                }
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // 设置系统相机拍照后的输出路径
                    // 创建临时文件
                    tempFile = createTempFile(filePath);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    startActivityForResult(cameraIntent, CODE_TAKE_PHOTO);
                } else {
                    Toast.makeText(getActivity(), "没有可用的相机", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /*-------------------------------------------------------*/
    void selectPictureViaTakingPhoto() {
        List<String> groups = new ArrayList<>();
        groups.add(Manifest.permission_group.CAMERA);
        groups.add(Manifest.permission_group.STORAGE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(groups,null),PERMISSION_CAMERA)){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // 设置系统相机拍照后的输出路径
                // 创建临时文件
                tempFile = createTempFile(filePath);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(cameraIntent, CODE_TAKE_PHOTO);
            } else {
                Toast.makeText(getActivity(), "没有可用的相机", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void chooseFromGallery() {

        List<String> groups = new ArrayList<>();
        groups.add(Manifest.permission_group.STORAGE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(groups,null),PERMISSION_READ_SD)){
            //打开相册选择图片
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, CODE_GALLERY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: " + (data != null));
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String picturePath;
                        if ("file".equals(selectedImage.getScheme())) {
                            picturePath = selectedImage.getPath();
                            tempFile = createTempFile(filePath);
                            if (compress) {
                                ImageSizeUtil.compressAndSaveToFile(picturePath, tempFile.getAbsolutePath(), compressWidth, compressHeight);
                                dismiss();
                                mOnPictureChoseListener.onPictureChose(tempFile);
                            } else {
                                crop(picturePath, 1, 1, 400, 400);
                            }
                        } else if ("content".equals(selectedImage.getScheme())) {

                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                                picturePath = cursor.getString(columnIndex);
                                cursor.close();
                                tempFile = createTempFile(filePath);
                                if (compress) {
                                    ImageSizeUtil.compressAndSaveToFile(picturePath, tempFile.getAbsolutePath(), compressWidth, compressHeight);
                                    dismiss();
                                    mOnPictureChoseListener.onPictureChose(tempFile);
                                } else crop(picturePath, 1, 1, 400, 400);
                            } else {
                                Toast.makeText(getActivity(), "图片获取失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), "图片获取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case CODE_TAKE_PHOTO:
                    if (compress) {
//                        dismiss();
//                        ImageSizeUtil.compressAndSaveToFile(tempFile.getAbsolutePath(), null, compressWidth, compressHeight);
                        mOnPictureChoseListener.onPictureChose(ImageSizeUtil.compressAndSaveToFile(tempFile.getAbsolutePath(), null, compressWidth, compressHeight));
                    } else
                        crop(tempFile.getAbsolutePath(), 1, 1, 400, 400);
                    break;
                case CODE_CUT:
                    dismiss();
                    mOnPictureChoseListener.onPictureChose(tempFile);
                    break;
                default:
            }
        }
    }

    private File createTempFile(String filePath) {
        String timeStamp = new SimpleDateFormat("MMddHHmmss", Locale.CHINA).format(new Date());
        String externalStorageState = Environment.getExternalStorageState();
        File dir = new File(Environment.getExternalStorageDirectory() + filePath);
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return new File(dir, timeStamp + ".jpg");
        } else {
            File cacheDir = getActivity().getCacheDir();
            return new File(cacheDir, timeStamp + ".jpg");
        }
    }

    //裁剪
    private void crop(String imagePath, int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, CODE_CUT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tempFile != null)
            outState.putSerializable(TEMP_FILE,tempFile);
    }

    public void setOnPictureChoseListener(OnPictureChoseListener mOnPictureChoseListener) {
        this.mOnPictureChoseListener = mOnPictureChoseListener;
    }

    public interface OnPictureChoseListener extends Serializable {

        void onPictureChose(File filePath);
    }
}
