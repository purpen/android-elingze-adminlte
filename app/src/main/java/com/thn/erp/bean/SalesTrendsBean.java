package com.thn.erp.bean;

import java.util.List;

/**
 * Created by lilin on 2017/7/5.
 */

public class SalesTrendsBean {


    /**
     * data : {"sale_amount_data":[{"sale_amount":88,"time":1526659200},{"sale_amount":23,"time":1526745600},{"sale_amount":98,"time":1526832000},{"sale_amount":111,"time":1526918400}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<SaleAmountDataBean> sale_amount_data;

        public static class SaleAmountDataBean {
            /**
             * sale_amount : 88
             * time : 1526659200
             */

            public int sale_amount;
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
