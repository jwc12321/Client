package com.purchase.sls.homepage.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.purchase.sls.common.location.LocationHelper;
import com.purchase.sls.common.unit.CommonAppPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/4/21.
 */

public class ChoiceCityActivity extends BaseActivity {
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

    private String city;
    private LocationHelper mLocationHelper;
    private CommonAppPreferences commonAppPreferences;
    private static final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    public View getSnackBarHolderView() {
        return null;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_city);
        ButterKnife.bind(this);
        city=getIntent().getStringExtra(StaticData.CHOICE_CITY);
        currentCity.setText(city);
        initList();
        setCityData(CityListLoader.getInstance().getCityListData());

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

    @OnClick({R.id.back, R.id.location_again,R.id.currentCity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.location_again:
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
            default:
        }
    }
}
