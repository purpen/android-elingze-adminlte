package com.thn.erp.goods.beans;

import java.util.List;

public class GoodsDetailData {

    /**
     * data : {"content":[{"content":"湿度理工哈个阿里戈中国结","type":"text"},{"content":"http://xxx/_uploads/photos/180202/ccab9ae0ab96840.jpg","type":"image"},{"content":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","type":"image"},{"content":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","type":"image"},{"content":"最具推荐的奇石文化","type":"text"}],"images":[{"created_at":1517503809,"filename":"炮兵系列-RCL-G100数据线800x800-1.jpg","filepath":"180202/f51932c571ba21f.jpg","id":12,"view_url":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg"},{"created_at":1517503810,"filename":"炮兵系列-RCL-G100数据线800x800-6.jpg","filepath":"180202/a6ca6a209d29c8d.jpg","id":17,"view_url":"http://xxx/_uploads/photos/180202/a6ca6a209d29c8d.jpg"}],"tags":"设计,骑士，文化"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * content : [{"content":"湿度理工哈个阿里戈中国结","type":"text"},{"content":"http://xxx/_uploads/photos/180202/ccab9ae0ab96840.jpg","type":"image"},{"content":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","type":"image"},{"content":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","type":"image"},{"content":"最具推荐的奇石文化","type":"text"}]
         * images : [{"created_at":1517503809,"filename":"炮兵系列-RCL-G100数据线800x800-1.jpg","filepath":"180202/f51932c571ba21f.jpg","id":12,"view_url":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg"},{"created_at":1517503810,"filename":"炮兵系列-RCL-G100数据线800x800-6.jpg","filepath":"180202/a6ca6a209d29c8d.jpg","id":17,"view_url":"http://xxx/_uploads/photos/180202/a6ca6a209d29c8d.jpg"}]
         * tags : 设计,骑士，文化
         */

        public String tags;
        public List<ContentBean> content;
        public List<ImagesBean> images;

        public static class ContentBean {
            /**
             * content : 湿度理工哈个阿里戈中国结
             * type : text
             */

            public String content;
            public String type;
        }

        public static class ImagesBean {
            /**
             * created_at : 1517503809
             * filename : 炮兵系列-RCL-G100数据线800x800-1.jpg
             * filepath : 180202/f51932c571ba21f.jpg
             * id : 12
             * view_url : http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg
             */

            public int created_at;
            public String filename;
            public String filepath;
            public int id;
            public String view_url;
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
