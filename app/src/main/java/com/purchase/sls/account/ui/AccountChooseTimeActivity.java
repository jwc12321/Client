package com.purchase.sls.account.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.widget.chooseTime.ChooseTimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountChooseTimeActivity extends BaseActivity implements ChooseTimePicker.OnTimeSelectListener, ChooseTimePicker.OnCancelListener {
    @BindView(R.id.button)
    Button button;
    private ChooseTimePicker chooseTimePicker;
    private int yearSelect = 0;
    private int monthSelect = 0;
    private int daySelect = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountChooseTimeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_choose_time);
        ButterKnife.bind(this);
    }

    private void initView() {

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                chooseTimePicker = new ChooseTimePicker.Builder()
                        .yearSelectGet(yearSelect)
                        .monthSelectGet(monthSelect)
                        .daySelectGet(daySelect)
                        .build();
                chooseTimePicker.setListener(this);
                chooseTimePicker.setCancelListener(this);
                chooseTimePicker.show(this);
                break;
            default:
        }
    }

    @Override
    public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
        yearSelect = backYearSelect;
        monthSelect = backMonthSelect;
        daySelect = backDaySelect;
        Log.d("111", "time==" + time);
    }

    @Override
    public void onCancel() {

    }
}
