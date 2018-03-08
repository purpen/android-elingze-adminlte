package com.thn.erp.bean;

import java.util.List;

/**
 * Created by lilin on 2017/7/5.
 */

public class SalesTrendsBean {

    /**
     * data : [{"order_count":"1","sum_money":"0.00","time":"2016-12-14"},{"order_count":"3","sum_money":"0.07","time":"2016-12-16"},{"order_count":"1","sum_money":"0.03","time":"2016-12-20"},{"order_count":"9","sum_money":"0.21","time":"2016-12-21"},{"order_count":"8","sum_money":"3011.06","time":"2016-12-22"},{"order_count":"9","sum_money":"0.10","time":"2016-12-28"},{"order_count":"1","sum_money":"0.01","time":"2017-01-03"},{"order_count":"1","sum_money":"0.02","time":"2017-01-04"}]
     * meta : {"message":"Success.","status_code":200}
     */

    public MetaBean meta;
    public List<DataBean> data;

    public static class MetaBean {
        /**
         * message : Success.
         * status_code : 200
         */

        public String message;
        public int status_code;
    }

    public static class DataBean {
        /**
         * order_count : 1
         * sum_money : 0.00
         * time : 2016-12-14
         */

        public String order_count;
        public String sum_money;
        public String time;
    }
}
