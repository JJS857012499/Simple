package jjs.com.simple.interactor.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.bean.ProductListEntity;
import jjs.com.simple.bean.ResponseOrderListEntity;
import jjs.com.simple.bean.ResponseProductListEntity;
import jjs.com.simple.interactor.CommonListInteractor;
import jjs.com.simple.listeners.BaseMultiLoadedListener;
import jjs.com.simple.utils.VolleyHelper;

/**
 * Created by JJS on 2016/3/4.
 */
public class ProductListInteractorImpl implements CommonListInteractor {

    private BaseMultiLoadedListener<ResponseProductListEntity> loadedListener;

    public ProductListInteractorImpl( BaseMultiLoadedListener<ResponseProductListEntity> loadedListener){
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(String requestTag, final int event_tag, String keywords, int page) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ApiConstants.Urls.BAIDU_IMAGES_URLS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseProductListEntity responseProductListEntity = new ResponseProductListEntity();
                List<ProductListEntity> productListEntitys = new ArrayList<ProductListEntity>();
                for (int i = 0;i<20;i++){
                    productListEntitys.add(new ProductListEntity());
                }
                responseProductListEntity.setProducts(productListEntitys);
                loadedListener.onSuccess(event_tag, responseProductListEntity);
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
