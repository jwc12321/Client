package com.purchase.sls.shoppingmall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.data.entity.ShoppingCartInfo;
import com.purchase.sls.shoppingmall.adapter.ShoppingCartAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/4.
 * 购物车
 */

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartAdapter.ItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.shopcart_rv)
    RecyclerView shopcartRv;
    @BindView(R.id.select_all)
    CheckBox selectAll;
    @BindView(R.id.select_number)
    TextView selectNumber;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.settlement_bt)
    Button settlementBt;

    private ShoppingCartAdapter shoppingCartAdapter;
    private List<ShoppingCartInfo> shoppingCartInfos;

    private int totalCount = 0;
    private boolean flag = false;//完成还是编辑

    private BigDecimal totalPriceBd;//总价
    private BigDecimal countBd;//数量
    private BigDecimal unitPriceBd;//单价

    public static void start(Context context) {
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        setHeight(back, title, edit);
        initView();
    }

    private void initView() {
        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.setItemClickListener(this);
        shopcartRv.setAdapter(shoppingCartAdapter);
        shoppingCartInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
            shoppingCartInfo.setName("大衣");
            shoppingCartInfo.setPrice("30.65");
            shoppingCartInfo.setCount(1);
            shoppingCartInfos.add(shoppingCartInfo);
        }
        for (int i = 0; i < 10; i++) {
            ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
            shoppingCartInfo.setName("篮球");
            shoppingCartInfo.setPrice("40.15");
            shoppingCartInfo.setCount(1);
            shoppingCartInfos.add(shoppingCartInfo);
        }
        shoppingCartAdapter.setData(shoppingCartInfos);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void checkGroup(int position, boolean isChecked) {
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(position);
        shoppingCartInfo.setChoosed(isChecked);
        calculatingPrice();
    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(position);
        int currentCount = shoppingCartInfo.getCount();
        currentCount++;
        shoppingCartInfo.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        calculatingPrice();
    }

    @Override
    public void doReduce(int position, View showCountView, boolean isChecked) {
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(position);
        int currentCount = shoppingCartInfo.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartInfo.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        calculatingPrice();
    }

    //计算价格
    private void calculatingPrice() {
        totalCount = 0;
        totalPriceBd= new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < shoppingCartInfos.size(); i++) {
            ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(i);
            if (shoppingCartInfo.isChoosed()) {
                totalCount++;
                unitPriceBd= new BigDecimal(shoppingCartInfo.getPrice()).setScale(2, RoundingMode.HALF_UP);
                countBd= new BigDecimal(shoppingCartInfo.getCount()).setScale(0, RoundingMode.HALF_UP);
                totalPriceBd =unitPriceBd.multiply(countBd).add(totalPriceBd);
            }
        }
        if(totalCount==0){
            selectNumber.setText("全选");
            settlementBt.setEnabled(false);
        }else {
            selectNumber.setText("已选( " + totalCount + " )");
            settlementBt.setEnabled(true);
        }
        totalPrice.setText("¥"+totalPriceBd.toString());
    }

    @OnClick({R.id.back,R.id.select_all,R.id.edit,R.id.settlement_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select_all:
                if(shoppingCartInfos.size()>0){
                    if(selectAll.isChecked()){
                        for (int i = 0; i < shoppingCartInfos.size(); i++) {
                            shoppingCartInfos.get(i).setChoosed(true);
                        }
                    }else {
                        for (int i = 0; i < shoppingCartInfos.size(); i++) {
                            shoppingCartInfos.get(i).setChoosed(false);
                        }
                    }
                    shoppingCartAdapter.notifyDataSetChanged();
                    calculatingPrice();
                }
                break;
            case R.id.edit:
                flag=!flag;
                if(flag){
                    edit.setText("完成");
                    settlementBt.setText("删除所选");
                    totalPrice.setVisibility(View.GONE);
                }else {
                    edit.setText("编辑");
                    settlementBt.setText("结算");
                    totalPrice.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.settlement_bt:
                String sttlebtStr=settlementBt.getText().toString();
                if(TextUtils.equals("结算",sttlebtStr)){

                }else {

                }
                break;
            default:
        }
    }
}
