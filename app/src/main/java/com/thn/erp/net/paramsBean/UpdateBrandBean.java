package com.thn.erp.net.paramsBean;

/**
 * Created by Stephen on 2018/4/10 15:45
 * Email: 895745843@qq.com
 */

public class UpdateBrandBean {
    private String name;
    private String features;
    private String description;
    private Integer supplier_id;
    private Boolean is_recommended;
    private Number sort_order;

    public UpdateBrandBean(String name, String features, String description, Integer supplier_id, Boolean is_recommended, Number sort_order) {
        this.name = name;
        this.features = features;
        this.description = description;
        this.supplier_id = supplier_id;
        this.is_recommended = is_recommended;
        this.sort_order = sort_order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Boolean getIs_recommended() {
        return is_recommended;
    }

    public void setIs_recommended(Boolean is_recommended) {
        this.is_recommended = is_recommended;
    }

    public Number getSort_order() {
        return sort_order;
    }

    public void setSort_order(Number sort_order) {
        this.sort_order = sort_order;
    }
}
