package com.purchase.sls.evaluate.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purchase.sls.BaseActivity;
import com.purchase.sls.R;
import com.purchase.sls.common.StaticData;
import com.purchase.sls.common.widget.customeview.ActionSheet;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.evaluate.DaggerEvaluateComponent;
import com.purchase.sls.evaluate.EvaluateContract;
import com.purchase.sls.evaluate.EvaluateModule;
import com.purchase.sls.evaluate.adapter.AddPhotoAdapter;
import com.purchase.sls.evaluate.presenter.SubmitEvaluatePresenter;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.view.View.VISIBLE;

/**
 * Created by JWC on 2018/5/5.
 * 评价商店
 */

public class SubmitEvaluateActivity extends BaseActivity implements EvaluateContract.SubmitEvaluateView, AddPhotoAdapter.AddPhotoListener, ActionSheet.OnPictureChoseListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
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

    private String typeAnonymous;

    private AddPhotoAdapter photoAdapter;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ActionSheet actionSheet;

    private String storeId;
    private String orderId;
    private String starts;

    @Inject
    SubmitEvaluatePresenter submitEvaluatePresenter;

    public static void start(Context context, String storeId,String orderId) {
        Intent intent = new Intent(context, SubmitEvaluateActivity.class);
        intent.putExtra(StaticData.BUSINESS_STOREID, storeId);
        intent.putExtra(StaticData.ORDER_ID,orderId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_evaluate);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        typeAnonymous = "1";
        storeId= getIntent().getStringExtra(StaticData.BUSINESS_STOREID);
        orderId=getIntent().getStringExtra(StaticData.ORDER_ID);
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starts=String.valueOf(rating);
            }
        });
        addAdapter();
    }

    private void addAdapter() {
        photoAdapter = new AddPhotoAdapter();
        phoneRv.setLayoutManager(new GridLayoutManager(this, 4));
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
        return null;
    }

    @Override
    public void setPresenter(EvaluateContract.SubmitEvaluatePresenter presenter) {

    }

    @Override
    public void submitSuccess() {
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

    @OnClick({R.id.add_photo,R.id.submit,R.id.back})
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
        if (photoPaths.size() > 7) {
            showMessage("至多选择8张照片");
            return;
        }
    }

    @Override
    public void onPictureChose(File filePath) {
        photoPaths.add(filePath.getAbsolutePath());
        phoneRv.setVisibility(VISIBLE);
        photoAdapter.setPaths(photoPaths);
    }

    @OnCheckedChanged({R.id.agreement_check})
    public void agreementCheck(boolean checked) {
        if(checked) {
            typeAnonymous = "1";
        }else {
            typeAnonymous = "0";
        }
    }

    private void submit(){
        SubmitEvaluateRequest submitEvaluateRequest=new SubmitEvaluateRequest();
        submitEvaluateRequest.setStoreid(storeId);
        submitEvaluateRequest.setWords(evaluateEt.getText().toString());
        submitEvaluateRequest.setStarts(starts);
        submitEvaluateRequest.setType(typeAnonymous);
        submitEvaluateRequest.setOrderid(orderId);
        String[] strings = new String[photoPaths.size()];
        submitEvaluateRequest.setPics(photoPaths.toArray(strings));
        submitEvaluatePresenter.submitEvaluate(submitEvaluateRequest);
    }
}
