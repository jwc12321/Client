package com.purchase.sls.common.widget.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.PhoneNumberUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallDialogFragment extends DialogFragment {

    private static final String KEY_PHONE = "phone";
    private static final String KEY_TITLE = "title";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView contentTextView;
    @BindView(R.id.button_cancel)
    TextView buttonCancel;
    @BindView(R.id.button_confirm)
    TextView buttonConfirm;
    @BindView(R.id.container)
    LinearLayout container;


    private String phone;
    private String titleString;

    public static CallDialogFragment newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString(KEY_PHONE, phoneNumber);
        CallDialogFragment fragment = new CallDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CallDialogFragment newInstance(String phoneNumber, String title) {

        Bundle args = new Bundle();
        args.putString(KEY_PHONE, phoneNumber);
        args.putString(KEY_TITLE, title);
        CallDialogFragment fragment = new CallDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fillet_dialog_common, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            phone = getArguments().getString(KEY_PHONE);
            titleString = getArguments().getString(KEY_TITLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        title.setText(titleString);
        String content = "";
        SpannableString ss = new SpannableString(phone);
//        if (getString(R.string.service_phone_number).equals(phone)) {
//            ss = new SpannableString(phone);
//        } else {
//            ss = new SpannableString(PhoneNumberUtils.encryptPhone(phone));
//        }
        ss.setSpan(new ForegroundColorSpan(Color.BLACK), content.length(), ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        contentTextView.setText(ss);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @OnClick({R.id.button_cancel, R.id.button_confirm})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                dismissAllowingStateLoss();
                break;
            case R.id.button_confirm:
                //or ACTION_DIAL ??

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                dismissAllowingStateLoss();
        }
    }
}
