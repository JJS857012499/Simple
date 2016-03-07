package jjs.com.simple.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jjs.com.simple.R;
import jjs.com.simple.ui.activity.base.BaseActivity;
import jjs.com.simple.view.HomeView;
import jjs.com.simple.widgets.LoadMoreListView;

public class HomeActivity extends BaseActivity implements HomeView {
    private static long DOUBLE_CLICK_TIME = 0L;

    @InjectView(R.id.home_price1)
    TextView mPrice1;
    @InjectView(R.id.home_price2)
    TextView mPrice2;
    @InjectView(R.id.home_price3)
    TextView mPrice3;
    @InjectView(R.id.home_order)
    Button mOrder;
    @InjectView(R.id.home_cust)
    Button mCust;
    @InjectView(R.id.home_notice_list)
    LinearLayout mNoticeList;
    @InjectView(R.id.home_product)
    Button homeProduct;
    @InjectView(R.id.home_me)
    Button homeMe;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        // TODO: 2016/3/5
        for(int i =0;i<3;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.list_item_common_text,null);
            TextView textView = ButterKnife.findById(view, R.id.list_item_common_text_tv);
            textView.setText("通知"+i);
            mNoticeList.addView(view);
        }
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


    @OnClick({R.id.home_order, R.id.home_cust,R.id.home_product, R.id.home_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_order:
                readyGo(OrderActivity.class);
                break;
            case R.id.home_cust:
                readyGo(CustActivity.class);
                break;
            case R.id.home_product:
                readyGo(ProductActivity.class);
                break;
            case R.id.home_me:
                readyGo(MeActivity.class);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - DOUBLE_CLICK_TIME > 2000){
                showToast(getString(R.string.double_click_exit));
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            }else{
                getBaseApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
