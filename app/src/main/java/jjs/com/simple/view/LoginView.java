package jjs.com.simple.view;

/**
 * Created by JJS on 2016/2/28.
 */
public interface LoginView {

    void loginFail(String msg);

    void navigateToMainPage();

    void initialized(String username, String pwd, boolean remeberPwd);
}
