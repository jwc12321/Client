package com.purchase.sls.mine;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */

@Module
public class PersonalCenterModule {
    private PersonalCenterContract.PersonalImView personalImView;
    private PersonalCenterContract.ShiftHandsetView shiftHandsetView;
    private PersonalCenterContract.SettingView settingView;
    private PersonalCenterContract.PersonalCenterView personalCenterView;

    public PersonalCenterModule(PersonalCenterContract.PersonalImView personalImView) {
        this.personalImView = personalImView;
    }

    public PersonalCenterModule(PersonalCenterContract.ShiftHandsetView shiftHandsetView) {
        this.shiftHandsetView = shiftHandsetView;
    }

    public PersonalCenterModule(PersonalCenterContract.SettingView settingView) {
        this.settingView = settingView;
    }

    public PersonalCenterModule(PersonalCenterContract.PersonalCenterView personalCenterView) {
        this.personalCenterView = personalCenterView;
    }

    @Provides
    PersonalCenterContract.PersonalImView providePersonalImView(){
        return personalImView;
    }
    @Provides
    PersonalCenterContract.ShiftHandsetView provideShiftHandsetView(){
        return shiftHandsetView;
    }
    @Provides
    PersonalCenterContract.SettingView provideSettingView(){
        return settingView;
    }

    @Provides
    PersonalCenterContract.PersonalCenterView providePersonalCenterView(){
        return personalCenterView;
    }
}
