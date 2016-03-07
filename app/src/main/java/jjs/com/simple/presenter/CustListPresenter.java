package jjs.com.simple.presenter;

import jjs.com.simple.bean.CustListEntity;

/**
 * Created by JJS on 2016/3/2.
 */
public interface CustListPresenter {

    void loadListData(String requestTag, int event_tag, String name,String phone, int page, boolean isSwipeRefresh);

    void onItemClickListener(int position, CustListEntity itemData);
}
