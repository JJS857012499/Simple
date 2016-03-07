package jjs.com.simple.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJS on 2016/3/4.
 */
public class ResponseProductListEntity {

    List<ProductListEntity> products = new ArrayList<ProductListEntity>();
    int total = 60;

    public List<ProductListEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductListEntity> products) {
        this.products = products;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
