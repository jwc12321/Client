package com.purchase.sls.nearbymap.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.unit.StaticHandler;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.tag.TagLayout;
import com.purchase.sls.common.widget.tag.adapter.TagBaseAdapter;
import com.purchase.sls.data.entity.MapAddressInfo;
import com.purchase.sls.data.entity.MapMarkerInfo;
import com.purchase.sls.data.entity.NearbyInfoResponse;
import com.purchase.sls.homepage.ui.ScreeningListActivity;
import com.purchase.sls.nearbymap.DaggerNearbyMapComponent;
import com.purchase.sls.nearbymap.NearbyMapContract;
import com.purchase.sls.nearbymap.NearbyMapModule;
import com.purchase.sls.nearbymap.adapter.MapAddressAdapter;
import com.purchase.sls.nearbymap.adapter.MapMarkerStoreAdapter;
import com.purchase.sls.nearbymap.adapter.NearbyItemAdapter;
import com.purchase.sls.nearbymap.adapter.NearbyMunuAdapter;
import com.purchase.sls.nearbymap.presenter.NearbyMapPresenter;
import com.purchase.sls.shopdetailbuy.ui.ShopDetailActivity;

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

public class SearchAddressActivity extends BaseActivity implements NearbyMapContract.NearbyView, PoiSearch.OnPoiSearchListener, MapAddressAdapter.OnEventClickListener, NearbyMunuAdapter.OnMenuItemClickListener, NearbyItemAdapter.OnItemClickListener, MapMarkerStoreAdapter.OnMapMarkerClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.delete_history)
    ImageView deleteHistory;
    @BindView(R.id.delete_text)
    TextView deleteText;
    @BindView(R.id.tagview_history)
    TagLayout tagviewHistory;
    @BindView(R.id.history_search_rl)
    RelativeLayout historySearchRl;
    @BindView(R.id.address_rv)
    RecyclerView addressRv;
    @BindView(R.id.nearby_munu_ry)
    RecyclerView nearbyMunuRy;
    @BindView(R.id.nearby_item_ry)
    RecyclerView nearbyItemRy;
    @BindView(R.id.map_store_rv)
    RecyclerView mapStoreRv;
    @BindView(R.id.nearby_ll)
    LinearLayout nearbyLl;
    //历史标签
    private TagBaseAdapter historyTagBaseAdapter;
    private List<String> historySearchList;
    private String historySearchStr;
    private CommonAppPreferences commonAppPreferences;
    private static final String SPLIT = "11235813211123581321";
    private static final int UPDATE_EDITTEXT = 99;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<MapAddressInfo> mapAddressInfos;

    private MapAddressAdapter mapAddressAdapter;
    private NearbyMunuAdapter nearbyMunuAdapter;
    private NearbyItemAdapter nearbyItemAdapter;
    private MapMarkerStoreAdapter mapMarkerStoreAdapter;

    private String latitude;
    private String longitude;
    private String district;
    private String currLatitude;
    private String currLongitude;
    private List<NearbyInfoResponse> nearbyInfoResponses;
    private int munuLastPosition = 0;
    private int itemLastPosition = 0;

    private boolean canSearch = true;

    @Inject
    NearbyMapPresenter nearbyMapPresenter;

    public static void start(Context context, String district, String latitude,String longitude) {
        Intent intent = new Intent(context, SearchAddressActivity.class);
        intent.putExtra(StaticData.DISTRICT, district);
        intent.putExtra(StaticData.LATITUDE,latitude);
        intent.putExtra(StaticData.LONGITUDE,longitude);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        ButterKnife.bind(this);
        setHeight(back, searchLl, cancel);
        initView();
        addAdapter();
        setTagview();
        showHistorySearch();
    }

    private void addAdapter() {
        mapAddressAdapter = new MapAddressAdapter();
        mapAddressAdapter.setOnEventClickListener(this);
        addressRv.setAdapter(mapAddressAdapter);
        nearbyMunuAdapter = new NearbyMunuAdapter();
        nearbyMunuAdapter.setOnMenuItemClickListener(this);
        nearbyMunuRy.setAdapter(nearbyMunuAdapter);
        nearbyItemAdapter = new NearbyItemAdapter();
        nearbyItemAdapter.setOnItemClickListener(this);
        nearbyItemRy.setAdapter(nearbyItemAdapter);
        mapMarkerStoreAdapter = new MapMarkerStoreAdapter(this,currLongitude,currLatitude);
        mapMarkerStoreAdapter.setOnMapMarkerClickListener(this);
        mapStoreRv.setAdapter(mapMarkerStoreAdapter);
    }

    private void initView() {
        district = getIntent().getStringExtra(StaticData.DISTRICT);
        currLatitude=getIntent().getStringExtra(StaticData.LATITUDE);
        currLongitude=getIntent().getStringExtra(StaticData.LONGITUDE);
        mapAddressInfos = new ArrayList<>();
        commonAppPreferences = new CommonAppPreferences(this);
        //监听edittext变化
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(searchEt.getText().toString())) {
                    mHandler.sendEmptyMessageDelayed(UPDATE_EDITTEXT, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        mapAddressInfos.clear();
        if (i == 1000 && poiResult != null && poiResult.getPois() != null) {// 搜索poi的结果
            if (poiResult.getQuery().equals(query)) {// 是否是同一条
                ArrayList<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                for (int j = 0; j < poiItems.size(); j++) {
                    MapAddressInfo mapAddressInfo = new MapAddressInfo();
                    mapAddressInfo.setTitle(poiItems.get(j).getTitle());
                    mapAddressInfo.setAddress(poiItems.get(j).getSnippet());
                    LatLonPoint latLonPoint = poiItems.get(j).getLatLonPoint();
                    mapAddressInfo.setLat(latLonPoint.getLatitude() + "");
                    mapAddressInfo.setLon(latLonPoint.getLongitude() + "");
                    mapAddressInfos.add(mapAddressInfo);
                }
                if (mapAddressInfos.size() > 0) {
                    addressRv.setVisibility(View.VISIBLE);
                }
                mapAddressAdapter.setData(mapAddressInfos);
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void backAddress(MapAddressInfo mapAddressInfo) {
        canSearch = false;
        setHistory(mapAddressInfo.getTitle());
        searchEt.setText(mapAddressInfo.getTitle());
        addressRv.setVisibility(View.GONE);
        latitude = mapAddressInfo.getLat();
        longitude = mapAddressInfo.getLon();
        nearbyMapPresenter.getNearbyInfo(district);
    }

    @Override
    public void itemClickListener(NearbyInfoResponse.CateInfo cateInfo, int itemPosition) {
        nearbyItemAdapter.setPosittion(itemLastPosition, itemPosition);
        itemLastPosition = itemPosition;
        nearbyMapPresenter.getMapMarkerInfo(cateInfo.getId(), (longitude + "," + latitude));
    }

    @Override
    public void menuItemClickListener(int menuPosition) {
        nearbyMunuAdapter.setPosittion(munuLastPosition, menuPosition);
        munuLastPosition = menuPosition;
        itemLastPosition = 0;
        nearbyItemAdapter.setItemList(nearbyInfoResponses.get(menuPosition).getCateInfos(), itemLastPosition);
        if (nearbyInfoResponses.get(menuPosition).getCateInfos() != null && nearbyInfoResponses.get(menuPosition).getCateInfos().size() > 0) {
            nearbyMapPresenter.getMapMarkerInfo(nearbyInfoResponses.get(menuPosition).getCateInfos().get(0).getId(), (longitude + "," + latitude));
        }
    }

    @Override
    public void mapMarkerClickListener(String storeid) {
        ShopDetailActivity.start(this, storeid);
    }

    @Override
    public void setPresenter(NearbyMapContract.NearbyPresenter presenter) {

    }

    @Override
    public void nearbyInfo(List<NearbyInfoResponse> nearbyInfoResponses) {
        this.nearbyInfoResponses = nearbyInfoResponses;
        munuLastPosition = 0;
        itemLastPosition = 0;
        if (nearbyInfoResponses != null && nearbyInfoResponses.size() > 0) {
            nearbyLl.setVisibility(View.VISIBLE);
            nearbyMunuAdapter.setMunuList(nearbyInfoResponses, munuLastPosition);
            nearbyItemAdapter.setItemList(nearbyInfoResponses.get(0).getCateInfos(), itemLastPosition);
            if (nearbyInfoResponses.get(0).getCateInfos() != null && nearbyInfoResponses.get(0).getCateInfos().size() > 0) {
                nearbyMapPresenter.getMapMarkerInfo(nearbyInfoResponses.get(0).getCateInfos().get(0).getId(), (longitude + "," + latitude));
            }
        } else {
            nearbyLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderapMarkers(List<MapMarkerInfo> mapMarkerInfos) {
        mapMarkerStoreAdapter.setData(mapMarkerInfos);
    }

    @Override
    public void uploadXySuccess() {

    }

    public static class MyHandler extends StaticHandler<SearchAddressActivity> {

        public MyHandler(SearchAddressActivity target) {
            super(target);
        }

        @Override
        public void handle(SearchAddressActivity target, Message msg) {
            switch (msg.what) {
                case UPDATE_EDITTEXT:
                    target.searchAddress();
                    break;
            }
        }
    }

    private Handler mHandler = new MyHandler(this);

    public void searchAddress() {
        if (canSearch) {
            query = new PoiSearch.Query(searchEt.getText().toString(), "", district);
//        query.setPageSize(30);// 设置每页最多返回多少条poiitem
//        query.setPageNum(0);// 设置查第一页
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();
        } else {
            canSearch = true;
        }
    }


    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
    }

    @Override
    protected void initializeInjector() {
        DaggerNearbyMapComponent.builder()
                .applicationComponent(getApplicationComponent())
                .nearbyMapModule(new NearbyMapModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.cancel, R.id.delete_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.delete_history:
                commonAppPreferences.setAddressHistorySearch("");
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
                    searchEt.setText(historySearchList.get(position));
                }
            }
        });
    }

    /**
     * 显示历史搜索
     */

    private void showHistorySearch() {
        historySearchStr = commonAppPreferences.getAddressHistorySearch();
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
        historySearchStr = commonAppPreferences.getAddressHistorySearch();
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
            commonAppPreferences.setAddressHistorySearch(historySearchStr);
        } else {
            historySearchStr = tag;
            commonAppPreferences.setAddressHistorySearch(tag);
        }
        String[] tags = historySearchStr.split(SPLIT);
        historySearchList.clear();
        for (int i = 0; i < tags.length; i++) {
            historySearchList.add(tags[i]);

        }
        historyTagBaseAdapter.setList(historySearchList);
    }
}

