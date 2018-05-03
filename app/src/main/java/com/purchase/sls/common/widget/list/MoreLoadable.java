package com.purchase.sls.common.widget.list;

import java.util.List;

/**
 * 用于列表activity或者fragment的上拉加载，其adapter必须实现该接口，以实现回调刷新数据
 * @param <T>
 */
public interface MoreLoadable<T> {

    void addMore(List<T> list);
}
