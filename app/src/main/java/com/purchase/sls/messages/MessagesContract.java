package com.purchase.sls.messages;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;
import com.purchase.sls.data.entity.MessageListInfo;

import java.util.List;

/**
 * Created by JWC on 2018/5/8.
 */

public interface MessagesContract {

    interface MessageListPresenter extends BasePresenter {
        void getMessageList(String refreshType,String type);

        void getMoreMessageList(String type);
    }

    interface MessageListView extends BaseView<MessageListPresenter> {
        void render(List<MessageListInfo.MessageItem> messageItems);

        void renderMore(List<MessageListInfo.MessageItem> messageItems);
    }
}
