package com.purchase.sls.messages;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/8.
 */
@Module
public class MessagesModule {
    private MessagesContract.MessageListView messageListView;

    public MessagesModule(MessagesContract.MessageListView messageListView) {
        this.messageListView = messageListView;
    }
    @Provides
    MessagesContract.MessageListView provideMessageListView(){
        return messageListView;
    }
}
