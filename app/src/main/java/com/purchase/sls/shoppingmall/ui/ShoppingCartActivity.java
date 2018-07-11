package com.purchase.sls.shoppingmall.ui;

import android.content.Context;
import android.content.Intent;
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
import com.purchase.sls.data.entity.GoodsOrderList;
import com.purchase.sls.data.entity.ShoppingCartInfo;
import com.purchase.sls.shoppingmall.DaggerShoppingMallComponent;
import com.purchase.sls.shoppingmall.ShoppingMallContract;
import com.purchase.sls.shoppingmall.ShoppingMallModule;
import com.purchase.sls.shoppingmall.adapter.ShoppingCartAdapter;
import com.purchase.sls.shoppingmall.presenter.ShoppingCartPresenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/4.
 * 购物车
 */

public class ShoppingCartActivity extends BaseActivity implements ShoppingMallContract.ShoppingCartView, ShoppingCartAdapter.ItemClickListener {
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
    @BindView(R.id.no_address_iv)
    ImageView noAddressIv;
    @BindView(R.id.no_goods_rl)
    RelativeLayout noGoodsRl;
    @BindView(R.id.confirm_rl)
    RelativeLayout confirmRl;

    private ShoppingCartAdapter shoppingCartAdapter;
    private List<ShoppingCartInfo> shoppingCartInfos;

    private int totalCount = 0;
    private boolean flag = false;//完成还是编辑

    private BigDecimal totalPriceBd;//总价
    private BigDecimal countBd;//数量
    private BigDecimal unitPriceBd;//单价

