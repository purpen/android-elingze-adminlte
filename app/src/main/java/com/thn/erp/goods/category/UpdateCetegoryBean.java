package com.thn.erp.goods.category;

/**
 * Created by Stephen on 2018/4/11 17:54
 * Email: 895745843@qq.com
 */

public class UpdateCetegoryBean {
    private String name;
    private String description;
    private Number sort_order;

    public UpdateCetegoryBean(String name, String description, Number sort_order) {
        this.name = name;
        this.description = description;
        this.sort_order = sort_order;
    }

}
