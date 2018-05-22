package com.purchase.sls.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.purchase.sls.R;


/**
 * Created by Administrator on 2016/12/26.
 */

public class MyRatingBar extends LinearLayout {
    private int mImageWidth = 20;  //图片设置默认的宽度
    private int mImageHeight = 20; //图片设置默认的高度
    private int mDefaultImageId = R.mipmap.star_in;
    private int mClickImageId = R.mipmap.star_out;
    private int mHalfImageId = R.mipmap.star3;
    private int mMargin = 5;   //图片之间默认的margin
    private int mStarNum = 5;  //星星默认的个数
    private int mStarChoose = 3;  //默认默认是三颗星
    private float mScope = 1.0f;
    private boolean isClick = true;
    public MyRatingBar(Context context) {
        super(context);
    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context,attrs);
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context,attrs);
    }
    private OnStarItemClickListener mStarItemClickListener;
    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);  //设置水平
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StarView, 0, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.StarView_mImageWidth:
                    mImageWidth = (int) a.getDimension(attr, mImageWidth);
                    break;
                case R.styleable.StarView_mImageHeight:
                    mImageHeight = (int) a.getDimension(attr, mImageHeight);
                    break;
                case R.styleable.StarView_mDefaultImageId:
                    mDefaultImageId = a.getResourceId(attr, mDefaultImageId);
                    break;
                case R.styleable.StarView_mHalfImageId:
                    mHalfImageId = a.getResourceId(attr, mHalfImageId);
                    break;
                case R.styleable.StarView_mClickImageId:
                    mClickImageId = a.getResourceId(attr, mClickImageId);
                    break;
                case R.styleable.StarView_mMargin:
                    mMargin = (int) a.getDimension(attr, mMargin);
                    break;
                case R.styleable.StarView_mStarNum:
                    mStarNum = a.getInt(attr, mStarNum);
                    break;
                case R.styleable.StarView_mStarChoose:
                    mStarChoose = a.getInt(attr, mStarChoose);
                    break;
                case R.styleable.StarView_mScope:
                    mScope = a.getFloat(attr , mScope);
                    break;
            }
        }
        a.recycle();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setStarNum(mStarNum);  //设置个数
    }

    /**
     * 设置星星数量
     *
     * @param number
     */
    public void setStarNum(int number) {
        if (number <= 0) {
            try {
                throw new Exception("设置的数据不能小于等于零");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.removeAllViews(); //清空所有view
        for (int i = 0; i < number; i++) {
            ImageView imageView = new ImageView(getContext());
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mImageWidth, mImageHeight);
            layoutParams.leftMargin = mMargin;
            layoutParams.rightMargin = mMargin;
            imageView.setLayoutParams(layoutParams);
            this.addView(imageView);
            imageView.setImageResource(mDefaultImageId);
            //可以用
//            setStarOnClick(imageView, i);
        }
        setCurrentChoose(mStarChoose);  //设置当前选择
        setmScope(mScope);
    }


    public void setmScope(float mScope) {
        if (mScope < 0){
            try {
                throw new Exception("设置的数据不能小于等于零");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mScope == 0.0){
            for (int i = 0; i < mStarNum; i++){
                resetDefaultImage();
            }
        } else {
            for (int i = 0; i < Math.ceil(mScope); i++){
                ImageView imageView = (ImageView)getChildAt(i);
                if (i == (Math.ceil(mScope) - 1)){
                    switch (mockHalfStar(mScope)){
                        case CHOOSE_EMPTY_IMAGVIEW:
                            imageView.setImageResource(mDefaultImageId);
                            break;
                        case CHOOSE_HALF_IMAGEVIEW:
                            imageView.setImageResource(mHalfImageId);
                            break;
                        case CHOOSE_FULL_IMAGEVIEW:
                            imageView.setImageResource(mClickImageId);
                            break;
                    }
                } else {
                    imageView.setImageResource(mClickImageId);
                }
            }
        }

    }

    /**
     * 0.0-0.2区间用0星
     * 0.2-0.7区间用半星
     * 0.7-1.0区间用全星
     * @param scope
     * @return
     */
    private final int CHOOSE_HALF_IMAGEVIEW = 1;
    private final int CHOOSE_FULL_IMAGEVIEW = 2;
    private final int CHOOSE_EMPTY_IMAGVIEW = 3;
    private int mockHalfStar(float scope){
        float offSet = scope - (float) Math.floor(scope);
        if (offSet == 0.0){
            return CHOOSE_FULL_IMAGEVIEW;
        } else if (offSet > 0.2 && offSet < 0.7){
            return CHOOSE_HALF_IMAGEVIEW;
        } else if (offSet >= 0.7){
            return CHOOSE_FULL_IMAGEVIEW;
        } else {
            return CHOOSE_EMPTY_IMAGVIEW;
        }
    }
    /**
     * 设置点击事件
     *
     * @param imageView
     * @param i
     */
    private void setStarOnClick(final ImageView imageView, final int i) {
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetDefaultImage();
                    setCurrentChoose(i+1);
                    if (mStarItemClickListener != null) {
                        mStarItemClickListener.onItemClick(imageView, i);
                    }
                }
            });
        }
    }

    /**
     * 设置当前选择
     *
     * @param index
     */
    private void setCurrentChoose(int index) {
        if(isClick){
            for (int i = 0; i < index; i++) {
                ImageView imageView = (ImageView) getChildAt(i);
                imageView.setImageResource(mClickImageId);
            }
        }
    }

    /**
     * 重置默认为默认的图片
     */
    private void resetDefaultImage() {
        int cNum = getChildCount();
        for (int i = 0; i < cNum; i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            imageView.setImageResource(mDefaultImageId);
        }
    }

    public int getmImageWidth() {
        return mImageWidth;
    }

    public void setmImageWidth(int mImageWidth) {
        this.mImageWidth = mImageWidth;
    }

    public int getmImageHeight() {
        return mImageHeight;
    }

    public void setmImageHeight(int mImageHeight) {
        this.mImageHeight = mImageHeight;
    }

    public int getmDefaultImageId() {
        return mDefaultImageId;
    }

    public void setmDefaultImageId(int mDefaultImageId) {
        this.mDefaultImageId = mDefaultImageId;
    }

    public int getmClickImageId() {
        return mClickImageId;
    }

    public void setmClickImageId(int mClickImageId) {
        this.mClickImageId = mClickImageId;
    }

    public int getmHalfImageId() {
        return mHalfImageId;
    }

    public void setmHalfImageId(int mHalfImageId) {
        this.mHalfImageId = mHalfImageId;
    }

    public int getmMargin() {
        return mMargin;
    }

    public void setmMargin(int mMargin) {
        this.mMargin = mMargin;
    }

    public int getmStarNum() {
        return mStarNum;
    }

    public void setmStarNum(int mStarNum) {
        this.mStarNum = mStarNum;
    }

    public int getmStarChoose() {
        return mStarChoose;
    }

    public void setmStarChoose(int mStarChoose) {
        this.mStarChoose = mStarChoose;
    }

    public float getmScope() {
        return mScope;
    }



    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public OnStarItemClickListener getmStarItemClickListener() {
        return mStarItemClickListener;
    }

    public int getImageWidth() {
        return mImageWidth;
    }

    public void setImageWidth(int mImageWidht) {
        this.mImageWidth = mImageWidht;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    public void setImageHeight(int mImageHeight) {
        this.mImageHeight = mImageHeight;
    }

    public int getDefaultImageId() {
        return mDefaultImageId;
    }

    public void setDefaultImageId(int resouceId) {
        this.mDefaultImageId = mDefaultImageId;
    }

    public int getClickImageId() {
        return mClickImageId;
    }

    public void setClickImageId(int mClickImageId) {
        this.mClickImageId = mClickImageId;
    }

    public OnStarItemClickListener getStarItemClickListener() {
        return mStarItemClickListener;
    }

    public void setmStarItemClickListener(OnStarItemClickListener mStarItemClickListener) {
        this.mStarItemClickListener = mStarItemClickListener;
    }

    /**
     * 星星点击事件
     */
    public interface OnStarItemClickListener {
        public void onItemClick(View view, int pos);
    }


}
