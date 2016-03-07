package jjs.com.simple.presenter.impl;

import android.content.Context;

import jjs.com.simple.R;
import jjs.com.simple.bean.CustListEntity;
import jjs.com.simple.bean.ResponseCustListEntity;
import jjs.com.simple.interactor.CustListInteractor;
import jjs.com.simple.interactor.impl.CustListInteractorImpl;
import jjs.com.simple.listeners.BaseMultiLoadedListener;
import jjs.com.simple.presenter.CustListPresenter;
import jjs.com.simple.ui.activity.qrcode.utils.Constants;
import jjs.com.simple.view.CustListView;

/**
 * Created by JJS on 2016/3/2.
 */
public class CustListPresenterImpl implements CustListPresenter, BaseMultiLoadedListener<ResponseCustListEntity> {


    private Context mContext;
    private CustListView mCustListView;
    private CustListInteractor mCustListInteractor;

    public CustListPresenterImpl(Context context, CustListView custListView){
        this.mContext = context;
        this.mCustListView = custListView;
        mCustListInteractor = new CustListInteractorImpl(this);
    }


    @Override
    public void loadListData(String requestTag, int event_tag, String name, String phone, int page, boolean isSwipeRefresh) {
        mCustListView.hideLoading();
        if (!isSwipeRefresh) {
            mCustListView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        mCustListInteractor.getCommonListData(requestTag, event_tag, name,phone, page);
    }

    @Override
    public void onItemClickListener(int position, CustListEntity itemData) {

    }

    @Override
    public void onSuccess(int event_tag, ResponseCustListEntity data) {
        mCustListView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mCustListView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mCustListView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mCustListView.hideLoading();
        mCustListView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mCustListView.hideLoading();
        mCustListView.showError(msg);
    }
}
