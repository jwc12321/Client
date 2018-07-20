package com.purchase.sls.homepage.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.purchase.sls.common.cityList.style.citylist.sortlistview.SortAdapter;
import com.purchase.sls.common.cityList.style.citylist.sortlistview.SortModel;
import com.purchase.sls.common.cityList.style.citylist.utils.CityListLoader;
import com.purchase.sls.common.cityList.utils.PinYinUtils;
import com.purchase.sls.common.location.ChoiceCityLocationHelper;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.homepage.adapter.AreaAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/21.
 */

public class ChoiceCityActivity extends BaseActivity implements AreaAdapter.ItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.currentCityTag)
    TextView currentCityTag;
    @BindView(R.id.currentCity)
    TextView currentCity;
    @BindView(R.id.localCityTag)
    TextView localCityTag;
    @BindView(R.id.localCity)
    TextView localCity;
    @BindView(R.id.country_lvcountry)
    ListView countryLvcountry;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    @BindView(R.id.location_again)
    TextView locationAgain;
    @BindView(R.id.choice_city_ll)
    LinearLayout choiceCityLl;
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


    public SortAdapter adapter;

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
        initAreaAdapter();
        areaAdapter.setData(cityListLoader.getArea(transmitCity), transmitCity);
        initHotArea();
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

    private void initHotArea() {
        hotCityRv.setLayoutManager(new GridLayoutManager(this, 3));
        int space = 20;
        hotCityRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
        hotAreaAdapter = new AreaAdapter();
        hotAreaAdapter.setItemClickListener(this);
        hotCityRv.setAdapter(hotAreaAdapter);
        CityInfoBean cityInfoBean1=new CityInfoBean("330800","衢州市",null);
        CityInfoBean cityInfoBean2=new CityInfoBean("331100","丽水市",null);
        CityInfoBean cityInfoBean3=new CityInfoBean("110100","北京市",null);
        CityInfoBean cityInfoBean4=new CityInfoBean("310100","上海市",null);
        CityInfoBean cityInfoBean5=new CityInfoBean("440100","广州市",null);
        CityInfoBean cityInfoBean6=new CityInfoBean("330100","杭州市",null);
        hotCityInfoBeans=new ArrayList<>();
        hotCityInfoBeans.add(cityInfoBean1);
        hotCityInfoBeans.add(cityInfoBean2);
        hotCityInfoBeans.add(cityInfoBean3);
        hotCityInfoBeans.add(cityInfoBean4);
        hotCityInfoBeans.add(cityInfoBean5);
        hotCityInfoBeans.add(cityInfoBean6);
        hotAreaAdapter.setData(hotCityInfoBeans,"");

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
        adapter.notifyDataSetChanged();
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
        sourceDateList = new ArrayList<SortModel>();
        adapter = new SortAdapter(ChoiceCityActivity.this, sourceDateList);
        countryLvcountry.setAdapter(adapter);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sidrbar.setTextView(dialog);
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    countryLvcountry.setSelection(position);
                }
            }
        });

        countryLvcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = ((SortModel) adapter.getItem(position)).getName();
                commonAppPreferences.setCity(cityName);
                cityInfoBean = CityInfoBean.findCity(cityListInfo, cityName);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(StaticData.CHOICE_CITY, cityInfoBean);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : sourceDateList) {
                String name = sortModel.getName();
                if (name.contains(filterStr) || characterParser.getSelling(name).startsWith(filterStr)) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @OnClick({R.id.back, R.id.location_again, R.id.currentCity, R.id.switch_area})
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
                Log.d("1111", "城市" + city + "经纬度====" + longitude + "," + latitude);
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
}
