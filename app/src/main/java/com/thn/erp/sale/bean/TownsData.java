package com.thn.erp.sale.bean;

import java.util.List;

/**
 * Created by lilin on 2018/3/26.
 */

public class TownsData {


    /**
     * data : [{"name":"城区","oid":47837,"pid":183,"sort_by":1,"status":true},{"name":"南石门镇","oid":47839,"pid":183,"sort_by":1,"status":true},{"name":"晏家屯镇","oid":47840,"pid":183,"sort_by":1,"status":true},{"name":"西黄村镇","oid":47841,"pid":183,"sort_by":1,"status":true},{"name":"将军墓镇","oid":47842,"pid":183,"sort_by":1,"status":true},{"name":"东汪镇","oid":47843,"pid":183,"sort_by":1,"status":true},{"name":"羊范镇","oid":47844,"pid":183,"sort_by":1,"status":true},{"name":"王快镇","oid":47845,"pid":183,"sort_by":1,"status":true},{"name":"皇寺镇","oid":47846,"pid":183,"sort_by":1,"status":true},{"name":"祝村镇","oid":47847,"pid":183,"sort_by":1,"status":true},{"name":"浆水镇","oid":47848,"pid":183,"sort_by":1,"status":true},{"name":"路罗镇","oid":47849,"pid":183,"sort_by":1,"status":true},{"name":"会宁镇","oid":47850,"pid":183,"sort_by":1,"status":true},{"name":"北小庄乡","oid":47851,"pid":183,"sort_by":1,"status":true},{"name":"太子井乡","oid":47852,"pid":183,"sort_by":1,"status":true},{"name":"城计头乡","oid":47853,"pid":183,"sort_by":1,"status":true},{"name":"冀家村乡","oid":47854,"pid":183,"sort_by":1,"status":true},{"name":"龙泉寺乡","oid":47855,"pid":183,"sort_by":1,"status":true},{"name":"宋家庄乡","oid":47856,"pid":183,"sort_by":1,"status":true},{"name":"白岸乡","oid":47857,"pid":183,"sort_by":1,"status":true}]
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
         * name : 城区
         * oid : 47837
         * pid : 183
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
