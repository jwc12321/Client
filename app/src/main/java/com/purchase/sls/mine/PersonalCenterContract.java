package com.purchase.sls.mine;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;

/**
 * Created by JWC on 2018/4/19.
 */

public interface PersonalCenterContract {
    interface PersonalImPresenter extends BasePresenter {
        void changeHeadPortrait(String photoUrl);

        void changeUserInfo(String nickname, String sex, String birthday);
    }

    interface PersonalImView extends BaseView<PersonalImPresenter> {
        void changeHeadPortraitSuccess(String phoneUrl);

        void changeUserInfoSuccess();
    }

    interface ShiftHandsetPresenter extends BasePresenter {
        void sendOldVcode(String tel, String dostr);

        void checkOldCode(String tel, String code, String type);

        void sendNewVCode(String newtel);

        void checkNewCode(String newtel, String code);
    }

    interface ShiftHandsetView extends BaseView<ShiftHandsetPresenter> {
        void oldVcodeSuccess();

        void checkOldCodeSuccess();

        void newVcodeSuccess();

        void checkNewCodeSuccess();
    }

    interface SettingPresenter extends BasePresenter {
        void detectionVersion(String edition, String type);
    }

    interface SettingView extends BaseView<SettingPresenter> {
        void detectionSuccess();
    }
}
