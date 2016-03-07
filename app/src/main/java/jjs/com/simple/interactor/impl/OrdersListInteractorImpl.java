package jjs.com.simple.interactor.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.OrderListEntity;
import jjs.com.simple.bean.ResponseOrderListEntity;
import jjs.com.simple.interactor.CommonListInteractor;
import jjs.com.simple.listeners.BaseMultiLoadedListener;
import jjs.com.simple.utils.UriHelper;
import jjs.com.simple.utils.VolleyHelper;

/**
 * Created by JJS on 2016/3/2.
 */
public class OrdersListInteractorImpl implements CommonListInteractor {

    private BaseMultiLoadedListener<ResponseOrderListEntity> loadedListener;
    public OrdersListInteractorImpl( BaseMultiLoadedListener<ResponseOrderListEntity> loadedListener ){
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(String requestTag, final int event_tag, String keywords, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiConstants.Urls.BAIDU_IMAGES_URLS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseOrderListEntity responseOrderListEntity = new ResponseOrderListEntity();
                List<OrderListEntity> orderListEntities = new ArrayList<OrderListEntity>();
                for (int i = 0;i<20;i++){
                    orderListEntities.add(new OrderListEntity());
                }
                responseOrderListEntity.setOrders(orderListEntities);
                loadedListener.onSuccess(event_tag, responseOrderListEntity);
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
