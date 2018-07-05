package com.purchase.sls.common.widget.tag;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class TagLayout extends ViewGroup {

    private int mLineSpacing;
    private int mTagSpacing;
    private BaseAdapter mAdapter;
    private TagItemClickListener mListener;
    private DataChangeObserver mObserver;

    private boolean isLimit = false;
    private int limitCount = 0;

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimit(boolean limit) {
        isLimit = limit;
    }

    public TagLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TagLayoutConfiguration tagLayoutConfiguration = new TagLayoutConfiguration(context,attrs);
        mLineSpacing = tagLayoutConfiguration.getLineSpacing();
        mTagSpacing = tagLayoutConfiguration.getTagSpacing();
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mAllViews.clear();
        mLineHeight.clear();
        int wantHeight = 0;
        int wantWidth = resolveSize(0, widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;
        int line = 1;
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();


        for (int i = 0; i < getChildCount(); i++) {
            final View childView = getChildAt(i);
            LayoutParams params = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, params.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, params.height)
            );
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();

            if (childLeft + childWidth + paddingRight > wantWidth) {
                line++;
                if (isLimit && (line  > getLimitCount())){
                    lineNumListener.getLineNum(line);
                    break;
                }
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineViews = new ArrayList<View>();
                childLeft = paddingLeft;
                childTop += mLineSpacing + childHeight;
                lineHeight = childHeight;


            }
            lineHeight = Math.max(childHeight, lineHeight);
            childLeft += childWidth + mTagSpacing;
            lineViews.add(childView);

        }
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        wantHeight += childTop + lineHeight + paddingBottom;
        setMeasuredDimension(wantWidth, resolveSize(wantHeight, heightMeasureSpec));

    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lineHeight = 0;
        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++)
        {
            // 每一行的所有的views
            List<View> lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);
            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++)
            {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE)
                {
                    continue;
                }
                //计算childView的left,top,right,bottom
                int lc = left + getPaddingLeft();
                int tc = top + getPaddingRight() ;
                int rc =lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + getPaddingRight()
                        + getPaddingLeft()+ mTagSpacing;
            }
            left = 0;
            top += lineHeight + mLineSpacing;
        }
    }
    private getLineNumListener lineNumListener;

    public void setLineNumListener(getLineNumListener lineNumListener) {
        this.lineNumListener = lineNumListener;
    }

    public interface getLineNumListener {
        void getLineNum(int line);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    public void setAdapter(BaseAdapter adapter){
        if (mAdapter == null){
            mAdapter = adapter;
            if (mObserver == null){
                mObserver = new DataChangeObserver();
                mAdapter.registerDataSetObserver(mObserver);
            }
            drawLayout();
        }
    }

    //每个标签的点击事件，暂时没有用到
    public void setItemClickListener(TagItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface TagItemClickListener {
        void itemClick(int position);
    }

    class DataChangeObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            TagLayout.this.drawLayout();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
    private void drawLayout() {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }

        this.removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i,null,null);
            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.itemClick(position);
                    }
                }
            });
            this.addView(view);
        }
    }
}
