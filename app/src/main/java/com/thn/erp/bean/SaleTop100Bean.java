package com.thn.erp.bean;

import java.util.List;

/**
 * Created by lilin on 2017/7/6.
 */

public class SaleTop100Bean {


    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<SaleLogStatisticsBean> sale_log_statistics;

        public static class SaleLogStatisticsBean {

            public String name;
            public double proportion;
            public int quantity;
            public int sale_amount;
        }
    }

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
