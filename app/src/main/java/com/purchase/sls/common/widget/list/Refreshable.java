package com.purchase.sls.common.widget.list;

import java.util.List;

/**
 * 用于列表activity或者fragment的下拉刷新，其adapter必须实现该接口，以实现回调刷新数据
 * @param <T>
 */
public interface Refreshable<T> {

    void refresh(List<T> list);
}
