package com.purchase.sls.shoppingmall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.refreshview.HeaderViewLayout;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.widget.tag.TagLayout;
import com.purchase.sls.common.widget.tag.adapter.TagBaseAdapter;
import com.purchase.sls.data.entity.GoodsItemList;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.GoodsSearchItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/5.
 * 商店搜索
 */

public class GoodsSearchActivity extends BaseActivity {
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.stline)
    View stline;
    @BindView(R.id.delete_history)
    ImageView deleteHistory;
    @BindView(R.id.tagview_history)
    TagLayout tagviewHistory;
    @BindView(R.id.history_search_rl)
    RelativeLayout historySearchRl;

    //历史标签
    private TagBaseAdapter historyTagBaseAdapter;
    private List<String> historySearchList;
    private String historySearchStr;
    private CommonAppPreferences commonAppPreferences;
    private static final String SPLIT = "11235813211123581321";

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodsSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search);
        ButterKnife.bind(this);
        setHeight(searchLl, null, cancel);
        initView();
    }

    private void initView() {
        commonAppPreferences = new CommonAppPreferences(this);
        initEdittext();
        setTagview();
        showHistorySearch();
    }

    private void setTagview() {
        historySearchList = new ArrayList<>();
        historyTagBaseAdapter = new TagBaseAdapter(this, historySearchList);
        tagviewHistory.setLimit(true);
        tagviewHistory.setLimitCount(3);
        tagviewHistory.setAdapter(historyTagBaseAdapter);
        tagviewHistory.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (position < historySearchList.size()) {
                    GoodsSearchItemActivity.start(GoodsSearchActivity.this, historySearchList.get(position),"","");
                    setHistory(historySearchList.get(position));
                }
            }
        });
    }

    /**
     * 显示历史搜索
     */

    private void showHistorySearch() {
        historySearchStr = commonAppPreferences.getHistorySearch();
        historySearchList.clear();
        if (historySearchStr.length() > 0) {
            String[] tags = historySearchStr.split(SPLIT);
            for (int i = 0; i < tags.length; i++) {
                historySearchList.add(tags[i]);
            }
            historyTagBaseAdapter.setList(historySearchList);
        }
    }

    private void initEdittext() {
        searchEt.setFocusable(true);
        searchEt.setFocusableInTouchMode(true);
        searchEt.requestFocus();
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    if (!TextUtils.isEmpty(searchEt.getText().toString())) {
                        setHistory(searchEt.getText().toString());
                        GoodsSearchItemActivity.start(GoodsSearchActivity.this, searchEt.getText().toString(),"","");
                        return true;
                    }
                }
                return false;
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchEt, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
    }

    /**
     * 添加历史搜索数据
     */
    private void setHistory(String tag) {
        historySearchStr = commonAppPreferences.getHistorySearch();
        if (historySearchStr.length() > 0) {
            String[] tags = historySearchStr.split(SPLIT);
            if (Arrays.asList(tags).contains(tag)) {
                if (historySearchStr.startsWith(tag)) {
                } else {
                    historySearchStr = historySearchStr.replace(SPLIT + tag, "");
                    historySearchStr = tag + SPLIT + historySearchStr;
                }
            } else {
                historySearchStr = tag + SPLIT + historySearchStr;
            }
            commonAppPreferences.setHistorySearch(historySearchStr);
        } else {
            historySearchStr = tag;
            commonAppPreferences.setHistorySearch(tag);
        }
        String[] tags = historySearchStr.split(SPLIT);
        historySearchList.clear();
        for (int i = 0; i < tags.length; i++) {
            historySearchList.add(tags[i]);

        }
        historyTagBaseAdapter.setList(historySearchList);
    }


    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.cancel, R.id.delete_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.delete_history:
                commonAppPreferences.setHistorySearch("");
                tagviewHistory.removeAllViews();
                break;
            default:
        }
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
}
