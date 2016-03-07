package jjs.com.simple.interactor.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.CustListEntity;
import jjs.com.simple.bean.ResponseCustListEntity;
import jjs.com.simple.interactor.CustListInteractor;
import jjs.com.simple.listeners.BaseMultiLoadedListener;
import jjs.com.simple.utils.VolleyHelper;

/**
 * Created by JJS on 2016/3/3.
 */
public class CustListInteractorImpl implements CustListInteractor {

    private BaseMultiLoadedListener<ResponseCustListEntity> loadedListener;
    public  CustListInteractorImpl(BaseMultiLoadedListener<ResponseCustListEntity> loadedListener ){
        this.loadedListener = loadedListener;
    }


    @Override
    public void getCommonListData(String requestTag, final int event_tag, String name, String phone, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiConstants.Urls.BAIDU_IMAGES_URLS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseCustListEntity responseCustListEntity = new ResponseCustListEntity();
                List<CustListEntity> custListEntities = new ArrayList<CustListEntity>();
                for (int i = 0;i<20;i++){
                    custListEntities.add(new CustListEntity());
                }
                responseCustListEntity.setCusts(custListEntities);
                loadedListener.onSuccess(event_tag, responseCustListEntity);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onException(error.getMessage());
            }
        }
        );
        stringRequest.setShouldCache(true);
        stringRequest.setTag(requestTag);

        VolleyHelper.getInstance().getRequestQueue().add(stringRequest);
    }
}
