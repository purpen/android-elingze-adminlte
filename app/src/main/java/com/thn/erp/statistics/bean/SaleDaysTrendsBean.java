package com.thn.erp.statistics.bean;

public class SaleDaysTrendsBean {

    /**
     * data : {"today":{"sale_amount":1000,"order_quantity":100,"day_yoy":-0.12},"seven_days":{"sale_amount":1000,"order_quantity":100,"week_yoy":0.12},"thirty_days":{"sale_amount":1000,"order_quantity":100,"month_yoy":0.12}}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * today : {"sale_amount":1000,"order_quantity":100,"day_yoy":-0.12}
         * seven_days : {"sale_amount":1000,"order_quantity":100,"week_yoy":0.12}
         * thirty_days : {"sale_amount":1000,"order_quantity":100,"month_yoy":0.12}
         */

        public TodayBean today;
        public SevenDaysBean seven_days;
        public ThirtyDaysBean thirty_days;

        public static class TodayBean {
            /**
             * sale_amount : 1000
             * order_quantity : 100
             * day_yoy : -0.12
             */

            public double sale_amount;
            public int order_quantity;
            public double day_yoy;
        }

        public static class SevenDaysBean {
            /**
             * sale_amount : 1000
             * order_quantity : 100
             * week_yoy : 0.12
             */

            public double sale_amount;
            public int order_quantity;
            public double week_yoy;
        }

        public static class ThirtyDaysBean {
            /**
             * sale_amount : 1000
             * order_quantity : 100
             * month_yoy : 0.12
             */

            public double sale_amount;
            public int order_quantity;
            public double month_yoy;
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
