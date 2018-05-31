package com.thn.erp.statistics.bean;

import java.util.List;

public class OrderTrendsBean {

    /**
     * data : {"order_quantity_data":[{"order_quantity":888,"time":1526659200},{"order_quantity":321,"time":1526745600},{"order_quantity":988,"time":1526832000},{"order_quantity":1111,"time":1526918400}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<OrderQuantityDataBean> order_quantity_data;

        public static class OrderQuantityDataBean {
            /**
             * order_quantity : 888
             * time : 1526659200
             */

            public int order_quantity;
            public int time;
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
