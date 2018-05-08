package com.purchase.sls.messages;

/**
 * Created by JWC on 2018/5/8.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.messages.ui.CouponMessageFragment;
import com.purchase.sls.messages.ui.MessageNotificationActivity;
import com.purchase.sls.messages.ui.NoticeMessageFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MessagesModule.class})
public interface MessagesComponent {
    void inject(MessageNotificationActivity messageNotificationActivity);
    void inject(NoticeMessageFragment noticeMessageFragment);
    void inject(CouponMessageFragment couponMessageFragment);
}
