package jjs.com.simple.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.MultiItemRowListAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jjs.com.simple.R;
import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.ProductListEntity;
import jjs.com.simple.bean.ResponseProductListEntity;
import jjs.com.simple.presenter.ProductListPresenter;
import jjs.com.simple.presenter.impl.ProductListPresenterImpl;
import jjs.com.simple.ui.activity.base.BaseActivity;
import jjs.com.simple.ui.activity.qrcode.CaptureActivity;
import jjs.com.simple.ui.activity.qrcode.utils.Constants;
import jjs.com.simple.utils.UriHelper;
import jjs.com.simple.view.ProductListView;
import jjs.com.simple.widgets.LoadMoreListView;

/**
 * Created by JJS on 2016/2/29.
 */
public class ProductActivity extends BaseActivity implements LoadMoreListView.OnLoadMoreListener, ProductListView {

    private static final int REQUEST_SCAN = 0x11;
    @InjectView(R.id.product_no)
    EditText mNo;
    @InjectView(R.id.product_search)
    Button mSearch;
    @InjectView(R.id.product_scan)
    Button mScan;
    @InjectView(R.id.product_list_view)
    LoadMoreListView mListView;

    private String mCurrentNo = "";
    private int mCurrentPage = 1;

    private ProductListPresenter mProductListPresenter = null;

    private MultiItemRowListAdapter mMultiItemRowListAdapter = null;
    private ListViewDataAdapter<ProductListEntity> mListViewAdapter = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_product;
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
        mProductListPresenter = new ProductListPresenterImpl(this, this);

        if (NetUtils.isNetworkConnected(mContext)) {
            mListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProductListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentNo,
                            mCurrentPage, false);
                }
            }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProductListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentNo,
                            mCurrentPage, false);
                }
            });
        }

        mListViewAdapter = new ListViewDataAdapter<ProductListEntity>(new ViewHolderCreator<ProductListEntity>() {
            @Override
            public ViewHolderBase<ProductListEntity> createViewHolder(int position) {
                return new ViewHolderBase<ProductListEntity>() {

                    ImageView mItemImage;
                    LinearLayout mItemParams;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.list_item_products_card, null);

                        mItemImage = ButterKnife.findById(convertView, R.id.list_item_products_pic);
                        mItemParams = ButterKnife.findById(convertView, R.id.list_item_products_params);

                        return convertView;
                    }

                    @Override
                    public void showData(final int position, ProductListEntity itemData) {
                        if (null != itemData) {
//                            mItemPrint.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (null != mListViewAdapter) {
//                                        if (position >= 0 && position < mListViewAdapter.getDataList().size()) {
//                                            mProductListPresenter.onItemClickListener(position, mListViewAdapter.getDataList().get(position));
//                                        }
//                                    }
//                                }
//                            });

                           int params =  new Random().nextInt(8);
                            if(mItemParams.getChildCount() > 0){
                                mItemParams.removeAllViews();
                            }
                            for(int i=0;i<params;i++){
                                View paramView = LayoutInflater.from(ProductActivity.this).inflate(R.layout.list_item_common_text, null);
                                TextView paramsTv = ButterKnife.findById(paramView,R.id.list_item_common_text_tv);
                                paramsTv.setText("参数"+i);
                                mItemParams.addView(paramView);
                            }

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
        return TransitionMode.RIGHT;
    }

    @OnClick({R.id.product_search, R.id.product_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_search:
                mCurrentNo = mNo.getText().toString();
                mCurrentPage = 1;
                mProductListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentNo, mCurrentPage, true);
                break;
            case R.id.product_scan:
                readyGoForResult(CaptureActivity.class, REQUEST_SCAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_SCAN) {
            String no = (String) data.getExtras().get(Constants.BUNDLE_KEY_SCAN_RESULT);
            mNo.setText(no);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoadMore() {
        mCurrentPage++;
        mProductListPresenter.loadListData(TAG_LOG, Constants.EVENT_LOAD_MORE_DATA, mCurrentNo, mCurrentPage, true);
    }

    @Override
    public void refreshListData(ResponseProductListEntity responseListEntity) {
        if (null != responseListEntity && null != responseListEntity.getProducts() && !responseListEntity.getProducts().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.getDataList().addAll(responseListEntity.getProducts());
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
    public void addMoreListData(ResponseProductListEntity responseListEntity) {
        if (null != mListView) {
            mListView.onLoadMoreComplete();
        }

        if (null != responseListEntity && null != responseListEntity.getProducts() && !responseListEntity.getProducts().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().addAll(responseListEntity.getProducts());
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
