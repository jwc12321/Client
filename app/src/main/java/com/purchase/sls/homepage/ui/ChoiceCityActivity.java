package com.purchase.sls.homepage.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.cityList.style.citylist.bean.CityInfoBean;
import com.purchase.sls.common.cityList.style.citylist.sortlistview.CharacterParser;
import com.purchase.sls.common.cityList.style.citylist.sortlistview.PinyinComparator;
import com.purchase.sls.common.cityList.style.citylist.sortlistview.SideBar;
import com.purchase.sls.common.cityList.style.citylist.sortlistview.SortModel;
import com.purchase.sls.common.cityList.style.citylist.utils.CityListLoader;
import com.purchase.sls.common.cityList.utils.PinYinUtils;
import com.purchase.sls.common.location.ChoiceCityLocationHelper;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.homepage.adapter.AreaAdapter;
import com.purchase.sls.homepage.adapter.SearchAreaAdapter;
import com.purchase.sls.homepage.adapter.SortRvAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/21.
 */

public class ChoiceCityActivity extends BaseActivity implements AreaAdapter.ItemClickListener, SortRvAdapter.ItemClickListener,SearchAreaAdapter.ItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.currentCityTag)
    TextView currentCityTag;
    @BindView(R.id.currentCity)
    TextView currentCity;
    @BindView(R.id.country_lvcountry)
    RecyclerView countryLvcountry;
    @BindView(R.id.location_again)
    TextView locationAgain;
    @BindView(R.id.choice_city_ll)
    RelativeLayout choiceCityLl;
    @BindView(R.id.area_rv)
    RecyclerView areaRv;
    @BindView(R.id.curr_city)
    TextView currCity;
    @BindView(R.id.area_select_iv)
    ImageView areaSelectIv;
    @BindView(R.id.switch_area)
    TextView switchArea;
    @BindView(R.id.curr_ll)
    RelativeLayout currLl;
    @BindView(R.id.hot_city_rv)
    RecyclerView hotCityRv;
    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    @BindView(R.id.hot_ll)
    LinearLayout hotLl;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.search_area_rv)
    RecyclerView searchAreaRv;
    @BindView(R.id.no_search)
    TextView noSearch;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;

    private ChoiceCityLocationHelper mLocationHelper;
    private String city;
    private String longitude;
    private String latitude;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private CommonAppPreferences commonAppPreferences;


    @Override
    public View getSnackBarHolderView() {
        return choiceCityLl;
    }


    public SortRvAdapter sortRvAdapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private List<SortModel> sourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private List<CityInfoBean> cityListInfo = new ArrayList<>();

    private CityInfoBean cityInfoBean = new CityInfoBean();

    //startActivityForResult flag
    public static final int CITY_SELECT_RESULT_FRAG = 0x0000032;

    public static List<CityInfoBean> sCityInfoBeanList = new ArrayList<>();

    public PinYinUtils mPinYinUtils = new PinYinUtils();

    private String transmitCity;
    private CityListLoader cityListLoader;
    private AreaAdapter areaAdapter;
    private AreaAdapter hotAreaAdapter;
    private boolean isFlag = false;
    private List<CityInfoBean> hotCityInfoBeans;
    private List<CityInfoBean> allCityInfoBeans;
    private List<CityInfoBean> searchCityInfoBeans = new ArrayList<>();
    private SearchAreaAdapter searchAreaAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_city);
        ButterKnife.bind(this);
