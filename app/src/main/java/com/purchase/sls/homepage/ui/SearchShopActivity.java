package com.purchase.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.tag.TagLayout;
import com.purchase.sls.common.widget.tag.adapter.TagBaseAdapter;
import com.purchase.sls.data.entity.HotSearchInfo;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.presenter.HotSearchPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/7.
 */

public class SearchShopActivity extends BaseActivity implements HomePageContract.HotSearchView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.delete_history)
    ImageView deleteHistory;
    @BindView(R.id.tagview_history)
    TagLayout tagviewHistory;
    @BindView(R.id.history_search_rl)
    RelativeLayout historySearchRl;
    @BindView(R.id.delete_text)
    TextView deleteText;
    @BindView(R.id.reflash_iv)
    ImageView reflashIv;
    @BindView(R.id.reflash_text)
    TextView reflashText;
    @BindView(R.id.tagview_hot)
    TagLayout tagviewHot;
    @BindView(R.id.hot_search_rl)
    RelativeLayout hotSearchRl;

    //历史标签
    private TagBaseAdapter historyTagBaseAdapter;
    private List<String> historySearchList;
    private String historySearchStr;
    private CommonAppPreferences commonAppPreferences;
    private static final String SPLIT = "11235813211123581321";

    @Inject
    HotSearchPresenter hotSearchPresenter;

    //热门搜索
    private TagBaseAdapter hotTagBaseAdapter;
    private List<String> hotSearchList;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shop);
        ButterKnife.bind(this);
        setHeight(back, searchLl, cancel);
        initView();
        setTagview();
        setHotTagview();
        showHistorySearch();
        hotSearchPresenter.getHotSearchs();
    }

    private void initView() {
        commonAppPreferences = new CommonAppPreferences(this);
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
                        UmengEventUtils.statisticsClick(SearchShopActivity.this, UMStaticData.KEY, searchEt.getText().toString(), UMStaticData.SEARCH_STORE);
                        ScreeningListActivity.start(SearchShopActivity.this, "", "搜索结果", "", searchEt.getText().toString(), "2");
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


    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.cancel, R.id.delete_history, R.id.reflash_iv, R.id.delete_text, R.id.reflash_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.delete_history:
            case R.id.delete_text:
                commonAppPreferences.setShopHistorySearch("");
                tagviewHistory.removeAllViews();
                break;
            case R.id.reflash_iv:
            case R.id.reflash_text:
                hotSearchPresenter.getHotSearchs();
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
                    setHistory(historySearchList.get(position));
                    UmengEventUtils.statisticsClick(SearchShopActivity.this, UMStaticData.KEY, historySearchList.get(position), UMStaticData.SEARCH_STORE);
                    ScreeningListActivity.start(SearchShopActivity.this, "", "搜索结果", "", historySearchList.get(position), "2");
                }
            }
        });
    }

    private void setHotTagview() {
        hotSearchList = new ArrayList<>();
        hotTagBaseAdapter = new TagBaseAdapter(this, hotSearchList);
        tagviewHot.setLimit(true);
        tagviewHot.setLimitCount(3);
        tagviewHot.setAdapter(hotTagBaseAdapter);
        tagviewHot.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (position < hotSearchList.size()) {
                    setHistory(hotSearchList.get(position));
                    UmengEventUtils.statisticsClick(SearchShopActivity.this, UMStaticData.KEY, hotSearchList.get(position), UMStaticData.SEARCH_STORE);
                    ScreeningListActivity.start(SearchShopActivity.this, "", "搜索结果", "", hotSearchList.get(position), "2");
                }
            }
        });
    }

    /**
     * 显示历史搜索
     */

    private void showHistorySearch() {
        historySearchStr = commonAppPreferences.getShopHistorySearch();
        historySearchList.clear();
        if (historySearchStr.length() > 0) {
            String[] tags = historySearchStr.split(SPLIT);
            for (int i = 0; i < tags.length; i++) {
                historySearchList.add(tags[i]);
            }
            historyTagBaseAdapter.setList(historySearchList);
        }
    }

    /**
     * 添加历史搜索数据
     */
    private void setHistory(String tag) {
        historySearchStr = commonAppPreferences.getShopHistorySearch();
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
            commonAppPreferences.setShopHistorySearch(historySearchStr);
        } else {
            historySearchStr = tag;
            commonAppPreferences.setShopHistorySearch(tag);
        }
        String[] tags = historySearchStr.split(SPLIT);
        historySearchList.clear();
        for (int i = 0; i < tags.length; i++) {
            historySearchList.add(tags[i]);

        }
        historyTagBaseAdapter.setList(historySearchList);
    }

    @Override
    public void renderHotSearchs(List<HotSearchInfo> hotSearchInfos) {
        hotSearchList.clear();
        for (int i = 0; i < hotSearchInfos.size(); i++) {
            hotSearchList.add(hotSearchInfos.get(i).getKeywords());
        }
        hotTagBaseAdapter.setList(hotSearchList);
    }

    @Override
    public void setPresenter(HomePageContract.HotSearchPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerHomePageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);
    }
}