    @Inject
    ShoppingCartPresenter shoppingCartPresenter;

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
        shoppingCartInfos = new ArrayList<>();
        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.setItemClickListener(this);
        shopcartRv.setAdapter(shoppingCartAdapter);
        shoppingCartPresenter.getShoppingCartList();
    }

    @Override
    protected void initializeInjector() {
        DaggerShoppingMallComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingMallModule(new ShoppingMallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void checkGroup(int position, boolean isChecked) {
        if(flag) {
            ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(position);
            shoppingCartInfo.setChoosed(isChecked);
        }else {
            for (int i=0;i<shoppingCartInfos.size();i++){
                if(i==position){
                    ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(i);
                    shoppingCartInfo.setChoosed(isChecked);
                }else {
                    ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(i);
                    shoppingCartInfo.setChoosed(false);
                }
            }
            shoppingCartAdapter.notifyDataSetChanged();
        }
        calculatingPrice();
    }

    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(position);
        int currentCount = Integer.parseInt(shoppingCartInfo.getGoodsnum());
        currentCount++;
        shoppingCartInfo.setGoodsnum(String.valueOf(currentCount));
        ((TextView) showCountView).setText(String.valueOf(currentCount));
        shoppingCartAdapter.notifyDataSetChanged();
        calculatingPrice();
    }

    @Override
    public void doReduce(int position, View showCountView, boolean isChecked) {
        ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(position);
        int currentCount = Integer.parseInt(shoppingCartInfo.getGoodsnum());
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartInfo.setGoodsnum(String.valueOf(currentCount));
        ((TextView) showCountView).setText(String.valueOf(currentCount));
        shoppingCartAdapter.notifyDataSetChanged();
        calculatingPrice();
    }

    //计算价格
    private void calculatingPrice() {
        totalCount = 0;
        totalPriceBd = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < shoppingCartInfos.size(); i++) {
            ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(i);
            if (shoppingCartInfo.isChoosed()) {
                totalCount++;
                unitPriceBd = new BigDecimal(shoppingCartInfo.getPrice()).setScale(2, RoundingMode.HALF_UP);
                countBd = new BigDecimal(shoppingCartInfo.getGoodsnum()).setScale(0, RoundingMode.HALF_UP);
                totalPriceBd = unitPriceBd.multiply(countBd).add(totalPriceBd);
            }
        }
        if (totalCount == 0) {
            selectNumber.setText("全选");
            settlementBt.setEnabled(false);
        } else {
            selectNumber.setText("已选( " + totalCount + " )");
            settlementBt.setEnabled(true);
        }
        totalPrice.setText("¥" + totalPriceBd.toString());
    }

    @OnClick({R.id.back, R.id.select_all, R.id.edit, R.id.settlement_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select_all:
                if (shoppingCartInfos.size() > 0) {
                    if (selectAll.isChecked()) {
                        for (int i = 0; i < shoppingCartInfos.size(); i++) {
                            shoppingCartInfos.get(i).setChoosed(true);
                        }
                    } else {
                        for (int i = 0; i < shoppingCartInfos.size(); i++) {
                            shoppingCartInfos.get(i).setChoosed(false);
                        }
                    }
                    shoppingCartAdapter.notifyDataSetChanged();
                    calculatingPrice();
                }
                break;
            case R.id.edit:
                flag = !flag;
                if (flag) {
                    edit.setText("完成");
                    settlementBt.setText("删除所选");
                    totalPrice.setVisibility(View.GONE);
                    cleanChoice();
                    selectAll.setVisibility(View.VISIBLE);
                    selectNumber.setVisibility(View.VISIBLE);
                    totalPrice.setText("¥0.00");
                    selectNumber.setText("全选");
                    settlementBt.setEnabled(false);
                } else {
                    edit.setText("编辑");
                    settlementBt.setText("结算");
                    totalPrice.setVisibility(View.VISIBLE);
                    cleanChoice();
                    selectAll.setVisibility(View.GONE);
                    selectNumber.setVisibility(View.GONE);
                    totalPrice.setText("¥0.00");
                    settlementBt.setEnabled(false);
                }
                break;
            case R.id.settlement_bt:
                orderDetailShopCart();
                break;
            default:
        }
    }

    //重置选项
    private void cleanChoice() {
        for (int i = 0; i < shoppingCartInfos.size(); i++) {
            shoppingCartInfos.get(i).setChoosed(false);
        }
        shoppingCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(ShoppingMallContract.ShoppingCartPresenter presenter) {

    }

    @Override
    public void renderShoppingCartList(List<ShoppingCartInfo> shoppingCartInfos) {
        this.shoppingCartInfos = shoppingCartInfos;
        if (shoppingCartInfos != null && shoppingCartInfos.size() > 0) {
            shopcartRv.setVisibility(View.VISIBLE);
            noGoodsRl.setVisibility(View.GONE);
            confirmRl.setVisibility(View.VISIBLE);
            shoppingCartAdapter.setData(shoppingCartInfos);
        } else {
            shopcartRv.setVisibility(View.GONE);
            noGoodsRl.setVisibility(View.VISIBLE);
            confirmRl.setVisibility(View.GONE);
        }
        calculatingPrice();
        selectNumber.setText("全选");
        settlementBt.setEnabled(false);
        selectAll.setChecked(false);
    }

    @Override
    public void orderShopCartSuccess(GoodsOrderList goodsOrderList) {
        FillInOrderActivity.start(this, goodsOrderList);
    }

    @Override
    public void deleteshopCartSuccess() {
        shoppingCartPresenter.getShoppingCartList();
    }

    private void orderDetailShopCart() {
        if (shoppingCartInfos != null) {
            showLoading();
            String cardId = "";
            List<ShoppingCartInfo> payOrderList = new ArrayList<>();
            for (int i = 0; i < shoppingCartInfos.size(); i++) {
                ShoppingCartInfo shoppingCartInfo = shoppingCartInfos.get(i);
                if (shoppingCartInfo.isChoosed()) {
                    payOrderList.add(shoppingCartInfo);
                }
            }
            if (payOrderList.size() > 0) {
                for (int z = 0; z < payOrderList.size(); z++) {
                    if (z == 0) {
                        cardId = payOrderList.get(z).getId();
                    } else {
                        cardId = cardId + "," + payOrderList.get(z).getId();
                    }
                }
                if (flag) {
                    shoppingCartPresenter.deleteshopCart(cardId);
                } else {
                    shoppingCartPresenter.orderShopCart(cardId);
                }
            } else {
                dismissLoading();
            }
        }
    }
}
