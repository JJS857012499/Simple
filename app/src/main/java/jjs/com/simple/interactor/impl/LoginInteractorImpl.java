package jjs.com.simple.interactor.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.obsessive.library.utils.TLog;
import com.google.gson.reflect.TypeToken;

import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.ResponseSingleListentity;
import jjs.com.simple.bean.UserEntity;
import jjs.com.simple.interactor.LoginInteractor;
import jjs.com.simple.listeners.BaseSingleLoadedListener;
import jjs.com.simple.utils.VolleyHelper;

/**
 * Created by JJS on 2016/2/28.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private BaseSingleLoadedListener<UserEntity> loginListener;

    public LoginInteractorImpl(BaseSingleLoadedListener<UserEntity> loginListener ) {
        this.loginListener = loginListener;
    }

    @Override
    public void login(final String requestTag,String username, String password) {
        TLog.i(requestTag,"login");
//        GsonRequest<UserEntity> gsonRequest = new GsonRequest<UserEntity>(
//                ApiConstants.Urls.BAIDU_IMAGES_URLS,
//                null,
//                new TypeToken<ResponseSingleListentity>() {
//                }.getType(),
//                new Response.Listener<UserEntity>() {
//                    @Override
//                    public void onResponse(UserEntity response) {
//                        loginListener.onSuccess(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loginListener.onException(error.getMessage());
//                    }
//                }
//        );
//
//        gsonRequest.setShouldCache(false);
//        gsonRequest.setTag(requestTag);
//
//        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiConstants.Urls.BAIDU_IMAGES_URLS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TLog.i(requestTag, response);
                        loginListener.onSuccess(new UserEntity());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TLog.i(requestTag, error.getMessage());
                        loginListener.onError(error.getMessage());
                    }
                });
        stringRequest.setShouldCache(true);
        stringRequest.setTag(requestTag);
        VolleyHelper.getInstance().getRequestQueue().add(stringRequest);

    }
}
