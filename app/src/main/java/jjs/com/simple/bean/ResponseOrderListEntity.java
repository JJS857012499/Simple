package jjs.com.simple.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJS on 2016/3/1.
 */
public class ResponseOrderListEntity {

    List<OrderListEntity> orders = new ArrayList<>();
    int total = 60;

    public List<OrderListEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderListEntity> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
