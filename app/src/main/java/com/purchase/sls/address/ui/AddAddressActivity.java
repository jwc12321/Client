package com.purchase.sls.address.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.address.AddressContract;
import com.purchase.sls.address.AddressModule;
import com.purchase.sls.address.DaggerAddressComponent;
import com.purchase.sls.address.presenter.AddAddressPresenter;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.choiceregion.model.AddressDtailsEntity;
import com.purchase.sls.common.widget.choiceregion.model.AddressModel;
import com.purchase.sls.common.widget.choiceregion.utils.JsonUtil;
import com.purchase.sls.common.widget.choiceregion.utils.Utils;
import com.purchase.sls.common.widget.choiceregion.view.ChooseAddressWheel;
import com.purchase.sls.common.widget.choiceregion.view.listener.OnAddressChangeListener;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.request.AddAddressRequest;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.purchase.sls.common.unit.AccountUtils.isAccountValid;

/**
 * Created by JWC on 2018/5/29.
 */

public class AddAddressActivity extends BaseActivity implements OnAddressChangeListener, AddressContract.AddAddressView {
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
    @BindView(R.id.check_default)
    ImageView checkDefault;
    @BindView(R.id.defalt_tt)
    TextView defaltTt;
    private String type; //0：编辑地址 1：添加地址
    private AddressInfo addressInfo;
    private ChooseAddressWheel chooseAddressWheel = null;
    private String dfAddress;
    private String province;
    private String city;
    private String district;
    private String addressId;
    private AddAddressRequest addAddressRequest;
    @Inject
    AddAddressPresenter addAddressPresenter;

    private boolean isFlag;

    public static void start(Context context, String type, AddressInfo addressInfo) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra(StaticData.ADD_EDIT_TYPE, type);
        intent.putExtra(StaticData.ADDRESSINFO, addressInfo);
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
        addressInfo = (AddressInfo) getIntent().getSerializableExtra(StaticData.ADDRESSINFO);
        if (TextUtils.equals("0", type)) {
            title.setText("编辑地址");
            if (addressInfo != null) {
                consigneeEt.setText(addressInfo.getUsername());
                phoneNumberEt.setText(addressInfo.getTel());
                province = addressInfo.getProvince();
                city = addressInfo.getCity();
                district = addressInfo.getCountry();
                region.setText(province + city + district);
                addressId = addressInfo.getId();
                detailedAddressEt.setText(addressInfo.getAddress());
                if (TextUtils.equals("1", addressInfo.getType())) {
                    checkDefault.setSelected(true);
                    checkDefault.setEnabled(false);
                    dfAddress = "1";
                    defaltTt.setText("默认地址");
                } else {
                    checkDefault.setSelected(false);
                    checkDefault.setEnabled(true);
                    dfAddress = "0";
                    isFlag = false;
                    defaltTt.setText("设为默认");
                }
            }
        } else {
            dfAddress = "0";
            title.setText("添加地址");
            isFlag = false;
            defaltTt.setText("设为默认");
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

    @Override
    protected void initializeInjector() {
        DaggerAddressComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.complete, R.id.region, R.id.check_default})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.complete:
                submitAddress();
                break;
            case R.id.region:
                Utils.hideKeyBoard(AddAddressActivity.this);
                chooseAddressWheel.show(view);
                break;
            case R.id.check_default:
                isFlag = !isFlag;
                if (isFlag) {
                    checkDefault.setSelected(true);
                    dfAddress = "1";
                } else {
                    checkDefault.setSelected(false);
                    dfAddress = "0";
                }
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
        this.province = province;
        this.city = city;
        this.district = district;
        region.setText(province + city + district);
    }

    private void submitAddress() {
        if (TextUtils.isEmpty(consigneeEt.getText().toString())) {
            showError("请填写名字");
            return;
        }
        if (TextUtils.isEmpty(phoneNumberEt.getText().toString())) {
            showMessage("请填写手机号");
            return;
        } else if (!isAccountValid(phoneNumberEt.getText().toString())) {
            showError(getString(R.string.invalid_account_input));
            return;
        }
        if (TextUtils.isEmpty(region.getText().toString())) {
            showError("请选择地区");
            return;
        }
        if (TextUtils.isEmpty(detailedAddressEt.getText().toString())) {
            showError("请填写详细地址");
            return;
        }
        if (addAddressRequest == null) {
            addAddressRequest = new AddAddressRequest();
        }
        addAddressRequest.setProvince(province);
        addAddressRequest.setCity(city);
        addAddressRequest.setCountry(district);
        addAddressRequest.setAddress(detailedAddressEt.getText().toString());
        addAddressRequest.setUsername(consigneeEt.getText().toString());
        addAddressRequest.setTel(phoneNumberEt.getText().toString());
        addAddressRequest.setType(dfAddress);
        addAddressRequest.setId(addressId);
        addAddressPresenter.addAddress(addAddressRequest);
    }

    @Override
    public void setPresenter(AddressContract.AddAddressPresenter presenter) {

    }

    @Override
    public void addSuccess() {
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(consigneeEt.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(phoneNumberEt.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(detailedAddressEt.getWindowToken(), 0);
    }
}
