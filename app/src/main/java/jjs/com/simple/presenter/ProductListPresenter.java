package jjs.com.simple.presenter;

import jjs.com.simple.bean.ProductListEntity;

/**
 * Created by JJS on 2016/3/4.
 */
public interface ProductListPresenter {

    void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh);

    void onItemClickListener(int position, ProductListEntity itemData);
}
