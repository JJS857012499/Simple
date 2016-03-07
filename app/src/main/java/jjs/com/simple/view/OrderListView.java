package jjs.com.simple.view;


import jjs.com.simple.bean.ResponseOrderListEntity;
import jjs.com.simple.view.base.BaseView;

/**
 * Created by JJS on 2016/3/1.
 */
public interface OrderListView extends BaseView {

    void refreshListData(ResponseOrderListEntity responseOrderListEntity );

    void addMoreListData(ResponseOrderListEntity responseOrderListEntity);
}