//        setHeight(back,title,null);
        commonAppPreferences = new CommonAppPreferences(this);
        transmitCity = getIntent().getStringExtra(StaticData.TRANSMIT_CITY);
        currCity.setText("当前:" + transmitCity);
        mapLocal();
        initList();
        cityListLoader = CityListLoader.getInstance();
        setCityData(cityListLoader.getCityListData());
        allCityInfoBeans = cityListLoader.getAllCityListData();
        initAreaAdapter();
        areaAdapter.setData(cityListLoader.getArea(transmitCity), transmitCity);
        initHotArea();
        initEditText();
        initSearchAdapter();
    }

    private void initEditText() {
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }
        });

        searchEt.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    searchRl.setVisibility(View.VISIBLE);
                } else {
                    searchRl.setVisibility(View.GONE);
                }

            }
        });
    }

    private void initAreaAdapter() {
        areaRv.setVisibility(View.GONE);
        areaRv.setLayoutManager(new GridLayoutManager(this, 3));
        int space = 20;
        areaRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
        areaAdapter = new AreaAdapter();
        areaAdapter.setItemClickListener(this);
        areaRv.setAdapter(areaAdapter);
    }

    private void initSearchAdapter() {
        searchAreaAdapter=new SearchAreaAdapter();
        searchAreaAdapter.setItemClickListener(this);
        searchAreaRv.setAdapter(searchAreaAdapter);
    }

    private void initHotArea() {
        hotCityRv.setLayoutManager(new GridLayoutManager(this, 3));
        int space = 20;
        hotCityRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
        hotAreaAdapter = new AreaAdapter();
        hotAreaAdapter.setItemClickListener(this);
        hotCityRv.setAdapter(hotAreaAdapter);
        CityInfoBean cityInfoBean1 = new CityInfoBean("330800", "衢州市", null);
        CityInfoBean cityInfoBean2 = new CityInfoBean("331100", "丽水市", null);
        CityInfoBean cityInfoBean3 = new CityInfoBean("110100", "北京市", null);
        CityInfoBean cityInfoBean4 = new CityInfoBean("310100", "上海市", null);
        CityInfoBean cityInfoBean5 = new CityInfoBean("440100", "广州市", null);
        CityInfoBean cityInfoBean6 = new CityInfoBean("330100", "杭州市", null);
        hotCityInfoBeans = new ArrayList<>();
        hotCityInfoBeans.add(cityInfoBean1);
        hotCityInfoBeans.add(cityInfoBean2);
        hotCityInfoBeans.add(cityInfoBean3);
        hotCityInfoBeans.add(cityInfoBean4);
        hotCityInfoBeans.add(cityInfoBean5);
        hotCityInfoBeans.add(cityInfoBean6);
        hotAreaAdapter.setData(hotCityInfoBeans, "");

    }


    private void setCityData(List<CityInfoBean> cityList) {
        cityListInfo = cityList;
        if (cityListInfo == null) {
            return;
        }
        int count = cityList.size();
        String[] list = new String[count];
        for (int i = 0; i < count; i++)
            list[i] = cityList.get(i).getName();

        sourceDateList.addAll(filledData(cityList));
        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        sortRvAdapter.setData(sourceDateList);
    }

    /**
     * 为ListView填充数据
     *
     * @return
     */
    private List<SortModel> filledData(List<CityInfoBean> cityList) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < cityList.size(); i++) {

            CityInfoBean result = cityList.get(i);

            if (result != null) {

                SortModel sortModel = new SortModel();

                String cityName = result.getName();
                //汉字转换成拼音
                if (!TextUtils.isEmpty(cityName) && cityName.length() > 0) {

                    String pinyin = "";
                    if (cityName.equals("重庆市")) {
                        pinyin = "chong";
                    } else if (cityName.equals("长沙市")) {
                        pinyin = "chang";
                    } else if (cityName.equals("长春市")) {
                        pinyin = "chang";
                    } else {
                        pinyin = mPinYinUtils.getStringPinYin(cityName.substring(0, 1));
                    }

                    if (!TextUtils.isEmpty(pinyin)) {

                        sortModel.setName(cityName);

                        String sortString = pinyin.substring(0, 1).toUpperCase();

                        // 正则表达式，判断首字母是否是英文字母
                        if (sortString.matches("[A-Z]")) {
                            sortModel.setSortLetters(sortString.toUpperCase());
                        } else {
                            sortModel.setSortLetters("#");
                        }
                        mSortList.add(sortModel);
                    } else {
                        Log.d("citypicker_log", "null,cityName:-> " + cityName + "       pinyin:-> " + pinyin);
                    }

                }

            }
        }
        return mSortList;
    }

    private void initList() {
        sourceDateList = new ArrayList<>();
        sortRvAdapter = new SortRvAdapter();
        sortRvAdapter.setItemClickListener(this);
        countryLvcountry.setAdapter(sortRvAdapter);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = sortRvAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    int scrollY = countryLvcountry.getChildAt(position).getTop();
                    int hotllY = hotLl.getHeight();
                    scrollview.scrollTo(0, scrollY + hotllY);
                }
            }
        });
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        searchCityInfoBeans.clear();
        if (allCityInfoBeans != null && allCityInfoBeans.size() > 0 && !TextUtils.isEmpty(filterStr)) {
            for (CityInfoBean cityInfoBean : allCityInfoBeans) {
                if (cityInfoBean.getName().contains(filterStr) || characterParser.getSelling(cityInfoBean.getName()).startsWith(filterStr)) {
                    searchCityInfoBeans.add(cityInfoBean);
                }
            }
            if(searchCityInfoBeans.size()>0) {
                searchAreaRv.setVisibility(View.VISIBLE);
                noSearch.setVisibility(View.GONE);
                searchAreaAdapter.setData(searchCityInfoBeans);
            }else {
                searchAreaRv.setVisibility(View.GONE);
                noSearch.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.back, R.id.location_again, R.id.currentCity, R.id.switch_area,R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.location_again:
                mLocationHelper.start();
                break;
            case R.id.currentCity:
                cityInfoBean = CityInfoBean.findCity(cityListInfo, city);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(StaticData.CHOICE_CITY, cityInfoBean);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.switch_area:
                isFlag = !isFlag;
                if (isFlag) {
                    areaSelectIv.setSelected(true);
                    areaRv.setVisibility(View.VISIBLE);
                } else {
                    areaSelectIv.setSelected(false);
                    areaRv.setVisibility(View.GONE);
                }
                break;
            case R.id.cancel:
                searchEt.setText("");
                searchEt.clearFocus();
                searchRl.setVisibility(View.GONE);
                break;
            default:
        }
    }


    //地图定位
    private void mapLocal() {
        mLocationHelper = ChoiceCityLocationHelper.sharedInstance(this);
        mLocationHelper.addOnLocatedListener(new ChoiceCityLocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(AMapLocation aMapLocation) {
                city = aMapLocation.getDistrict();
                longitude = aMapLocation.getLongitude() + "";
                latitude = aMapLocation.getLatitude() + "";
                currentCity.setText(city);
                commonAppPreferences.setCity(city);
                commonAppPreferences.setLocal(longitude, latitude);
                if (TextUtils.isEmpty(city) && TextUtils.equals("0.0", longitude) && TextUtils.equals("0.0", latitude)) {
                    currentCity.setText("定位失败，请重新定位");
                    Toast.makeText(getApplicationContext(), "查看定位权限有没有开", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION)) {
            mLocationHelper.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mLocationHelper.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationHelper.destroyLocation();
    }

    @Override
    public void returnArea(CityInfoBean cityInfoBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StaticData.CHOICE_CITY, cityInfoBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void returnArea(SortModel sortModel) {
        cityInfoBean = new CityInfoBean("", sortModel.getName(), null);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StaticData.CHOICE_CITY, cityInfoBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
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

    @Override
    public void returnSearchArea(CityInfoBean cityInfoBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StaticData.CHOICE_CITY, cityInfoBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchEt.getWindowToken(), 0);
    }
}
