package com.purchase.sls.mine;

/**
 * Created by JWC on 2018/4/19.
 */

import com.purchase.sls.ActivityScope;
import com.purchase.sls.ApplicationComponent;
import com.purchase.sls.mine.ui.ChangeNickNameActivity;
import com.purchase.sls.mine.ui.PersonalInformationActivity;
import com.purchase.sls.mine.ui.SettingActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {PersonalCenterModule.class})
public interface PersonalCenterComponent {
    void inject(SettingActivity settingActivity);
    void inject(PersonalInformationActivity personalInformationActivity);
    void inject(ChangeNickNameActivity changeNickNameActivity);
}
