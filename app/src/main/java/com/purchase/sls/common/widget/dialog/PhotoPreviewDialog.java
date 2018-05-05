package com.purchase.sls.common.widget.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.purchase.sls.R;
import com.purchase.sls.common.widget.adapter.ViewPageAdapter;
import com.purchase.sls.common.widget.customeview.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2016/9/22.
 */

public class PhotoPreviewDialog extends DialogFragment implements ViewPager.OnPageChangeListener, ViewPageAdapter.OnDismissListener, View.OnClickListener {

    private static String KEY = "ARGS_KEY";
    @BindView(R.id.viewPager)
    ViewPagerFixed viewPager;
    @BindView(R.id.indicator)
    LinearLayout indicator;
    private Activity context;
    private int index = -1;
    private List<ImageView> indicators;
    private int mIndicatorSelectedResId = R.drawable.gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.white_radius;
    private int mIndicatorMargin = PADDING_SIZE;
    private int mIndicatorWidth = INDICATOR_SIZE;
    private int mIndicatorHeight = INDICATOR_SIZE;
    public static final int INDICATOR_SIZE=20;
    public static final int PADDING_SIZE=10;
    private int lastPosition=0;
    private ViewPageAdapter adapter;

    public static PhotoPreviewDialog newInstance(Param param) {

        Bundle args = new Bundle();
        args.putParcelable(KEY, param);
        PhotoPreviewDialog fragment = new PhotoPreviewDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.BlackNoTitle);


    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.photo_browser_layout, container);
//        rootView.setOnClickListener(this);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }
    private void createIndicator(int count,int position) {
        if ( count > 1 ){
            indicator.setVisibility(View.VISIBLE);
            if (indicators == null)
                indicators = new ArrayList<>();
            else
                indicators.clear();
            indicator.removeAllViews();
            for (int i = 0; i < count; i++) {
                ImageView imageView = new ImageView(getContext());
                indicators.add(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth,mIndicatorHeight);
                params.leftMargin = mIndicatorMargin;
                params.rightMargin = mIndicatorMargin;
                if(i == position){
                    imageView.setImageResource(mIndicatorSelectedResId);
                    lastPosition = position;
                }else{
                    imageView.setImageResource(mIndicatorUnselectedResId);
                }
                indicator.addView(imageView, params);
            }
        } else
            indicator.setVisibility(View.GONE);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        Param param = args.getParcelable(KEY);
        List<String> filePaths = param.filePaths;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int count = param.filePaths.size();
        index = index == -1 ? param.index : index;
        List<View> views = new ArrayList<>();
        if(adapter == null && filePaths != null && count != 0){
            createIndicator(count,index);
            adapter = new ViewPageAdapter(getContext(),filePaths);
            adapter.setOnDismissListener(this);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(index);
            viewPager.addOnPageChangeListener(this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        indicators.get(lastPosition).setImageResource(mIndicatorUnselectedResId);
        indicators.get(position).setImageResource(mIndicatorSelectedResId);
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void dimiss() {
        getDialog().dismiss();
    }

    @Override
    public void onClick(View v) {
        dimiss();
    }


    public static class Builder {
        Param param;
        int index;
        List<String> filePaths;

        public Builder() {
            param = new Param();
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder path(List<String> filePaths) {
            this.filePaths = filePaths;
            return this;
        }

        public PhotoPreviewDialog build() {
            param.index = index;
            param.filePaths = filePaths;
            PhotoPreviewDialog dialog = PhotoPreviewDialog.newInstance(param);
            return dialog;
        }
    }

    public static class Param implements Parcelable {
        public int index;
        public List<String> filePaths;


        public Param() {
        }

        protected Param(Parcel in) {
            index = in.readInt();
            filePaths = in.createStringArrayList();
        }

        public static final Creator<Param> CREATOR = new Creator<Param>() {
            @Override
            public Param createFromParcel(Parcel in) {
                return new Param(in);
            }

            @Override
            public Param[] newArray(int size) {
                return new Param[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(index);
            dest.writeStringList(filePaths);
        }
    }
}
