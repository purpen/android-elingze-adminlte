package com.thn.erp.statistics.adapter
import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.thn.erp.R
import com.thn.erp.statistics.bean.WareHouseBean
import java.util.*

class WareHouseAdapter: BaseQuickAdapter<WareHouseBean.WareHouseProportion, BaseViewHolder> {

    constructor(@LayoutRes resId:Int):super(resId)

    override fun convert(helper: BaseViewHolder, item: WareHouseBean.WareHouseProportion) {
        helper.setText(R.id.column0,""+0)
        helper.setText(R.id.column1,item.name)
        helper.setText(R.id.column2,item.address)
        helper.setText(R.id.column3,String.format(Locale.CHINESE,"%,d",item.quantity))
        helper.setText(R.id.column4,String.format(Locale.CHINESE,"%,.2f",item.cost))
        helper.setText(R.id.column5,String.format(Locale.CHINESE,"%.2f%%",100*(item.proportion)))

    }
}