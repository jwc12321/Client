package com.purchase.sls.shopdetailbuy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/4/28.
 */

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.shop_name)
    TextView shopName;

    private String businessName;

    public static void start(Context context,String businessName){
        Intent intent=new Intent(context,PaySuccessActivity.class);
        intent.putExtra(StaticData.BUSINESS_NAME, businessName);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysuccess);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        businessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        shopName.setText(businessName);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
