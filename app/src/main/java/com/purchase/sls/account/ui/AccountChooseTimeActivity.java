package com.purchase.sls.account.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.chooseTime.ChooseTimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountChooseTimeActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.choose_type_ll)
    LinearLayout chooseTypeLl;
    @BindView(R.id.monthly)
    TextView monthly;
    @BindView(R.id.monthly_query_ll)
    LinearLayout monthlyQueryLl;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.start_date_ll)
    LinearLayout startDateLl;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.end_date_ll)
    LinearLayout endDateLl;
    @BindView(R.id.phase_query_ll)
    LinearLayout phaseQueryLl;
    @BindView(R.id.ok_button)
    Button okButton;
    @BindView(R.id.monthly_bt)
    Button monthlyBt;
    @BindView(R.id.phase_bt)
    Button phaseBt;

    private ChooseTimePicker chooseMonthlyTimePicker;
    private int monthlyYearSelect = 0;
    private int monthlyMonthSelect = 0;
    private int monthlyDaySelect = 0;
    private String monthlyTime;

    private ChooseTimePicker chooseStartTimePicker;
    private int startYearSelect = 0;
    private int startMonthSelect = 0;
    private int startDaySelect = 0;
    private String startTime;

    private ChooseTimePicker chooseEndTimePicker;
    private int endYearSelect = 0;
    private int endMonthSelect = 0;
    private int endDaySelect = 0;
    private String endTime;

    private String chooseTimeType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_choose_time);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        monthlyBt.setSelected(true);
        phaseBt.setSelected(false);
        chooseTimeType = "1";
        monthlyQueryLl.setVisibility(View.VISIBLE);
        phaseQueryLl.setVisibility(View.GONE);
        monthlyYearSelect = 100;
        monthlyMonthSelect = FormatUtil.nowMonth();
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.monthly_bt, R.id.phase_bt, R.id.monthly_query_ll, R.id.start_date_ll, R.id.end_date_ll, R.id.ok_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.monthly_bt://月度查询
                monthlyBt.setSelected(true);
                phaseBt.setSelected(false);
                monthlyQueryLl.setVisibility(View.VISIBLE);
                phaseQueryLl.setVisibility(View.GONE);
                chooseTimeType = "1";
                break;
            case R.id.phase_bt://阶段查询
                monthlyBt.setSelected(false);
                phaseBt.setSelected(true);
                monthlyQueryLl.setVisibility(View.GONE);
                phaseQueryLl.setVisibility(View.VISIBLE);
                chooseTimeType = "2";
                break;
            case R.id.monthly_query_ll:
                setMonthlyTimePicker();
                break;
            case R.id.start_date_ll:
                setStartTimePicker();
                break;
            case R.id.end_date_ll:
                setEndTimePicker();
                break;
            case R.id.ok_button:
                okBt();
                break;
            default:
        }
    }


    /**
     * 选择月度查询
     */
    private void setMonthlyTimePicker() {
        chooseMonthlyTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("1")
                .yearSelectGet(monthlyYearSelect)
                .monthSelectGet(monthlyMonthSelect)
                .daySelectGet(monthlyDaySelect)
                .build();
        chooseMonthlyTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                monthlyYearSelect = backYearSelect;
                monthlyMonthSelect = backMonthSelect;
                monthlyDaySelect = backDaySelect;
                monthlyTime = time;
                monthly.setText(time);
            }
        });
        chooseMonthlyTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        chooseMonthlyTimePicker.show(this);
    }

    /**
     * 开启起始时间的时间选择器
     */
    private void setStartTimePicker() {
        chooseStartTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("2")
                .yearSelectGet(startYearSelect)
                .monthSelectGet(startMonthSelect)
                .daySelectGet(startDaySelect)
                .build();
        chooseStartTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                startYearSelect = backYearSelect;
                startMonthSelect = backMonthSelect;
                startDaySelect = backDaySelect;
                startDate.setText(time);
                startTime = time;
            }
        });
        chooseStartTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        chooseStartTimePicker.show(this);
    }


    /**
     * 开启结束时间的时间选择器
     */
    private void setEndTimePicker() {
        chooseEndTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("2")
                .yearSelectGet(endYearSelect)
                .monthSelectGet(endMonthSelect)
                .daySelectGet(endDaySelect)
                .build();
        chooseEndTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                endYearSelect = backYearSelect;
                endMonthSelect = backMonthSelect;
                endDaySelect = backDaySelect;
                endDate.setText(time);
                endTime = time;
            }
        });
        chooseEndTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        chooseEndTimePicker.show(this);
    }

    /**
     * 点击ok回调
     */
    private void okBt() {
        if (TextUtils.equals("1", chooseTimeType)) {
            if (TextUtils.isEmpty(monthly.getText().toString())) {
                Toast.makeText(AccountChooseTimeActivity.this, "请选择时间!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(StaticData.CHOOSE_TIME_TYPE, chooseTimeType);
                intent.putExtra(StaticData.CHOOSE_TIME_FIRST, monthlyTime);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else {
            if (TextUtils.isEmpty(startDate.getText().toString())) {
                Toast.makeText(AccountChooseTimeActivity.this, "请选择开始日期!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(endDate.getText().toString())) {
                Toast.makeText(AccountChooseTimeActivity.this, "请选择结束日期!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(StaticData.CHOOSE_TIME_TYPE, chooseTimeType);
                intent.putExtra(StaticData.CHOOSE_TIME_FIRST, startTime);
                intent.putExtra(StaticData.CHOOSE_TIME_SECOND, endTime);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
