package com.thn.erp.sale.bean;

import java.util.List;

/**
 * Created by lilin on 2018/3/26.
 */

public class ProvinceCityRestrict {

    /**
     * data : [{"name":"北京","oid":1,"pid":0,"sort_by":1,"status":true},{"name":"上海","oid":2,"pid":0,"sort_by":1,"status":true},{"name":"天津","oid":3,"pid":0,"sort_by":1,"status":true},{"name":"重庆","oid":4,"pid":0,"sort_by":1,"status":true},{"name":"河北","oid":5,"pid":0,"sort_by":1,"status":true},{"name":"山西","oid":6,"pid":0,"sort_by":1,"status":true},{"name":"河南","oid":7,"pid":0,"sort_by":1,"status":true},{"name":"辽宁","oid":8,"pid":0,"sort_by":1,"status":true},{"name":"吉林","oid":9,"pid":0,"sort_by":1,"status":true},{"name":"黑龙江","oid":10,"pid":0,"sort_by":1,"status":true},{"name":"内蒙古","oid":11,"pid":0,"sort_by":1,"status":true},{"name":"江苏","oid":12,"pid":0,"sort_by":1,"status":true},{"name":"山东","oid":13,"pid":0,"sort_by":1,"status":true},{"name":"安徽","oid":14,"pid":0,"sort_by":1,"status":true},{"name":"浙江","oid":15,"pid":0,"sort_by":1,"status":true},{"name":"福建","oid":16,"pid":0,"sort_by":1,"status":true},{"name":"湖北","oid":17,"pid":0,"sort_by":1,"status":true},{"name":"湖南","oid":18,"pid":0,"sort_by":1,"status":true},{"name":"广东","oid":19,"pid":0,"sort_by":1,"status":true},{"name":"广西","oid":20,"pid":0,"sort_by":1,"status":true},{"name":"江西","oid":21,"pid":0,"sort_by":1,"status":true},{"name":"四川","oid":22,"pid":0,"sort_by":1,"status":true},{"name":"海南","oid":23,"pid":0,"sort_by":1,"status":true},{"name":"贵州","oid":24,"pid":0,"sort_by":1,"status":true},{"name":"云南","oid":25,"pid":0,"sort_by":1,"status":true},{"name":"西藏","oid":26,"pid":0,"sort_by":1,"status":true},{"name":"陕西","oid":27,"pid":0,"sort_by":1,"status":true},{"name":"甘肃","oid":28,"pid":0,"sort_by":1,"status":true},{"name":"青海","oid":29,"pid":0,"sort_by":1,"status":true},{"name":"宁夏","oid":30,"pid":0,"sort_by":1,"status":true},{"name":"新疆","oid":31,"pid":0,"sort_by":1,"status":true},{"name":"台湾","oid":32,"pid":0,"sort_by":1,"status":true},{"name":"钓鱼岛","oid":84,"pid":0,"sort_by":1,"status":true},{"name":"港澳","oid":52993,"pid":0,"sort_by":1,"status":true}]
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;
    public List<DataBean> data;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }

    public static class DataBean {
        /**
         * name : 北京
         * oid : 1
         * pid : 0
         * sort_by : 1
         * status : true
         */

        public String name;
        public int oid;
        public int pid;
        public int sort_by;
        public boolean status;
    }
}
