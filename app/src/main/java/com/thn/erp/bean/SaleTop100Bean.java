package com.thn.erp.bean;

import java.util.List;

/**
 * Created by lilin on 2017/7/6.
 */

public class SaleTop100Bean {

    /**
     * data : [{"sum_money":"18034419.50","sales_quantity":"3024","sku_number":"","sku_id":"1","sku_name":"","proportion":"99.03"},{"sum_money":"65043.00","sales_quantity":"657","sku_number":"117063086023","sku_id":"112","sku_name":"--众筹款","proportion":"0.36"},{"sum_money":"59211.00","sales_quantity":"459","sku_number":"117063007428","sku_id":"116","sku_name":"--众筹款","proportion":"0.33"},{"sum_money":"35200.00","sales_quantity":"4","sku_number":"117063064739","sku_id":"114","sku_name":"--众筹款","proportion":"0.19"},{"sum_money":"14579.00","sales_quantity":"61","sku_number":"117070351669","sku_id":"119","sku_name":"--众筹款","proportion":"0.08"},{"sum_money":"2580.00","sales_quantity":"86","sku_number":"117063039706","sku_id":"113","sku_name":"--众筹款","proportion":"0.01"},{"sum_money":"900.00","sales_quantity":"15","sku_number":"117063057252","sku_id":"115","sku_name":"--众筹款","proportion":"0.00"},{"sum_money":"0.28","sales_quantity":"14","sku_number":"116121435522","sku_id":"16","sku_name":"这是第一个产品吗蓝色","proportion":"0.00"},{"sum_money":"0.20","sales_quantity":"20","sku_number":"116110436749","sku_id":"77","sku_name":"VOJO面条数据线 苹果充电线 长线橘色","proportion":"0.00"},{"sum_money":"0.20","sales_quantity":"10","sku_number":"116110405857","sku_id":"78","sku_name":"VOJO面条数据线 苹果充电线 长线灰色","proportion":"0.00"},{"sum_money":"0.15","sales_quantity":"5","sku_number":"116110424036","sku_id":"79","sku_name":"VOJO面条数据线 苹果充电线 长线绿色","proportion":"0.00"},{"sum_money":"0.14","sales_quantity":"14","sku_number":"116121494677","sku_id":"17","sku_name":"这是第一个产品吗黑色","proportion":"0.00"},{"sum_money":"0.12","sales_quantity":"6","sku_number":"116110492753","sku_id":"28","sku_name":"华米手环黑色","proportion":"0.00"},{"sum_money":"0.08","sales_quantity":"8","sku_number":"116110436229","sku_id":"80","sku_name":"VOJO面条数据线 苹果充电线 长线黄色","proportion":"0.00"},{"sum_money":"0.03","sales_quantity":"3","sku_number":"116121478479","sku_id":"19","sku_name":"a1螺丝刀a1螺丝刀a1螺丝刀红色","proportion":"0.00"},{"sum_money":"0.03","sales_quantity":"3","sku_number":"116110419069","sku_id":"29","sku_name":"华米手环白色","proportion":"0.00"}]
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
         * sum_money : 18034419.50
         * sales_quantity : 3024
         * sku_number :
         * sku_id : 1
         * sku_name :
         * proportion : 99.03
         */

        public String sum_money;
        public String sales_quantity;
        public String sku_number;
        public String sku_id;
        public String sku_name;
        public String proportion;
    }
}
