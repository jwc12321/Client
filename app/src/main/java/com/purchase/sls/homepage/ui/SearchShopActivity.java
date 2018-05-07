package com.purchase.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/7.
 */

public class SearchShopActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        searchEt.setFocusable(true);
        searchEt.setFocusableInTouchMode(true);
        searchEt.requestFocus();
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(searchEt.getText().toString()))
                        ScreeningListActivity.start(SearchShopActivity.this, "", "", searchEt.getText().toString(), "", searchEt.getText().toString());
                    return true;
                }
                return false;
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchEt,InputMethodManager.SHOW_FORCED);
            }
        }, 1000);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
        }
    }
}
