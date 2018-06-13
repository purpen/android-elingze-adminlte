package com.thn.erp.statistics.adapter
import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.erp.R
import com.thn.erp.statistics.bean.CustomerGradeBean
import java.util.*

class CustomerAreaAdapter: BaseQuickAdapter<CustomerGradeBean, BaseViewHolder> {

    constructor(@LayoutRes resId:Int):super(resId)

    override fun convert(helper: BaseViewHolder, item: CustomerGradeBean) {
        helper.setText(R.id.column0,""+helper.adapterPosition)
        helper.setText(R.id.column1,item.province)
        helper.setText(R.id.column2,"${item.quantity}")
        helper.setText(R.id.column3,String.format(Locale.CHINESE,"%,.2f",item.amount))
        helper.setText(R.id.column4,String.format(Locale.CHINESE,"%.2f%%",item.proportion*100f))
    }
}