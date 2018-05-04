package com.purchase.sls.mine;

import com.purchase.sls.BasePresenter;
import com.purchase.sls.BaseView;

/**
 * Created by JWC on 2018/4/19.
 */

public interface PersonalCenterContract {
    interface PersonalImPresenter extends BasePresenter{
        void changeHeadPortrait(String photoUrl);
        void changeUserInfo(String nickname, String sex, String birthday);
    }
    interface PersonalImView extends BaseView<PersonalImPresenter>{
        void changeHeadPortraitSuccess();
        void changeUserInfoSuccess();
    }


}
