package jjs.com.simple.presenter.impl;

import android.content.Context;

import com.github.obsessive.library.utils.TLog;

import java.util.ArrayList;
import java.util.List;

import jjs.com.simple.R;
import jjs.com.simple.bean.OrderListEntity;
import jjs.com.simple.bean.ResponseOrderListEntity;
import jjs.com.simple.interactor.CommonListInteractor;
import jjs.com.simple.interactor.impl.OrdersListInteractorImpl;
import jjs.com.simple.listeners.BaseMultiLoadedListener;
import jjs.com.simple.presenter.OrderListPresenter;
import jjs.com.simple.ui.activity.qrcode.utils.Constants;
import jjs.com.simple.view.OrderListView;

/**
 * Created by JJS on 2016/3/2.
 */
public class OrderListPresenterImpl implements OrderListPresenter, BaseMultiLoadedListener<ResponseOrderListEntity> {

    private Context mContext;
    private OrderListView mOrderListView;
    private CommonListInteractor mCommonListInteractor = null;

    public OrderListPresenterImpl(Context context, OrderListView orderListView) {
        this.mContext = context;
        this.mOrderListView = orderListView;
        this.mCommonListInteractor = new OrdersListInteractorImpl(this) ;
    }

    @Override
    public void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh) {
            mOrderListView.hideLoading();
            if (!isSwipeRefresh) {
                mOrderListView.showLoading(mContext.getString(R.string.common_loading_message));
            }
            mCommonListInteractor.getCommonListData(requestTag, event_tag, keywords, page);

    }

    @Override
    public void onItemClickListener(int position, OrderListEntity itemData) {
        TLog.i("tag",position+"");

    }

    @Override
    public void onSuccess(int event_tag, ResponseOrderListEntity data) {
        mOrderListView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mOrderListView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mOrderListView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mOrderListView.hideLoading();
        mOrderListView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mOrderListView.hideLoading();
        mOrderListView.showError(msg);
    }
}
