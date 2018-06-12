package com.thn.erp.statistics.bean

data class GoodsProportionBean(var success: Boolean, var status: StatusBean, var data: DataBean) {
    data class StatusBean(var code: Int, var message: String)
    data class DataBean(var list: List<GoodsProportion>)
    data class GoodsProportion(var name: String, var quantity: Int, var proportion: Float)
}