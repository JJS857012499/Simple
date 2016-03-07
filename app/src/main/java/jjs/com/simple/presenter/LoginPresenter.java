package jjs.com.simple.presenter;

import jjs.com.simple.bean.UserEntity;

/**
 * Created by JJS on 2016/2/28.
 */
public interface LoginPresenter  extends  Presenter{

    void loginSucces(UserEntity userEntity);

    void login(String username, String password,boolean rememberPwd);

}
