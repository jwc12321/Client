package com.purchase.sls.mine;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */

@Module
public class PersonalCenterModule {
    private PersonalCenterContract.PersonalImView personalImView;

    public PersonalCenterModule(PersonalCenterContract.PersonalImView personalImView) {
        this.personalImView = personalImView;
    }
    @Provides
    PersonalCenterContract.PersonalImView providePersonalImView(){
        return personalImView;
    }
}
