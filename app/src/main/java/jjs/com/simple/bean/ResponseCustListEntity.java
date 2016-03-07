package jjs.com.simple.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJS on 2016/3/3.
 */
public class ResponseCustListEntity {

    List<CustListEntity> custs = new ArrayList<>();

    int total = 60;

    public List<CustListEntity> getCusts() {
        return custs;
    }

    public void setCusts(List<CustListEntity> custs) {
        this.custs = custs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
