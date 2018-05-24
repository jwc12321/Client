package com.purchase.sls.mine.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.account.ui.AccountChooseTimeActivity;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.unit.PersionAppPreferences;
import com.purchase.sls.common.widget.chooseTime.ChooseTimePicker;
import com.purchase.sls.common.widget.customeview.ActionSheet;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.mine.DaggerPersonalCenterComponent;
import com.purchase.sls.mine.PersonalCenterContract;
import com.purchase.sls.mine.PersonalCenterModule;
import com.purchase.sls.mine.presenter.PersonalImPresenter;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/4.
 * 个人信息
 */

public class PersonalInformationActivity extends BaseActivity implements PersonalCenterContract.PersonalImView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.arrow_first)
    ImageView arrowFirst;
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.item_head_portrait)
    RelativeLayout itemHeadPortrait;
    @BindView(R.id.arrow_second)
    ImageView arrowSecond;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.item_nickname)
    RelativeLayout itemNickname;
    @BindView(R.id.arrow_third)
    ImageView arrowThird;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.item_sex)
    RelativeLayout itemSex;
    @BindView(R.id.arrow_fourth)
    ImageView arrowFourth;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.item_birthday)
    RelativeLayout itemBirthday;
    @BindView(R.id.preservation)
    TextView preservation;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.view_first)
    View viewFirst;
    @BindView(R.id.female)
    Button female;
    @BindView(R.id.view_second)
    View viewSecond;
    @BindView(R.id.male)
    Button male;
    @BindView(R.id.black_background)
    LinearLayout blackBackground;
    @BindView(R.id.choose_sex_rl)
    RelativeLayout chooseSexRl;

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;
    private String nickName;

    private ChooseTimePicker chooseTimePicker;
    private int yearSelect = 0;
    private int monthSelect = 0;
    private int daySelect = 0;
    private String sexType;
    private String birthdayTime;
    private String choseWhat;
    @Inject
    PersonalImPresenter personalImPresenter;
    private static final int CHANGE_NIKENAME = 199;

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalInformationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_information);
        ButterKnife.bind(this);
        setHeight(back,title,preservation);
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(this);
        persionInfoStr = persionAppPreferences.getPersionInfo();
        gson = new Gson();
        if (persionInfoStr != null && !TextUtils.isEmpty(persionInfoStr)) {
            persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
            GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.app_icon, photo);
            nickName=persionInfoResponse.getNickname();
            nickname.setText(nickName);
            if (TextUtils.equals("0", persionInfoResponse.getSex())) {
                sex.setText("男");
                sexType = "0";
            } else {
                sex.setText("女");
                sexType = "1";
            }
            birthdayTime = persionInfoResponse.getBirthday();
            birthday.setText(birthdayTime);
            blackBackground.setAlpha(0.4f);
        }
        yearSelect = 100;
        monthSelect = FormatUtil.nowMonth();
        daySelect = FormatUtil.nowDay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
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
        return null;
    }

    @OnClick({R.id.back, R.id.preservation, R.id.item_head_portrait, R.id.item_nickname, R.id.item_sex, R.id.item_birthday, R.id.cancel, R.id.male, R.id.female, R.id.black_background})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.preservation:
                break;
            case R.id.item_head_portrait:
                ChangeHeadPortraitActivity.start(this);
                break;
            case R.id.item_nickname:
                Intent intent = new Intent(this, ChangeNickNameActivity.class);
                startActivityForResult(intent, CHANGE_NIKENAME);
                break;
            case R.id.item_sex:
                choseWhat = "1";
                visSex();
                break;
            case R.id.item_birthday:
                choseWhat = "2";
                setTimePicker();
                break;
            case R.id.cancel:
                goneSex();
                break;
            case R.id.male:
                sex.setText("男");
                sexType = "0";
                personalImPresenter.changeUserInfo("", sexType, "");
                goneSex();
                break;
            case R.id.female:
                sex.setText("女");
                sexType = "1";
                personalImPresenter.changeUserInfo("", sexType, "");
                goneSex();
                break;
            case R.id.black_background:
                goneSex();
                break;
            default:
        }
    }


    /**
     * 开启结束时间的时间选择器
     */
    private void setTimePicker() {
        chooseTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("2")
                .yearSelectGet(yearSelect)
                .monthSelectGet(monthSelect)
                .daySelectGet(daySelect)
                .build();
        chooseTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                yearSelect = backYearSelect;
                monthSelect = backMonthSelect;
                daySelect = backDaySelect;
                birthday.setText(time);
                birthdayTime = time;
                personalImPresenter.changeUserInfo("", "", birthdayTime);
            }
        });
        chooseTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {
                chooseTimePicker=null;
            }
        });
        chooseTimePicker.show(this);
    }

    @Override
    public void setPresenter(PersonalCenterContract.PersonalImPresenter presenter) {

    }

    @Override
    public void changeHeadPortraitSuccess(String phoneUrl) {
        persionInfoResponse.setAvatar(phoneUrl);
        persionInfoStr = gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoStr);
    }

    @Override
    public void changeUserInfoSuccess() {
        if (TextUtils.equals("1", choseWhat)) {
            persionInfoResponse.setSex(sexType);
        } else if (TextUtils.equals("2", choseWhat)) {
            persionInfoResponse.setBirthday(birthdayTime);
        }
        persionInfoStr = gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoStr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CHANGE_NIKENAME) {
            persionInfoStr = persionAppPreferences.getPersionInfo();
            if (persionInfoStr != null && !TextUtils.isEmpty(persionInfoStr)) {
                persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
                nickname.setText(persionInfoResponse.getNickname());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            this.finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(chooseTimePicker!=null){
            chooseTimePicker.dismiss();
            chooseTimePicker=null;
        }
    }

    private void visSex(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        //动画持续时间
        alphaAnimation.setDuration(300);
        //动画停留在结束的位置
        alphaAnimation.setFillAfter(true);
        //开启动画
        chooseSexRl.startAnimation(alphaAnimation);
        chooseSexRl.setVisibility(View.VISIBLE);
    }

    private void goneSex(){
        blackBackground.setEnabled(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        //动画持续时间
        alphaAnimation.setDuration(300);
        //开启动画
        chooseSexRl.startAnimation(alphaAnimation);
        chooseSexRl.setVisibility(View.GONE);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始时回调
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                blackBackground.setEnabled(true);
                //动画结束时回调
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复时回调
            }
        });
    }
}
