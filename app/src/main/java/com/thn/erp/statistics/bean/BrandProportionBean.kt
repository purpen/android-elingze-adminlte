package com.thn.erp.statistics.bean

data class BrandProportionBean(var success: Boolean, var status: StatusBean, var data: DataBean) {
    data class StatusBean(var code: Int, var message: String)
    data class DataBean(var list: List<BrandProportion>)
    data class BrandProportion(var name: String, var quantity: Int, var proportion: Float)
}