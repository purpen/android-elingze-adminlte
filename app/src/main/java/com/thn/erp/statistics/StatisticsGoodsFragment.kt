package com.thn.erp.statistics

import android.os.Handler
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.thn.erp.R
import com.thn.erp.base.BaseFragment
import com.thn.erp.statistics.bean.BrandProportionBean
import com.thn.erp.statistics.bean.GoodsProportionBean
import kotlinx.android.synthetic.main.fragment_goods_statistics.*

class StatisticsGoodsFragment : BaseFragment() {
    override fun getLayout(): Int = R.layout.fragment_goods_statistics

    override fun initView() {
        setUpGoodsClassifyPieChart()
        setUpBrandClassifyPieChart()
    }

    private fun setUpGoodsClassifyPieChart() {
        goodsClassifyPieChart.setUsePercentValues(true)
        goodsClassifyPieChart.getDescription().setEnabled(false)
        goodsClassifyPieChart.setExtraOffsets(10f, 15f, 10f, 15f)
        goodsClassifyPieChart.setDragDecelerationFrictionCoef(0.95f)
        goodsClassifyPieChart.setDrawHoleEnabled(true)
        goodsClassifyPieChart.setHoleRadius(70f)
        goodsClassifyPieChart.setTransparentCircleRadius(70f)
        goodsClassifyPieChart.setEntryLabelTextSize(12f)
        goodsClassifyPieChart.setEntryLabelColor(resources.getColor(R.color.color_666))
        goodsClassifyPieChart.setDrawCenterText(false)
        goodsClassifyPieChart.setRotationEnabled(true)
        goodsClassifyPieChart.setRotationAngle(45f)
        goodsClassifyPieChart.setHighlightPerTapEnabled(true)
        val l = goodsClassifyPieChart.getLegend()
        l.setEnabled(false)
    }

    private fun setUpBrandClassifyPieChart() {
        brandClassifyPieChart.setUsePercentValues(true)
        brandClassifyPieChart.getDescription().setEnabled(false)
        brandClassifyPieChart.setExtraOffsets(10f, 15f, 15f, 10f)
        brandClassifyPieChart.setDragDecelerationFrictionCoef(0.95f)
        brandClassifyPieChart.setDrawHoleEnabled(true)
        brandClassifyPieChart.setHoleRadius(70f)
        brandClassifyPieChart.setTransparentCircleRadius(70f)
        brandClassifyPieChart.setEntryLabelTextSize(12f)
        brandClassifyPieChart.setEntryLabelColor(resources.getColor(R.color.color_666))
        brandClassifyPieChart.setDrawCenterText(false)
        brandClassifyPieChart.setRotationEnabled(true)
        brandClassifyPieChart.setRotationAngle(45f)
        brandClassifyPieChart.setHighlightPerTapEnabled(true)
        val l = brandClassifyPieChart.getLegend()
        l.setEnabled(false)
    }

    override fun requestNet() {
        Handler().postDelayed({
            var list = ArrayList<GoodsProportionBean.GoodsProportion>()
            for (i in 1..3) {
                var goodsProportion = GoodsProportionBean.GoodsProportion("name" + i, i * 2, 0.333f);
                list.add(goodsProportion)
            }
            setGoodsClassifyPieData(list)
            var list1=ArrayList<BrandProportionBean.BrandProportion>()
            for (i in 1..3) {
                var goodsProportion = BrandProportionBean.BrandProportion("name" + i, i * 2, 0.333f);
                list1.add(goodsProportion)
            }
            setBrandClassifyPieData(list1)
        }, 1000)
    }

    /**
     * 设置商品分类饼图
     */
    private fun setGoodsClassifyPieData(datas: List<GoodsProportionBean.GoodsProportion>?) {
        if (datas == null) return
        val entries = ArrayList<PieEntry>()
        for (data in datas) {
            entries.add(PieEntry(data.proportion, String.format("%s%s个", data.name, data.quantity)))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 0f
        dataSet.selectionShift = 3f
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.color_27AE59))
        colors.add(resources.getColor(R.color.color_ffe583))
        colors.add(resources.getColor(R.color.color_ff686a))
        dataSet.setColors(colors)

        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLineColor = resources.getColor(R.color.color_666)
        dataSet.valueLinePart1Length = 0.3f
        dataSet.valueLinePart2Length = 0.5f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        if (goodsClassifyPieChart.getData() != null && goodsClassifyPieChart.getData().getDataSetCount() > 0) {
            val pieDataSet = goodsClassifyPieChart.getData().getDataSetByIndex(0) as PieDataSet
            pieDataSet.values = entries
            goodsClassifyPieChart.getData().notifyDataChanged()
            goodsClassifyPieChart.notifyDataSetChanged()
        } else {
            val data = PieData(dataSet)
            data.setValueFormatter(object : IValueFormatter {
                override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?): String {
                    val iv = value.toInt() * 100
                    val dv = (value * 100).toInt()
                    return if (dv - iv == 0) {
                        String.format("%d%%", (iv * 0.01).toInt())
                    } else {
                        String.format("%.2f%%", value)
                    }
                }
            })
            data.setValueTextSize(12f)
            data.setValueTextColor(resources.getColor(R.color.color_222))
            goodsClassifyPieChart.setData(data)
        }
        goodsClassifyPieChart.invalidate()
    }

    /**
     * 设置品牌分类饼图
     */
    private fun setBrandClassifyPieData(datas: List<BrandProportionBean.BrandProportion>?) {
        if (datas == null) return
        val entries = ArrayList<PieEntry>()
        for (data in datas) {
            entries.add(PieEntry(data.proportion, String.format("%s%s个", data.name, data.quantity)))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 0f
        dataSet.selectionShift = 3f
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.color_27AE59))
        colors.add(resources.getColor(R.color.color_ffe583))
        colors.add(resources.getColor(R.color.color_ff686a))
        dataSet.setColors(colors)

        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLineColor = resources.getColor(R.color.color_666)
        dataSet.valueLinePart1Length = 0.3f
        dataSet.valueLinePart2Length = 0.5f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        if (brandClassifyPieChart.getData() != null && brandClassifyPieChart.getData().getDataSetCount() > 0) {
            val pieDataSet = brandClassifyPieChart.getData().getDataSetByIndex(0) as PieDataSet
            pieDataSet.values = entries
            brandClassifyPieChart.getData().notifyDataChanged()
            brandClassifyPieChart.notifyDataSetChanged()
        } else {
            val data = PieData(dataSet)
            data.setValueFormatter(object : IValueFormatter {
                override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?): String {
                    val iv = value.toInt() * 100
                    val dv = (value * 100).toInt()
                    return if (dv - iv == 0) {
                        String.format("%d%%", (iv * 0.01).toInt())
                    } else {
                        String.format("%.2f%%", value)
                    }
                }
            })
            data.setValueTextSize(12f)
            data.setValueTextColor(resources.getColor(R.color.color_222))
            brandClassifyPieChart.setData(data)
        }
        brandClassifyPieChart.invalidate()
    }


}