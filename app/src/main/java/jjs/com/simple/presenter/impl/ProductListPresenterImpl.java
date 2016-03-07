package jjs.com.simple.presenter.impl;

import android.content.Context;

import com.github.obsessive.library.utils.TLog;

import jjs.com.simple.R;
import jjs.com.simple.bean.ProductListEntity;
import jjs.com.simple.bean.ResponseProductListEntity;
import jjs.com.simple.interactor.CommonListInteractor;
import jjs.com.simple.interactor.impl.ProductListInteractorImpl;
import jjs.com.simple.listeners.BaseMultiLoadedListener;
import jjs.com.simple.presenter.ProductListPresenter;
import jjs.com.simple.ui.activity.qrcode.utils.Constants;
import jjs.com.simple.view.ProductListView;

/**
 * Created by JJS on 2016/3/4.
 */
public class ProductListPresenterImpl implements ProductListPresenter, BaseMultiLoadedListener<ResponseProductListEntity> {

    private Context mContext;
    private ProductListView mView;
    private CommonListInteractor mCommonListInteractor;

    public ProductListPresenterImpl(Context context,ProductListView view ){
        this.mContext = context;
        this.mView = view;
        this.mCommonListInteractor = new ProductListInteractorImpl(this) ;
    }

    @Override
    public void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh) {
        mView.hideLoading();
        if (!isSwipeRefresh) {
            mView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        mCommonListInteractor.getCommonListData(requestTag, event_tag, keywords, page);
    }

    @Override
    public void onItemClickListener(int position, ProductListEntity itemData) {
        TLog.i("tag", position + "");
    }

    @Override
    public void onSuccess(int event_tag, ResponseProductListEntity data) {
        mView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mView.hideLoading();
        mView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mView.hideLoading();
        mView.showError(msg);
    }
}
