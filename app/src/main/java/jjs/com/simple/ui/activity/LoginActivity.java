package jjs.com.simple.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import jjs.com.simple.R;
import jjs.com.simple.presenter.LoginPresenter;
import jjs.com.simple.presenter.impl.LoginPresenterImpl;
import jjs.com.simple.ui.activity.base.BaseActivity;
import jjs.com.simple.view.LoginView;

/**
 * Created by JJS on 2016/2/28.
 */
public class LoginActivity extends BaseActivity implements LoginView {


    @InjectView(R.id.login_userName)
    EditText mUserName;
    @InjectView(R.id.login_passwd)
    EditText mPasswd;
    @InjectView(R.id.login_remeberPwd)
    CheckBox mRemeberPwd;
    @InjectView(R.id.login_commit)
    Button mCommit;

    private LoginPresenter loginPresenter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
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

        loginPresenter = new LoginPresenterImpl(this, this);
        loginPresenter.initialized();

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



    @OnClick(R.id.login_commit)
    public void onClick() {
        String username = mUserName.getText().toString().trim();
        String pwd = mPasswd.getText().toString().trim();
        boolean remberpwd = mRemeberPwd.isChecked();
        if(CommonUtils.isEmpty(username)){
            showToast("用户名不能为空");
            return ;
        }
        if(CommonUtils.isEmpty(pwd)){
            showToast("密码不能为空");
            return ;
        }

        loginPresenter.login(username, pwd, remberpwd);
    }


    @Override
    public void loginFail(String msg) {
        showToast(msg);
    }

    @Override
    public void navigateToMainPage() {
        readyGoThenKill(HomeActivity.class);
    }

    @Override
    public void initialized(String username, String pwd, boolean remeberPwd) {
        mUserName.setText(username);
        mPasswd.setText(pwd);
        mRemeberPwd.setChecked(remeberPwd);
    }
}
