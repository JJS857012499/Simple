package jjs.com.simple.view;

import jjs.com.simple.bean.ResponseProductListEntity;
import jjs.com.simple.view.base.BaseView;

/**
 * Created by JJS on 2016/3/4.
 */
public interface ProductListView extends BaseView {

    void refreshListData(ResponseProductListEntity responseListEntity );

    void addMoreListData(ResponseProductListEntity responseListEntity);
}
