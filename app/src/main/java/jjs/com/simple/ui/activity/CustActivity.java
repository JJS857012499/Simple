package jjs.com.simple.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.MultiItemRowListAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jjs.com.simple.R;
import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.CustListEntity;
import jjs.com.simple.bean.ResponseCustListEntity;
import jjs.com.simple.presenter.CustListPresenter;
import jjs.com.simple.presenter.impl.CustListPresenterImpl;
import jjs.com.simple.ui.activity.base.BaseActivity;
import jjs.com.simple.ui.activity.qrcode.utils.Constants;
import jjs.com.simple.utils.UriHelper;
import jjs.com.simple.view.CustListView;
import jjs.com.simple.widgets.LoadMoreListView;

/**
 * Created by JJS on 2016/2/29.
 */
public class CustActivity extends BaseActivity implements CustListView, LoadMoreListView.OnLoadMoreListener {
    @InjectView(R.id.cust_name)
    EditText mName;
    @InjectView(R.id.cust_phone)
    EditText mPhone;
    @InjectView(R.id.cust_search)
    Button mSearch;
    @InjectView(R.id.cust_list_view)
    LoadMoreListView mListView;


    private String mCurrentName = "";
    private String mCurrentPhone = "";
    private int mCurrentPage = 1;

    private CustListPresenter mCustListPresenter = null;

    private MultiItemRowListAdapter mMultiItemRowListAdapter = null;
    private ListViewDataAdapter<CustListEntity> mListViewAdapter = null;
    
    
    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_cust;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return mListView;
    }

    @Override
    protected void initViewsAndEvents() {
        mCurrentPage = 1;
        mCustListPresenter = new CustListPresenterImpl(this, this);

        if (NetUtils.isNetworkConnected(mContext)) {
            mListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCustListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentName,mCurrentPhone,
                            mCurrentPage, false);
                }
            }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCustListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentName, mCurrentPhone,
                            mCurrentPage, false);
                }
            });
        }

        mListViewAdapter = new ListViewDataAdapter<CustListEntity>(new ViewHolderCreator<CustListEntity>() {
            @Override
            public ViewHolderBase<CustListEntity> createViewHolder(int position) {
                return new ViewHolderBase<CustListEntity>() {

                    ImageView mItemImage;
                    Button mItemPrint;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.list_item_custs_card, null);

//                        mItemImage = ButterKnife.findById(convertView, R.id.list_item_orders_pic);
//                        mItemPrint = ButterKnife.findById(convertView, R.id.list_item_orders_print);

                        return convertView;
                    }

                    @Override
                    public void showData(final int position, CustListEntity itemData) {
                        if (null != itemData) {
//                            mItemPrint.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (null != mListViewAdapter) {
//                                        if (position >= 0 && position < mListViewAdapter.getDataList().size()) {
//                                            mCustListPresenter.onItemClickListener(position, mListViewAdapter.getDataList().get(position));
//                                        }
//                                    }
//                                }
//                            });
                        }
                    }
                };
            }
        });

        mMultiItemRowListAdapter = new MultiItemRowListAdapter(mContext, mListViewAdapter, 1, 0);

        mListView.setAdapter(mMultiItemRowListAdapter);
        mListView.setOnLoadMoreListener(this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    @OnClick(R.id.cust_search)
    public void onClick() {
        mCurrentName = mName.getText().toString();
        mCurrentPhone = mPhone.getText().toString();
        mCurrentPage = 1;
        mCustListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentName,mCurrentPhone,mCurrentPage, true);
    }

    @Override
    public void onLoadMore() {
        mCurrentPage++;
        mCustListPresenter.loadListData(TAG_LOG, Constants.EVENT_LOAD_MORE_DATA, mCurrentName,mCurrentPhone,mCurrentPage, true);
    }


    @Override
    public void refreshListData(ResponseCustListEntity responseListEntity) {
        if (null != responseListEntity && null != responseListEntity.getCusts() && !responseListEntity.getCusts().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.getDataList().addAll(responseListEntity.getCusts());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (UriHelper.getInstance().calculateTotalPages(responseListEntity.getTotal()) > mCurrentPage) {
                mListView.setCanLoadMore(true);
            } else {
                mListView.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void addMoreListData(ResponseCustListEntity responseListEntity) {
        if (null != mListView) {
            mListView.onLoadMoreComplete();
        }

        if (null != responseListEntity && null != responseListEntity.getCusts() && !responseListEntity.getCusts().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().addAll(responseListEntity.getCusts());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (UriHelper.getInstance().calculateTotalPages(responseListEntity.getTotal()) > mCurrentPage) {
                mListView.setCanLoadMore(true);
            } else {
                mListView.setCanLoadMore(false);
            }
        }
    }
}
