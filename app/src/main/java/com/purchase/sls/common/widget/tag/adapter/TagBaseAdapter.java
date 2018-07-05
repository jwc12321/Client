package com.purchase.sls.common.widget.tag.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.purchase.sls.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/16.
 */
public class TagBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    public TagBaseAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setList(List<String> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }

    boolean isChangeStyle = false;
    public void setStyle(int textColor, String borderColor,String bgColor){
        this.textColor = textColor;
        this.borderColor = borderColor;
        this.bgColor = bgColor;
        if (bgColor != null && TextUtils.isEmpty(bgColor))
           isChangeStyle = false;
        else
            isChangeStyle = true;
    }
    int textColor;
    String borderColor;
    String bgColor;
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tagview, null);
            holder = new ViewHolder(convertView);
            holder.tagBtn = (TextView) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String text = getItem(position).toString();
        holder.tagBtn.setText(text);
        GradientDrawable gd = (GradientDrawable) holder.tagBtn.getBackground();
        if (isChangeStyle){
            int strokeColor = Color.parseColor(borderColor);//边框颜色
            int fillColor = Color.parseColor(bgColor);//内部填充颜色
            gd.setColor(fillColor);
            gd.setStroke(1,strokeColor);
            holder.tagBtn.setBackground(gd);
            holder.tagBtn.setTextSize(9.0f);
            holder.tagBtn.setTextColor(ContextCompat.getColor(holder.tagBtn.getContext(),textColor));
        } else {
            holder.tagBtn.setTextSize(14.0f);
            holder.tagBtn.setMinHeight(mContext.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.common_thirty_margin));
            holder.tagBtn.setMinWidth(mContext.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.common_forty_five_margin));
//            int strokeColor = Color.parseColor("#89e2dfdf");
//            int fillColor = Color.parseColor("#ffffff");
//            gd.setColor(fillColor);
//            gd.setStroke(1,strokeColor);
//            holder.tagBtn.setBackground(gd);
//            holder.tagBtn.setTextColor(ContextCompat.getColor(holder.tagBtn.getContext(),R.color.appText5));
        }
        return convertView;
    }



    static class ViewHolder {
        @BindView(R.id.tag_btn)
        TextView tagBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
