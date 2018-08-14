package com.purchase.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.data.entity.AllCategoriesInfo;
import com.purchase.sls.homepage.DaggerHomePageComponent;
import com.purchase.sls.homepage.HomePageContract;
import com.purchase.sls.homepage.HomePageModule;
import com.purchase.sls.homepage.adapter.AllCategoriesAdapter;
import com.purchase.sls.homepage.presenter.AllCategoriesPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 全部分类
 */
public class AllCategoriesActivity extends BaseActivity implements HomePageContract.AllCategoriesView,AllCategoriesAdapter.OnAllCgClickListener{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.all_categories_rv)
    RecyclerView allCategoriesRv;

    private AllCategoriesAdapter allCategoriesAdapter;
    @Inject
    AllCategoriesPresenter allCategoriesPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, AllCategoriesActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        ButterKnife.bind(this);
        setHeight(back,title,searchIv);
        initView();
    }

    private void initView(){
        allCategoriesAdapter=new AllCategoriesAdapter(this);
        allCategoriesRv.setAdapter(allCategoriesAdapter);
        allCategoriesAdapter.setOnAllCgClickListener(this);
        allCategoriesPresenter.getAllCategoriesInfos();
    }

    @Override
    protected void initializeInjector() {
        DaggerHomePageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homePageModule(new HomePageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderAllCategoriesInfos(List<AllCategoriesInfo> allCategoriesInfos) {
        allCategoriesAdapter.setData(allCategoriesInfos);
    }

    @Override
    public void setPresenter(HomePageContract.AllCategoriesPresenter presenter) {

    }


    @OnClick({R.id.back, R.id.search_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_iv:
                SearchShopActivity.start(this);
                break;
            default:
        }
    }

    @Override
    public void returnId(String id, String name) {
        ScreeningListActivity.start(this, id,name, "0", "","2");
    }
}
