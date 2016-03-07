package jjs.com.simple.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.MultiItemRowListAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jjs.com.simple.R;
import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.OrderListEntity;
import jjs.com.simple.bean.ResponseOrderListEntity;
import jjs.com.simple.presenter.OrderListPresenter;
import jjs.com.simple.presenter.impl.OrderListPresenterImpl;
import jjs.com.simple.ui.activity.base.BaseActivity;
import jjs.com.simple.ui.activity.qrcode.CaptureActivity;
import jjs.com.simple.ui.activity.qrcode.utils.Constants;
import jjs.com.simple.utils.UriHelper;
import jjs.com.simple.view.OrderListView;
import jjs.com.simple.widgets.LoadMoreListView;

/**
 * Created by JJS on 2016/2/29.
 */
public class OrderActivity extends BaseActivity  implements OrderListView, LoadMoreListView.OnLoadMoreListener {


    private static final int REQUEST_SCAN = 0x11;
    @InjectView(R.id.order_no)
    EditText mOrderNo;
    @InjectView(R.id.order_search)
    Button mOrderSearch;
    @InjectView(R.id.order_scan)
    Button mOrderScan;
    @InjectView(R.id.order_btn)
    LinearLayout mOrderBtn;
    @InjectView(R.id.order_list_view)
    LoadMoreListView mOrderListView;

    private String mCurrentOrderNo = "";
    private int mCurrentPage = 1;

    private OrderListPresenter mOrderListPresenter = null;

    private MultiItemRowListAdapter mMultiItemRowListAdapter = null;
    private ListViewDataAdapter<OrderListEntity> mListViewAdapter = null;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return mOrderListView;
    }

    @Override
    protected void initViewsAndEvents() {
        mCurrentPage = 1;
        mOrderListPresenter = new OrderListPresenterImpl(this, this);

        if (NetUtils.isNetworkConnected(mContext)) {
            mOrderListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOrderListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentOrderNo,
                                mCurrentPage, false);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOrderListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentOrderNo,
                            mCurrentPage, false);
                }
            });
        }

        mListViewAdapter = new ListViewDataAdapter<OrderListEntity>(new ViewHolderCreator<OrderListEntity>() {
            @Override
            public ViewHolderBase<OrderListEntity> createViewHolder(int position) {
                return new ViewHolderBase<OrderListEntity>() {

                    ImageView mItemImage;
                    Button mItemPrint;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.list_item_orders_card, null);

                        mItemImage = ButterKnife.findById(convertView, R.id.list_item_orders_pic);
                        mItemPrint = ButterKnife.findById(convertView, R.id.list_item_orders_print);

                        return convertView;
                    }

                    @Override
                    public void showData(final int position, OrderListEntity itemData) {
                        if (null != itemData) {

//                            if (!CommonUtils.isEmpty(itemData.getThumbnail_v2())) {
//                                ImageLoader.getInstance().displayImage(itemData.getThumbnail_v2(), mItemImage);
//                            }

                            mItemPrint.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (null != mListViewAdapter) {
                                        if (position >= 0 && position < mListViewAdapter.getDataList().size()) {
                                            mOrderListPresenter.onItemClickListener(position, mListViewAdapter.getDataList().get(position));
                                        }
                                    }
                                }
                            });
                        }
                    }
                };
            }
        });

        mMultiItemRowListAdapter = new MultiItemRowListAdapter(mContext, mListViewAdapter, 1, 0);

        mOrderListView.setAdapter(mMultiItemRowListAdapter);
        mOrderListView.setOnLoadMoreListener(this);

//        mSwipeRefreshLayout.setColorSchemeColors(
//                getResources().getColor(R.color.gplus_color_1),
//                getResources().getColor(R.color.gplus_color_2),
//                getResources().getColor(R.color.gplus_color_3),
//                getResources().getColor(R.color.gplus_color_4));
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        
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
        return TransitionMode.RIGHT;
    }


    @OnClick({R.id.order_search, R.id.order_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_search:
                mCurrentOrderNo = mOrderNo.getText().toString();
                mCurrentPage = 1;
                mOrderListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentOrderNo,mCurrentPage, true);
                break;
            case R.id.order_scan:
                readyGoForResult(CaptureActivity.class, REQUEST_SCAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_SCAN){
            String no = (String) data.getExtras().get(Constants.BUNDLE_KEY_SCAN_RESULT);
            mOrderNo.setText(no);
        }

    }

    @Override
    public void refreshListData(ResponseOrderListEntity responseOrderListEntity) {
        if (null != responseOrderListEntity && null != responseOrderListEntity.getOrders() && !responseOrderListEntity.getOrders().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.getDataList().addAll(responseOrderListEntity.getOrders());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (UriHelper.getInstance().calculateTotalPages(responseOrderListEntity.getTotal()) > mCurrentPage) {
                mOrderListView.setCanLoadMore(true);
            } else {
                mOrderListView.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void addMoreListData(ResponseOrderListEntity responseOrderListEntity) {
        if (null != mOrderListView) {
            mOrderListView.onLoadMoreComplete();
        }

        if (null != responseOrderListEntity && null != responseOrderListEntity.getOrders() && !responseOrderListEntity.getOrders().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().addAll(responseOrderListEntity.getOrders());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (UriHelper.getInstance().calculateTotalPages(responseOrderListEntity.getTotal()) > mCurrentPage) {
                mOrderListView.setCanLoadMore(true);
            } else {
                mOrderListView.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void onLoadMore() {
        mCurrentPage++;
        System.out.print(mCurrentPage);
        mOrderListPresenter.loadListData(TAG_LOG, Constants.EVENT_LOAD_MORE_DATA, mCurrentOrderNo, mCurrentPage, true);
    }
}
