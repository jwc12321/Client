package com.purchase.sls.common.widget.chooseTime;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.purchase.sls.R;
import com.purchase.sls.common.widget.chooseTime.adapter.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/1/13.
 */

public class ChooseTimePicker extends ActionSheet {


    private static final String TAG = "ChooseTimePicker";
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.year_wv)
    WheelView mYearWheelView;
    @BindView(R.id.month_wv)
    WheelView mMonthWheelView;
    @BindView(R.id.day_wv)
    WheelView mDayWheelView;
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDayAdapter;

    //变量
    private ArrayList<String> arry_year = new ArrayList<String>();
    private ArrayList<String> arry_month = new ArrayList<String>();
    private ArrayList<String> arry_day = new ArrayList<String>();

    private int nowYearId = 0;
    private int nowMonthId = 0;
    private int nowDayId = 0;

    private int nowYearPosition=0;

    private String mYearStr;
    private String mMonthStr;
    private String mDayStr;

    private int backYearSelect=0;
    private int backMonthSelect=0;
    private int backDaySelect=0;

    private int selectYearInt;
    private int selectMonthInt;


    //常量
    private final int MAX_TEXT_SIZE = 16;
    private final int MIN_TEXT_SIZE = 14;

    private boolean monthAll=false;
    private boolean yearAll=false;

    private View sheetView;

    private static final String KEY_YEAR_SELECT="key_year_select";
    private static final String KEY_MONTH_SELECT="key_month_select";
    private static final String KEY_DAY_SELECT="key_day_select";


    public static ChooseTimePicker newInstance() {
        ChooseTimePicker chooseTimePicker = new ChooseTimePicker();
        return chooseTimePicker;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.action_sheet_choose_time_schedule;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        sheetView = getSheetView();
        ButterKnife.bind(this, sheetView);
        Bundle bundle =getArguments();
        nowYearId=bundle.getInt(KEY_YEAR_SELECT);
        nowMonthId=bundle.getInt(KEY_MONTH_SELECT);
        nowDayId=bundle.getInt(KEY_DAY_SELECT);
        monthAll=false;
        yearAll=false;
        initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initData() {
        initYear();
        initMonth();
        initDay();
        initListener();
        initNowData();
    }

    private void initNowData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mYearWheelView.setCurrentItem(nowYearId);
                mMonthWheelView.setCurrentItem(nowMonthId);
                mDayWheelView.setCurrentItem(nowDayId);
            }
        }, 100);
    }

    private void initDayDate(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mDayWheelView.setCurrentItem(0);
            }
        }, 100);
    }




    /**
     * 初始化年
     */
    private void initYear() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        arry_year.clear();
        arry_year.add("全部");
        for (int i = 0; i <= 5; i++) {
            int year = nowYear - 2 + i;
            arry_year.add(year + "年");
            if(year==nowYear){
                nowYearPosition = arry_year.size() - 1;
            }
        }
        mYearAdapter = new CalendarTextAdapter(getActivity(), arry_year, nowYearId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mYearWheelView.setVisibleItems(5);
        mYearWheelView.setViewAdapter(mYearAdapter);
        mYearWheelView.setCurrentItem(nowYearId);
        mYearStr = arry_year.get(nowYearId);
//        setTextViewStyle(mYearStr, mYearAdapter);
    }

    private int getYearMonth(String dateStr, String splitStr) {
        if(!TextUtils.equals("全部",dateStr)) {
            String[] dateTemp = null;
            dateTemp = dateStr.split(splitStr);
            return Integer.parseInt(dateTemp[0]);
        }
        return -1;
    }


    /**
     * 初始化月
     */

    private void initMonth() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
        arry_month.clear();
        arry_month.add("全部");
        for (int i = 1; i <= 12; i++) {
            arry_month.add(i + "月");
        }
        mMonthAdapter = new CalendarTextAdapter(getActivity(), arry_month, nowMonth, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMonthWheelView.setVisibleItems(5);
        mMonthWheelView.setViewAdapter(mMonthAdapter);
        mMonthWheelView.setCurrentItem(nowMonthId);
        mMonthStr = arry_month.get(nowMonthId);
//        setTextViewStyle(mMonthStr, mMonthAdapter);
    }


    /**
     * 初始化天
     */
    private void initDay() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        int nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
        setDay(nowYear, nowMonth);
        mDayAdapter = new CalendarTextAdapter(getActivity(), arry_day, nowDayId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mDayWheelView.setVisibleItems(5);
        mDayWheelView.setViewAdapter(mDayAdapter);
        mDayWheelView.setCurrentItem(nowDayId);
        mDayStr = arry_day.get(nowDayId);
//        setTextViewStyle(mDayStr, mDayAdapter);
    }

    /**
     * 将改年的所有日期写入数组
     *
     * @param year
     */
    private void setDay(int year, int month) {
        arry_day.clear();
        arry_day.add("全部");
        boolean isRun = isRunNian(year);
        Calendar nowCalendar = Calendar.getInstance();
        int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for (int day = 1; day <= 31; day++) {
                    arry_day.add(day + "日");
                }
                break;
            case 2:
                if (isRun) {
                    for (int day = 1; day <= 29; day++) {
                        arry_day.add(day + "日");
                    }
                } else {
                    for (int day = 1; day <= 28; day++) {
                        arry_day.add(day + "日");
                    }
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for (int day = 1; day <= 30; day++) {
                    arry_day.add(day + "日");
                }
                break;
            default:
                break;
        }
    }


    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    private boolean isRunNian(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 初始化滚动监听事件
     */
    private void initListener() {
        //年份*****************************
        mYearWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                backYearSelect=wheel.getCurrentItem();
                if (wheel.getCurrentItem() > nowYearPosition) {
                    mYearWheelView.setCurrentItem(nowYearPosition);
                } else {
                    String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                    setTextViewStyle(currentText, mYearAdapter);
                    mYearStr = arry_year.get(wheel.getCurrentItem()) + "";
                    if (currentText.equals("全部")) {
                        yearAll=true;
                        monthAll=true;
                        mMonthWheelView.setCurrentItem(0);
                        mMonthWheelView.setCanScroll(false);
                    } else if (TextUtils.equals("2月", mMonthStr)) {
                        yearAll=false;
                        monthAll=false;
                        selectYearInt = getYearMonth(mYearStr, "年");
                        setDay(selectYearInt, 2);
                        mDayAdapter.setList(arry_day);
                        mDayWheelView.setViewAdapter(mDayAdapter);
                        mDayWheelView.setCurrentItem(0);
                        mMonthWheelView.setCanScroll(true);
                    } else {
                        yearAll=false;
                        monthAll=false;
                        if(TextUtils.equals("全部",mMonthStr)){
                            mMonthWheelView.setCurrentItem(0);
                        }
                        mMonthWheelView.setCanScroll(true);
                    }
                }
            }
        });

        mYearWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
            }
        });

        //月********************
        mMonthWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                backMonthSelect=wheel.getCurrentItem();
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setMonthTextViewStyle(currentText, mMonthAdapter);
                mMonthStr = arry_month.get(wheel.getCurrentItem());
                if (TextUtils.equals(currentText, "全部")) {
                    monthAll=true;
                    mDayWheelView.setCurrentItem(0);
                    mDayWheelView.setCanScroll(false);
                } else {
                    monthAll=false;
                    mDayWheelView.setCanScroll(true);
                    selectYearInt = getYearMonth(mYearStr, "年");
                    selectMonthInt = getYearMonth(mMonthStr, "月");
                    setDay(selectYearInt, selectMonthInt);
                    mDayAdapter.setList(arry_day);
                    mDayWheelView.setViewAdapter(mDayAdapter);
                    mDayWheelView.setCurrentItem(0);
                }
            }
        });

        mMonthWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMonthAdapter);
