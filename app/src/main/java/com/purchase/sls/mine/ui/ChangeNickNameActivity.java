package com.purchase.sls.mine.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.mine.DaggerPersonalCenterComponent;
import com.purchase.sls.mine.PersonalCenterContract;
import com.purchase.sls.mine.PersonalCenterModule;
import com.purchase.sls.mine.presenter.PersonalImPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/4.
 * 修改昵称
 */

public class ChangeNickNameActivity extends BaseActivity implements PersonalCenterContract.PersonalImView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.preservation)
    TextView preservation;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.nickname_tv)
    TextView nicknameTv;
    @BindView(R.id.nickname_et)
    EditText nicknameEt;
    @BindView(R.id.clean_iv)
    ImageView cleanIv;

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;
    private String nickName;

    @Inject
    PersonalImPresenter personalImPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        persionAppPreferences = new PersionAppPreferences(this);
        persionInfoStr = persionAppPreferences.getPersionInfo();
        gson = new Gson();
        if (persionInfoStr != null && !TextUtils.isEmpty(persionInfoStr)) {
            persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
            nickName=persionInfoResponse.getNickname();
        }
        nicknameEt.setText(nickName);
        nicknameEt.setSelection(nicknameEt.getText().length());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(nicknameEt, InputMethodManager.SHOW_FORCED);
            }
        }, 1000);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back,R.id.preservation,R.id.clean_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.preservation:
                if(TextUtils.isEmpty(nicknameEt.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请填写昵称",
                            Toast.LENGTH_SHORT).show();
                }else {
                    personalImPresenter.changeUserInfo(nicknameEt.getText().toString(),"","");
                }
                break;
            case R.id.clean_iv:
                nicknameEt.setText("");
                break;
                default:
        }
    }

    @Override
    public void setPresenter(PersonalCenterContract.PersonalImPresenter presenter) {

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
    public void changeHeadPortraitSuccess(String phoneUrl) {

    }

    @Override
    public void changeUserInfoSuccess() {
        persionInfoResponse.setNickname(nicknameEt.getText().toString());
        persionInfoStr=gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoStr);
        Intent intent=new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(nicknameEt.getWindowToken(),0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideKeyboard();
    }
}
