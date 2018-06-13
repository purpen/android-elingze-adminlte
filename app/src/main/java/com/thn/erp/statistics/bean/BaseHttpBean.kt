package com.thn.erp.statistics.bean

data class BaseHttpBean(var success: Boolean, var status: StatusBean, var data: DataBean<Any>) {
    data class StatusBean(var code: Int, var message: String)
    data class DataBean<T>(var list: T)
}