package jjs.com.simple.view;

import jjs.com.simple.bean.ResponseCustListEntity;
import jjs.com.simple.view.base.BaseView;

/**
 * Created by JJS on 2016/3/3.
 */
public interface CustListView extends BaseView {

    void refreshListData( ResponseCustListEntity responseListEntity );

    void addMoreListData(ResponseCustListEntity responseListEntity);
}
