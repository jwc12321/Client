package com.purchase.sls.shoppingmall.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.GradationScrollView;
import com.purchase.sls.common.widget.shoppingselect.OnSelectedListener;
import com.purchase.sls.common.widget.shoppingselect.ShoppingSelectView;
import com.purchase.sls.data.entity.GoodsDetailInfo;
import com.purchase.sls.data.entity.GoodsSku;
import com.purchase.sls.data.entity.GoodsSpec;
import com.purchase.sls.data.entity.GoodsUnitPrice;
import com.purchase.sls.data.entity.ShoppingCartInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/6.
 * 选择规格
 */

public class SelectSpecActivity extends BaseActivity implements OnSelectedListener {
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.shopselectView)
    ShoppingSelectView shopselectView;
    @BindView(R.id.decrease_count)
    ImageView decreaseCount;
    @BindView(R.id.goods_count)
    TextView goodsCount;
    @BindView(R.id.increase_count)
    ImageView increaseCount;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.confirm_bt)
    Button confirmBt;

    private GoodsDetailInfo goodsDetailInfo;
    private GoodsSku goodsSku;
    private List<GoodsSpec> goodsSpecs;
    private List<GoodsUnitPrice> goodsUnitPrices;
    private String goodsSpecStr;
    private GoodsUnitPrice goodsUnitPrice;

    private BigDecimal totalPriceBd;//总价
    private BigDecimal countBd;//数量
    private BigDecimal unitPriceBd;//单价
    private int currentCount;

    Map<String, String> map = new HashMap<String, String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_spec);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        goodsDetailInfo = (GoodsDetailInfo) getIntent().getSerializableExtra(StaticData.GOODS_DETAIL);
        shopselectView.setOnSelectedListener(this);
        if (goodsDetailInfo != null) {
            GlideHelper.load(this, goodsDetailInfo.getGoodsImg(), R.mipmap.app_icon, photo);
            goodsSku = goodsDetailInfo.getGoodsSku();
            if (goodsSku != null) {
                goodsSku = goodsDetailInfo.getGoodsSku();
                goodsSpecs = goodsSku.getGoodsSpecs();
                goodsUnitPrices = goodsSku.getGoodsUnitPrices();
                if (goodsSpecs != null) {
                    shopselectView.setData(goodsSpecs);
                }
            }
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onSelected(String position, String title, String smallTitle) {
        goodsSpecStr = "";
        map.put(position, smallTitle);
        Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            goodsSpecStr = goodsSpecStr + entry.getValue();
        }
        for (GoodsUnitPrice goodsUnitPrice : goodsUnitPrices) {
            if (TextUtils.equals(goodsSpecStr, goodsUnitPrice.getName())) {
                this.goodsUnitPrice = goodsUnitPrice;
                return;
            }
        }
        calculatingPrice(goodsUnitPrice);
    }

    private void calculatingPrice(GoodsUnitPrice goodsUnitPrice) {
        if(goodsUnitPrice!=null) {
            totalPriceBd = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            unitPriceBd = new BigDecimal(goodsUnitPrice.getPrice()).setScale(2, RoundingMode.HALF_UP);
            countBd = new BigDecimal(currentCount).setScale(0, RoundingMode.HALF_UP);
            totalPriceBd = unitPriceBd.multiply(countBd).add(totalPriceBd);
            price.setText("¥"+totalPriceBd.toString());
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    @OnClick({R.id.cancel, R.id.decrease_count, R.id.increase_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.decrease_count:
                currentCount--;
                if(currentCount<0){
                    return;
                }
                calculatingPrice(goodsUnitPrice);
                break;
            case R.id.increase_count:
                currentCount++;
                calculatingPrice(goodsUnitPrice);
                break;
            default:
        }
    }
}
