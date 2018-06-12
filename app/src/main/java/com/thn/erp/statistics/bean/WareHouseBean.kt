package com.thn.erp.statistics.bean

data class WareHouseBean(var success: Boolean, var status: StatusBean, var data: DataBean) {
    data class StatusBean(var code: Int, var message: String)
    data class DataBean(var list: List<WareHouseProportion>)
    data class WareHouseProportion(var name: String,var cost:Float,var address:String ,var quantity: Int, var proportion: Float)
}