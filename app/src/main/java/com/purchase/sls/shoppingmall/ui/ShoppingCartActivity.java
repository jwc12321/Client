package com.purchase.sls.shoppingmall.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/4.
 * 购物车
 */

public class ShoppingCartActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
    @BindView(R.id.select_cb)
    CheckBox selectCb;
    @BindView(R.id.select_number)
    TextView selectNumber;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.settlement_bt)
    Button settlementBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        setHeight(back, title, edit);
        initView();
    }

    private void initView(){

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
