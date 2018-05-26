package com.purchase.sls.evaluate.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.UMStaticData;
import com.purchase.sls.common.unit.UmengEventUtils;
import com.purchase.sls.common.widget.MyClickRatingBar;
import com.purchase.sls.common.widget.customeview.ActionSheet;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.evaluate.DaggerEvaluateComponent;
import com.purchase.sls.evaluate.EvaluateContract;
import com.purchase.sls.evaluate.EvaluateModule;
import com.purchase.sls.evaluate.adapter.AddPhotoAdapter;
import com.purchase.sls.evaluate.presenter.SubmitEvaluatePresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/5/5.
 * 评价商店
 */

public class SubmitEvaluateActivity extends BaseActivity implements EvaluateContract.SubmitEvaluateView, AddPhotoAdapter.AddPhotoListener, ActionSheet.OnPictureChoseListener, MyClickRatingBar.OnStarItemClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.text_number)
    TextView textNumber;
    @BindView(R.id.evaluate_et)
    EditText evaluateEt;
    @BindView(R.id.phone_rv)
    RecyclerView phoneRv;
    @BindView(R.id.add_photo)
    ImageView addPhoto;
    @BindView(R.id.agreement_check)
    CheckBox agreementCheck;
    @BindView(R.id.rating_bar)
    MyClickRatingBar ratingBar;
    @BindView(R.id.agreement)
    TextView agreement;
    @BindView(R.id.registration_agreement_ll)
    LinearLayout registrationAgreementLl;
    @BindView(R.id.submit_ll)
    LinearLayout submitLl;

    private String typeAnonymous;

    private AddPhotoAdapter photoAdapter;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ActionSheet actionSheet;

    private String storeId;
    private String orderId;
    private String starts;
    private String businessName;
    private List<String> uploadFiles;

    @Inject
    SubmitEvaluatePresenter submitEvaluatePresenter;

    public static void start(Context context, String storeId, String orderId, String businessName) {
        Intent intent = new Intent(context, SubmitEvaluateActivity.class);
        intent.putExtra(StaticData.BUSINESS_STOREID, storeId);
        intent.putExtra(StaticData.ORDER_ID, orderId);
        intent.putExtra(StaticData.BUSINESS_NAME, businessName);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_evaluate);
        ButterKnife.bind(this);
        setHeight(back, title, submit);
        initView();
    }

    private void initView() {
        uploadFiles = new ArrayList<>();
        typeAnonymous = "1";
        starts = "1";
        storeId = getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        orderId = getIntent().getStringExtra(StaticData.ORDER_ID);
        businessName = getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        title.setText(businessName);
        ratingBar.setmStarItemClickListener(this);
        addAdapter();
    }

    private void addAdapter() {
        photoAdapter = new AddPhotoAdapter();
        phoneRv.setLayoutManager(new GridLayoutManager(this, 3));
        photoAdapter.setPaths(photoPaths);
        photoAdapter.setPhotoListener(this);
        phoneRv.setAdapter(photoAdapter);
    }

    @OnTextChanged(R.id.evaluate_et)
    public void onTextChange(Editable editable) {
        int length = editable.toString().length();
        textNumber.setText(length + "");
        textNumber.setTextColor(length > 80 ? getResources().getColor(R.color.appText11) : getResources().getColor(R.color.appText2));
    }


    @Override
    protected void initializeInjector() {
        DaggerEvaluateComponent.builder()
                .applicationComponent(getApplicationComponent())
                .evaluateModule(new EvaluateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return submitLl;
    }

    @Override
    public void setPresenter(EvaluateContract.SubmitEvaluatePresenter presenter) {

    }

    @Override
    public void uploadFileSuccess(String photoUrl) {
        uploadFiles.add(photoUrl);
    }

    @Override
    public void submitSuccess() {
        UmengEventUtils.statisticsClick(this, UMStaticData.COMMENT_STORE);
        EvaluateSuccessActivity.start(this);
        this.finish();
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
    public void startAddPhoto() {
        initPhotoPicker();
    }

    @Override
    public void removePhoto(int position) {
        uploadFiles.remove(position);
    }

    @OnClick({R.id.add_photo, R.id.submit, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
                initPhotoPicker();
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    private void initPhotoPicker() {
        if (actionSheet == null) {
            actionSheet = ActionSheet.newInstance(true, 90, 90);
            actionSheet.setOnPictureChoseListener(SubmitEvaluateActivity.this);
        }
        actionSheet.show(this);
        Log.d("111", "数据" + photoPaths.size());
        if (photoPaths.size() > 8) {
            showMessage("至多选择9张照片");
            return;
        }
    }

    @Override
    public void onPictureChose(File filePath) {
        photoPaths.add(filePath.getAbsolutePath());
        photoAdapter.setPaths(photoPaths);
        submitEvaluatePresenter.uploadFile(filePath.getAbsolutePath());
    }

    @OnCheckedChanged({R.id.agreement_check})
    public void agreementCheck(boolean checked) {
        if (checked) {
            typeAnonymous = "1";
        } else {
            typeAnonymous = "0";
        }
    }

    private void submit() {
        String picsStr = "";
        SubmitEvaluateRequest submitEvaluateRequest = new SubmitEvaluateRequest();
        submitEvaluateRequest.setStoreid(storeId);
        submitEvaluateRequest.setWords(evaluateEt.getText().toString());
        submitEvaluateRequest.setStarts(starts);
        submitEvaluateRequest.setType(typeAnonymous);
        submitEvaluateRequest.setOrderid(orderId);
        for (int i = 0; i < uploadFiles.size(); i++) {
            if (i < uploadFiles.size() - 1) {
                picsStr = picsStr + uploadFiles.get(i) + ",";
            } else {
                picsStr = picsStr + uploadFiles.get(i);
            }
        }
        submitEvaluateRequest.setPics(picsStr);
        submitEvaluatePresenter.submitEvaluate(submitEvaluateRequest);
    }

    @Override
    public void onItemClick(View view, int pos) {
        starts = String.valueOf(pos + 1);
    }
}
