package com.purchase.sls.common.widget.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.purchase.sls.R;


/**
 * Created by Administrator on 2016/8/16.
 */
public class TagLayoutConfiguration {

    private static final int DEFAULT_LINE_SPACING = 5;
    private static final int DEFAULT_TAG_SPACING = 10;
    private static final int DEFAULT_FIXED_COLUMN_SIZE = 3;

    private int lineSpacing;
    private int tagSpacing;
    private int columnSize;
    private boolean isFixed;


    public TagLayoutConfiguration(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TagLayout);
        try {
            lineSpacing = typedArray.getDimensionPixelSize(R.styleable.TagLayout_lineSpacing,DEFAULT_LINE_SPACING);
            tagSpacing = typedArray.getDimensionPixelSize(R.styleable.TagLayout_tagSpacing, DEFAULT_TAG_SPACING);
            columnSize = typedArray.getInteger(R.styleable.TagLayout_columnSize,DEFAULT_FIXED_COLUMN_SIZE);
            isFixed = typedArray.getBoolean(R.styleable.TagLayout_isFixed, false);

        } finally {
            typedArray.recycle();

        }

    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getTagSpacing() {
        return tagSpacing;
    }

    public void setTagSpacing(int tagSpacing) {
        this.tagSpacing = tagSpacing;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }
}
