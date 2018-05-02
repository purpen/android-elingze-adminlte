package com.thn.erp.overview;

import java.util.List;

public class SlidesData {

    /**
     * data : {"slides":[{"description":"","image":"https://kg.erp.taihuoniao.com/20180502/2407FoBTlmYqM_-0GC_nTvPmReYVz0t7.png","link":"118130979158","rid":11,"sort_order":0,"status":true,"title":"小程序商家管理","type":2}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        public List<SlidesBean> slides;

        public static class SlidesBean {
            /**
             * description :
             * image : https://kg.erp.taihuoniao.com/20180502/2407FoBTlmYqM_-0GC_nTvPmReYVz0t7.png
             * link : 118130979158
             * rid : 11
             * sort_order : 0
             * status : true
             * title : 小程序商家管理
             * type : 2
             */

            public String description;
            public String image;
            public String link;
            public int rid;
            public int sort_order;
            public boolean status;
            public String title;
            public int type;
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
