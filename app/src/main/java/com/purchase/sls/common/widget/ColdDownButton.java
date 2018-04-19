package com.purchase.sls.common.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JWC on 2018/4/18.
 * 倒计时按钮
 */

public class ColdDownButton extends AppCompatButton {
    private CharSequence mRealString;

    private Timer mTimer;

    private TimerTask mTask;

    private int mCount = 60;
    private TimerState STATE;

    /**
     * 倒计时的二种状态
     */
    enum TimerState{

        NORMAL,//正常状态
        COUNTING,//倒计时中

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            mCount--;
//            if (mCount >= 0) {
//                String text = mCount + "s";
//                setText(text);
//            } else {
//                mCount = 60;
//                setText(mRealString);
//                setEnabled(true);
//                mTask.cancel();
//            }
            if (STATE == TimerState.COUNTING&&mCount>0){

                update();
            }else if (mCount == 0 && STATE == TimerState.COUNTING){

                reset();

            }
            return true;
        }
    });
    /**
     * 重置按钮
     */
    public interface OnResetListener {
        void onReset();
    }
    private OnResetListener onResetListener;

    public void setOnResetListener(OnResetListener onResetListener) {
        this.onResetListener = onResetListener;
    }

    public void reset() {

        setEnabled(true);
        setText(mRealString);
        mCount= 60;
        STATE = TimerState.NORMAL;
        if (onResetListener != null){
            onResetListener.onReset();
        }
    }


    /**
     * 更新按钮状态
     */
    private void update() {
        mCount--;
        String text = mCount + "s";
        setText(text);
        mHandler.sendEmptyMessageDelayed(0x008, 1000);
    }
    public ColdDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSingleLine();
        STATE = TimerState.NORMAL;
        mRealString = getText();
        mTimer = new Timer();
    }

    public ColdDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public void startCold() {
        if (STATE == TimerState.NORMAL) {
            setEnabled(false);
            STATE = TimerState.COUNTING;
//            mTask = new CountDownTask();
            mCount = 60;
            mHandler.sendEmptyMessage(0x008);
//            mTimer.schedule(mTask, 0, 1000);
        }
    }

    public boolean isCounting(){
        return STATE == TimerState.COUNTING;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        mTimer.cancel();
    }

    private class CountDownTask extends TimerTask {

        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    }
}
