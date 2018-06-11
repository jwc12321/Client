package com.purchase.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.widget.customeview.ActionSheet;
import com.purchase.sls.common.widget.pickphoto.beans.ImgBean;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.mine.DaggerPersonalCenterComponent;
import com.purchase.sls.mine.PersonalCenterContract;
import com.purchase.sls.mine.PersonalCenterModule;
import com.purchase.sls.mine.presenter.PersonalImPresenter;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/11.
 * 修改头像
 */

public class ChangeHeadPortraitActivity extends BaseActivity implements PersonalCenterContract.PersonalImView, ActionSheet.OnPictureChoseListener {
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.change_bt)
    Button changeBt;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.change_head_rl)
    RelativeLayout changeHeadRl;

    private ActionSheet actionSheet;
    private String headPhone;
    @Inject
    PersonalImPresenter personalImPresenter;

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangeHeadPortraitActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_head_portrait);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initVeiw();
    }

    private void initVeiw() {
        persionAppPreferences = new PersionAppPreferences(this);
        persionInfoStr = persionAppPreferences.getPersionInfo();
        gson = new Gson();
        if (persionInfoStr != null && !TextUtils.isEmpty(persionInfoStr)) {
            persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
            GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.app_icon, photo);
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerPersonalCenterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .personalCenterModule(new PersonalCenterModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View getSnackBarHolderView() {
        return changeHeadRl;
    }

    @OnClick({R.id.back, R.id.change_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change_bt:
                if (actionSheet == null) {
                    actionSheet = ActionSheet.newInstance(false, photo.getWidth(), photo.getHeight());
                    actionSheet.setOnPictureChoseListener(ChangeHeadPortraitActivity.this);
                }
                actionSheet.setMax(1,"1");
                actionSheet.show(this);
                break;
        }
    }

    @Override
    public void onPictureChose(File filePath) {
        actionSheet.dismiss();
        headPhone = filePath.getAbsolutePath();
        photo.setImageBitmap(BitmapFactory.decodeFile(headPhone));
        personalImPresenter.changeHeadPortrait(headPhone);
    }

    @Override
    public void onPhotoResult(List<ImgBean> selectedImgs) {

    }

    @Override
    public void setPresenter(PersonalCenterContract.PersonalImPresenter presenter) {

    }

    @Override
    public void changeHeadPortraitSuccess(String phoneUrl) {
        Toast.makeText(getApplicationContext(), "头像修改成功",
                Toast.LENGTH_SHORT).show();
        persionInfoResponse.setAvatar(phoneUrl);
        persionInfoStr = gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoStr);
    }

    @Override
    public void changeUserInfoSuccess() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (actionSheet != null) {
                actionSheet.dismiss();
            }
            this.finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
