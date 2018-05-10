package com.purchase.sls.messages.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.common.unit.FormatUtil;
import com.purchase.sls.common.widget.list.MoreLoadable;
import com.purchase.sls.common.widget.list.Refreshable;
import com.purchase.sls.data.entity.MessageListInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/8.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageView> implements Refreshable<MessageListInfo.MessageItem>, MoreLoadable<MessageListInfo.MessageItem> {
    private LayoutInflater layoutInflater;
    private List<MessageListInfo.MessageItem> messageItems;

    public void setData(List<MessageListInfo.MessageItem> messageItems) {
        this.messageItems = messageItems;
        notifyDataSetChanged();
    }

    @Override
    public MessageView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_message, parent, false);
        return new MessageView(view);
    }

    @Override
    public void onBindViewHolder(MessageView holder, int position) {
        final MessageListInfo.MessageItem messageItem = messageItems.get(holder.getAdapterPosition());
        holder.bindData(messageItem);
        holder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.itemClick(messageItem.getType(), messageItem.getMessageId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageItems == null ? 0 : messageItems.size();
    }

    @Override
    public void refresh(List<MessageListInfo.MessageItem> list) {
        this.messageItems = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<MessageListInfo.MessageItem> list) {
        int pos = messageItems.size();
        messageItems.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }

    public class MessageView extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.explain)
        TextView explain;
        @BindView(R.id.item_ll)
        LinearLayout itemLl;

        public MessageView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(MessageListInfo.MessageItem messageItem) {
            time.setText(FormatUtil.formatDateByLine(messageItem.getSendtime()));
            if (TextUtils.equals("1", messageItem.getType())) {
                title.setText("优惠券提醒");
                explain.setText("您有" + messageItem.getNumber() + "张即将过期的优惠券");
            } else if (TextUtils.equals("2", messageItem.getType())) {
                title.setText("评价提醒");
                explain.setText("您有" + messageItem.getNumber() + "条未评价订单");
            } else {
                title.setText("消息提醒");
                explain.setText(messageItem.getTitle());
            }
        }
    }

    public interface OnItemClick {
        void itemClick(String type, String url);
    }

    private OnItemClick onItemClick;

    public void setonOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
