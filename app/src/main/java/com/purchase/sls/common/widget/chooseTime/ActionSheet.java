package com.purchase.sls.common.widget.chooseTime;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.purchase.sls.BaseActivity;

import java.io.File;

/**
 * ActionSheet基类，只有默认的底部弹出动画，子类继承该类，重写getLayoutRes方法即可，由于是继承Fragment
 * 而不是DialogFragment，对于数据传递比较友好，避免继承DialogFragment到导致布局尴尬
 */
public abstract class ActionSheet extends Fragment {

    private static final String TAG = "ActionSheet";
    private static final int BG_ID = 10;
    private static final String EXTRA_DISMISSED = "DISMISS";
    private LayoutInflater inflater;
    private View bgView;
    private View sheetView;
    private FrameLayout sheetWrapper;

    private static final int TRANSLATE_DURATION = 200;
    private static final int ALPHA_DURATION = 300;
    private ViewGroup decorView;

    private File tempFile;
    private boolean bottom;
    private Context context;
    private boolean commiting;
    private boolean mDismissed = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            mDismissed = savedInstanceState.getBoolean(EXTRA_DISMISSED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView() {
        if (inflater == null)
            inflater = LayoutInflater.from(getContext());
        sheetWrapper = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        sheetWrapper.setLayoutParams(params);
        sheetView = inflater.inflate(getLayoutRes(), null);
        bgView = new View(getContext());
        bgView.setId(BG_ID);
        bgView.setLayoutParams(params);
        bgView.setBackgroundColor(Color.argb(100, 255, 255, 255));
        FrameLayout.LayoutParams bottomParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        bottomParams.gravity = Gravity.BOTTOM;
        sheetView.setLayoutParams(bottomParams);
        decorView = (ViewGroup) getActivity().getWindow().getDecorView();
        sheetWrapper.addView(bgView);
        sheetWrapper.addView(sheetView);
        decorView.addView(sheetWrapper);
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        animateViewIn();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ActionSheet");
    }

    protected void init() {
    }

    public View getSheetView() {
        return sheetView;
    }

    protected abstract
    @LayoutRes
    int getLayoutRes();


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
        new Handler().post(new Runnable() {
            public void run() {
                FragmentTransaction ft = ((BaseActivity) context).getSupportFragmentManager().beginTransaction();
                ft.remove(ActionSheet.this);
                ft.commit();
                mDismissed = true;
            }
        });
    }

    public void show(final Context context) {
        if (!mDismissed)
            return;
        this.context = context;
        /*if (context instanceof BaseActivity &&!isAdded()) {
            BaseActivity ba = (BaseActivity) context;
            FragmentManager fm = ba.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(this, null);
            ft.addToBackStack(null);
            ft.commitAllowingStateLoss();
            mDismissed = false;
        }*/
        mDismissed = false;
        new Handler().post(new Runnable() {
            public void run() {
                FragmentTransaction ft = ((BaseActivity) context).getSupportFragmentManager().beginTransaction();
                ft.add(ActionSheet.this, getClass().getSimpleName());
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_DISMISSED, mDismissed);
    }

}
