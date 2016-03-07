package jjs.com.simple.presenter.impl;

import android.content.Context;

import jjs.com.simple.bean.UserEntity;
import jjs.com.simple.interactor.LoginInteractor;
import jjs.com.simple.interactor.impl.LoginInteractorImpl;
import jjs.com.simple.listeners.BaseSingleLoadedListener;
import jjs.com.simple.presenter.LoginPresenter;
import jjs.com.simple.utils.SharedPreferencesUtils;
import jjs.com.simple.view.LoginView;

/**
 * Created by JJS on 2016/2/28.
 */
public class LoginPresenterImpl  implements LoginPresenter ,BaseSingleLoadedListener<UserEntity>{

    private Context mContext;
    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        if (null == loginView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mLoginView = loginView;
        mLoginInteractor = new LoginInteractorImpl(this) ;
    }


    @Override
    public void onSuccess(UserEntity data) {
        loginSucces(data);
    }

    @Override
    public void onError(String msg) {
        mLoginView.loginFail(msg);
    }

    @Override
    public void onException(String msg) {
        mLoginView.loginFail(msg);
    }

    @Override
    public void loginSucces(UserEntity userEntity) {
        mLoginView.navigateToMainPage();
    }


    @Override
    public void login(String username, String password, boolean rememberPwd) {
        //TODO 登录业务逻辑
        if(rememberPwd){
            SharedPreferencesUtils.setParam(mContext,"username",username);
            SharedPreferencesUtils.setParam(mContext,"pwd",password);
            SharedPreferencesUtils.setParam(mContext,"remeberPwd",true);
        }
        mLoginInteractor.login("login_tag", username,password);
    }

    @Override
    public void initialized() {
        String username  = (String) SharedPreferencesUtils.getParam(mContext, "username", "");
        String pwd = (String) SharedPreferencesUtils.getParam(mContext, "pwd", "");
        boolean remeberPwd = (boolean) SharedPreferencesUtils.getParam(mContext, "remeberPwd", false);
        mLoginView.initialized(username,pwd,remeberPwd);
    }
}
