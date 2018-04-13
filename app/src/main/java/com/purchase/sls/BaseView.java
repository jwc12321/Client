package com.purchase.sls;

/**
 * Created by Administrator on 2017/12/15.
 * Description: MVP base view
 */

public interface BaseView<T> extends LoadDataView {
    void setPresenter(T presenter);
}
