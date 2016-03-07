package jjs.com.simple.presenter;

import jjs.com.simple.bean.OrderListEntity;

/**
 * Created by JJS on 2016/3/2.
 */
public interface OrderListPresenter {

    void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh);

    void onItemClickListener(int position, OrderListEntity itemData);
}