//                if (TextUtils.equals(currentText, "全部")) {
//                    monthAll=true;
//                    mDayWheelView.setCurrentItem(0);
//                    mDayWheelView.setCanScroll(false);
//                } else {
//                    monthAll=false;
//                    mDayWheelView.setCanScroll(true);
//                    selectYearInt = getYearMonth(mYearStr, "年");
//                    selectMonthInt = getYearMonth(mMonthStr, "月");
//                    setDay(selectYearInt, selectMonthInt);
//                    mDayAdapter.setList(arry_day);
//                    mDayWheelView.setViewAdapter(mDayAdapter);
//                    mDayWheelView.setCurrentItem(0);
//                }
            }
        });


        //天********************

        mDayWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                backDaySelect=wheel.getCurrentItem();
                String currentText = (String) mDayAdapter.getItemText(wheel.getCurrentItem());
                setDayTextViewStyle(currentText, mDayAdapter);
                mDayStr = arry_day.get(wheel.getCurrentItem());
            }
        });


        mDayWheelView.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDayAdapter.getItemText(wheel.getCurrentItem());
                setDayTextViewStyle(currentText, mDayAdapter);
            }
        });
    }


    /**
     * 滚轮的adapter
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        public void setList(ArrayList<String> list) {
            this.list = list;
            notifyDataChangedEvent();
        }

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, R.id.tempValue, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index) + "";
            return str;
        }
    }



    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
                if (curriteItemText.equals(currentText)) {
                    textvew.setTextSize(MAX_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText5));
                } else {
                    textvew.setTextSize(MIN_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
                }
        }
    }


    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setMonthTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if(yearAll){
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
            }else {
                if (curriteItemText.equals(currentText)) {
                    textvew.setTextSize(MAX_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText5));
                } else {
                    textvew.setTextSize(MIN_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
                }
            }
        }
    }

    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setDayTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if(monthAll){
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
            }else {
                if (curriteItemText.equals(currentText)) {
                    textvew.setTextSize(MAX_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText5));
                } else {
                    textvew.setTextSize(MIN_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
                }
            }
        }
    }

    /**
     * 设置文字的不选中大小
     *
     * @param adapter
     */
    public void setTextViewEnableStyle(CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            textvew.setTextSize(MIN_TEXT_SIZE);
            textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
        }
    }


    public static class Builder {
        OnTimeSelectListener listener;
        int yearSelect;
        int monthSelect;
        int daySelect;

        public  Builder yearSelectGet(int yearSelect){
            this.yearSelect=yearSelect;
            return this;
        }
        public  Builder monthSelectGet(int monthSelect){
            this.monthSelect=monthSelect;
            return this;
        }
        public  Builder daySelectGet(int daySelect){
            this.daySelect=daySelect;
            return this;
        }


        public ChooseTimePicker build() {
            Bundle args=new Bundle();
            args.putInt(KEY_YEAR_SELECT,yearSelect);
            args.putInt(KEY_MONTH_SELECT,monthSelect);
            args.putInt(KEY_DAY_SELECT,daySelect);
            ChooseTimePicker chooseTimePicker = ChooseTimePicker.newInstance();
            chooseTimePicker.setArguments(args);
            chooseTimePicker.setListener(listener);
            return chooseTimePicker;
        }

        public Builder cb(OnTimeSelectListener listener) {
            this.listener = listener;
            return this;
        }
    }


    public interface OnTimeSelectListener {
        void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect);
    }

    public void setListener(OnTimeSelectListener listener) {
        this.listener = listener;
    }

    public OnTimeSelectListener listener;


    public interface OnCancelListener {
        void onCancel();
    }

    public void setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (cancelListener != null) {
            cancelListener.onCancel();
        }
    }

    public OnCancelListener cancelListener;

    @OnClick({R.id.cancel, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                if (cancelListener != null) {
                    cancelListener.onCancel();
                }
                dismiss();
                break;
            case R.id.confirm:
                if (listener != null) {
                    Log.d(TAG, "mYearStr:" + mYearStr + "mMonthStr:" + mMonthStr + "mDayStr:" + mDayStr);
                    if (TextUtils.equals("全部", mYearStr)) {
                        listener.onConfirmServiceTime(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, 0),backYearSelect,backMonthSelect,backDaySelect);
                    } else {
                        if (TextUtils.equals("全部", mMonthStr)) {
                            listener.onConfirmServiceTime(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, 1),backYearSelect,backMonthSelect,backDaySelect);
                        } else {
                            if (TextUtils.equals("全部", mDayStr)) {
                                listener.onConfirmServiceTime(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, 2),backYearSelect,backMonthSelect,backDaySelect);
                            } else {
                                listener.onConfirmServiceTime(strTimeToDateFormat(mYearStr, mMonthStr, mDayStr, 3),backYearSelect,backMonthSelect,backDaySelect);
                            }
                        }
                    }
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    private String strTimeToDateFormat(String yearStr, String monthStr, String dayStr, int returnYype) {
        if (monthStr.length() == 2) {
            monthStr = "0" + monthStr;
        }
        if(dayStr.length()==2){
            dayStr="0"+dayStr;
        }
        if (returnYype == 0) {
            return "全部";
        } else if (returnYype == 1) {
            return yearStr.replace("年", "");
        } else if (returnYype == 2) {
            return yearStr.replace("年", "-") + monthStr.replace("月", "");
        } else {
            return yearStr.replace("年", "-") + monthStr.replace("月", "-") + dayStr.replace("日", "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
