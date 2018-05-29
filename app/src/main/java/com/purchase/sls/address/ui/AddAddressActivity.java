package com.purchase.sls.address.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.choiceregion.model.AddressDtailsEntity;
import com.purchase.sls.common.widget.choiceregion.model.AddressModel;
import com.purchase.sls.common.widget.choiceregion.utils.JsonUtil;
import com.purchase.sls.common.widget.choiceregion.utils.Utils;
import com.purchase.sls.common.widget.choiceregion.view.ChooseAddressWheel;
import com.purchase.sls.common.widget.choiceregion.view.listener.OnAddressChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/29.
 */

public class AddAddressActivity extends BaseActivity implements OnAddressChangeListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.consignee_et)
    EditText consigneeEt;
    @BindView(R.id.phone_number_et)
    EditText phoneNumberEt;
    @BindView(R.id.region)
    TextView region;
    @BindView(R.id.detailed_address_et)
    EditText detailedAddressEt;
    @BindView(R.id.agreement_check)
    CheckBox agreementCheck;

    private String type; //0：编辑地址 1：添加地址
    private ChooseAddressWheel chooseAddressWheel = null;
    private String dfAddress = "0";

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra(StaticData.ADD_EDIT_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        setHeight(back, title, complete);
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(StaticData.ADD_EDIT_TYPE);
        if (TextUtils.equals("0", type)) {
            title.setText("编辑地址");
        } else {
            title.setText("添加地址");
        }
        initWheel();
        initData();
    }

    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this);
        chooseAddressWheel.setOnAddressChangeListener(this);
    }

    private void initData() {
        String address = Utils.readAssert(this, "address.txt");
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            AddressDtailsEntity data = model.Result;
            if (data == null) return;
            if (data.ProvinceItems != null && data.ProvinceItems.Province != null) {
                chooseAddressWheel.setProvince(data.ProvinceItems.Province);
                chooseAddressWheel.defaultValue(data.Province, data.City, data.Area);
            }
        }
    }

    @OnClick({R.id.back, R.id.complete, R.id.region})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:
                break;
            case R.id.region:
                Utils.hideKeyBoard(AddAddressActivity.this);
                chooseAddressWheel.show(view);
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onAddressChange(String province, String city, String district) {
        region.setText(province + city + district);
    }

    @OnCheckedChanged({R.id.agreement_check})
    public void agreementCheck(boolean checked) {
        if (checked) {
            dfAddress = "1";
        } else {
            dfAddress = "0";
        }
    }
}
