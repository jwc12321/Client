package com.purchase.sls.ordermanage.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.GlideHelper;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.dialog.CallDialogFragment;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.entity.LogisticRracesInfo;
import com.purchase.sls.energy.ui.SkEcLtActivity;
import com.purchase.sls.ordermanage.DaggerOrderManageComponent;
import com.purchase.sls.ordermanage.OrderManageContract;
import com.purchase.sls.ordermanage.OrderManageModule;
import com.purchase.sls.ordermanage.presenter.ActivityDetailInfoPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/7.
 */

public class ActivityOrderDetailActivity extends BaseActivity implements OrderManageContract.ActivityDetailInfoView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.express_car)
    ImageView expressCar;
    @BindView(R.id.express_where)
    TextView expressWhere;
    @BindView(R.id.express_time)
    TextView expressTime;
    @BindView(R.id.express_if)
    RelativeLayout expressIf;
    @BindView(R.id.local_addrsss)
    ImageView localAddrsss;
    @BindView(R.id.consignee)
    TextView consignee;
    @BindView(R.id.tel)
    TextView tel;
    @BindView(R.id.receiving_address)
    TextView receivingAddress;
    @BindView(R.id.shop_iv)
    ImageView shopIv;
    @BindView(R.id.activity_type_iv)
    ImageView activityTypeIv;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.energy_number)
    TextView energyNumber;
    @BindView(R.id.original_price)
    TextView originalPrice;
    @BindView(R.id.call_kefu)
    LinearLayout callKefu;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.create_time)
    TextView createTime;
    @BindView(R.id.pay_time)
    TextView payTime;
    @BindView(R.id.deliver_time)
    TextView deliverTime;
    @BindView(R.id.order_detail_ll)
    LinearLayout orderDetailLl;
    @BindView(R.id.shop_item)
    LinearLayout shopItem;
    @BindView(R.id.arrow_iv)
    ImageView arrowIv;

    private ActivityOrderDetailInfo activityOrderDetailInfo;
    @Inject
    ActivityDetailInfoPresenter activityDetailInfoPresenter;
    private List<LogisticRracesInfo> logisticRracesInfos;
    private String activityId;
    private String expressName;
    private String expressNum;
    private String connect;

    public static void start(Context context, ActivityOrderDetailInfo activityOrderDetailInfo) {
        Intent intent = new Intent(context, ActivityOrderDetailActivity.class);
        intent.putExtra(StaticData.ACTIVITY_ORDER_DETAIL, activityOrderDetailInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_order_detail);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        activityOrderDetailInfo = (ActivityOrderDetailInfo) getIntent().getSerializableExtra(StaticData.ACTIVITY_ORDER_DETAIL);
        if (activityOrderDetailInfo != null) {
            activityId = activityOrderDetailInfo.getActId();
            setOrderStatus(activityOrderDetailInfo.getStatus(), activityOrderDetailInfo.getpType());
            if(TextUtils.equals("1",activityOrderDetailInfo.getpType())){
                consignee.setText("请至个人中心->兑换券查看并使用");
                tel.setVisibility(View.GONE);
                receivingAddress.setVisibility(View.GONE);
            }else {
                tel.setVisibility(View.VISIBLE);
                receivingAddress.setVisibility(View.VISIBLE);
                consignee.setText("收货人: " + activityOrderDetailInfo.getName());
                tel.setText(activityOrderDetailInfo.getTel());
                receivingAddress.setText(activityOrderDetailInfo.getProvince() + activityOrderDetailInfo.getCity() + activityOrderDetailInfo.getArea() + activityOrderDetailInfo.getAddress());
            }
            GlideHelper.load(this, activityOrderDetailInfo.getGoodsLogo(), R.mipmap.app_icon, shopIv);
            shopName.setText(activityOrderDetailInfo.getGoodsName());
            energyNumber.setText(activityOrderDetailInfo.getPrice() + "能量");
            originalPrice.setText("¥" + activityOrderDetailInfo.getGoodsPrice());
            originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            orderNumber.setText("订单编号:" + activityOrderDetailInfo.getOrderNum());
            createTime.setText("创建时间:" + FormatUtil.formatDateByLine(activityOrderDetailInfo.getCreateTime()));
            payTime.setText("支付时间:" + FormatUtil.formatDateByLine(activityOrderDetailInfo.getCreateTime()));
            deliverTime.setText("发货时间:" + FormatUtil.formatDateByLine(activityOrderDetailInfo.getSendTime()));
            if (TextUtils.equals("1", activityOrderDetailInfo.getActType())) {
                activityTypeIv.setBackgroundResource(R.mipmap.spike);
            } else if (TextUtils.equals("2", activityOrderDetailInfo.getActType())) {
                activityTypeIv.setBackgroundResource(R.mipmap.exchange);
            } else {
                activityTypeIv.setBackgroundResource(R.mipmap.lottery);
            }
            expressName = activityOrderDetailInfo.getExpressName();
            expressNum = activityOrderDetailInfo.getExpressNum();
            logisticRracesInfos = activityOrderDetailInfo.getLogisticRracesInfos();
            if(TextUtils.equals("1",activityOrderDetailInfo.getpType())){
                expressWhere.setText("该兑换劵已发送");
                expressTime.setVisibility(View.GONE);
            }else {
                if (logisticRracesInfos != null && logisticRracesInfos.size() > 0) {
                    LogisticRracesInfo logisticRracesInfo = logisticRracesInfos.get(logisticRracesInfos.size() - 1);
                    expressWhere.setText(logisticRracesInfo.getAcceptStation());
                    expressTime.setVisibility(View.VISIBLE);
                    expressTime.setText(logisticRracesInfo.getAcceptTime());
                } else {
                    expressWhere.setText("暂无物流信息");
                    expressTime.setVisibility(View.GONE);
                }
            }
            connect = activityOrderDetailInfo.getConnect();
        }
    }


    //设置状态 待发货,1已发货,2已收获,10未开将,11未中奖',
    private void setOrderStatus(String status, String pType) {
        if (TextUtils.equals("0", status)) {
            orderStatus.setText("待发货");
            orderStatus.setTextColor(Color.parseColor("#FFA850"));
        } else if (TextUtils.equals("1", status)) {
            if (TextUtils.equals("1", pType)) {
                orderStatus.setText("待使用");
            } else {
                orderStatus.setText("待收货");
            }
            orderStatus.setTextColor(Color.parseColor("#E8192D"));
        } else if (TextUtils.equals("2", status)) {
            if (TextUtils.equals("1", pType)) {
                orderStatus.setText("已使用");
            } else {
                orderStatus.setText("已收货");
            }
            orderStatus.setTextColor(Color.parseColor("#198732"));
        } else if (TextUtils.equals("10", status)) {
            orderStatus.setText("未开将");
            orderStatus.setTextColor(Color.parseColor("#0C92C0"));
        } else if (TextUtils.equals("11", status)) {
            orderStatus.setText("未中奖");
        }
    }


    @Override
    protected void initializeInjector() {
        DaggerOrderManageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderManageModule(new OrderManageModule(this))
                .build().inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return orderDetailLl;
    }

    @OnClick({R.id.back, R.id.call_kefu, R.id.shop_item, R.id.express_if})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.call_kefu:
                if (!TextUtils.isEmpty(connect)) {
                    dial(connect, "联系客户");
                }
                break;
            case R.id.shop_item:
                if (!TextUtils.isEmpty(activityId)) {
                    activityDetailInfoPresenter.getActivityDetail(activityId);
                }
                break;
            case R.id.express_if:
                if (!TextUtils.equals("1",activityOrderDetailInfo.getpType())&&!TextUtils.isEmpty(expressName) && !TextUtils.isEmpty(expressNum) && logisticRracesInfos != null && logisticRracesInfos.size() > 0) {
                    LogisticsDetailsActivity.start(this, expressName, expressNum, logisticRracesInfos);
                }
                break;
            default:
        }
    }

    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private String mCallingPhone;
    private String mTitle;

    // 拨打电话
    private void dial(String phone, String title) {
        boolean ret = requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        if (ret) {
            CallDialogFragment serviceFragment = CallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getSupportFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial(mCallingPhone, mTitle);
                break;
        }
    }

    @Override
    public void setPresenter(OrderManageContract.ActivityDetailInfoPresenter presenter) {

    }

    @Override
    public void activityDetailInfo(ActivityInfo activityInfo) {
        SkEcLtActivity.start(this, activityInfo);
    }
}
