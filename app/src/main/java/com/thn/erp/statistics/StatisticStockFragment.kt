package com.thn.erp.statistics

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.thn.erp.R
import com.thn.erp.base.BaseFragment
import com.thn.erp.statistics.adapter.WareHouseAdapter
import com.thn.erp.statistics.bean.WareHouseBean
import kotlinx.android.synthetic.main.fragment_stock_statistics.*

class StatisticStockFragment : BaseFragment() {
    private lateinit var statesArr: ArrayList<Boolean>
    private lateinit var adapter: WareHouseAdapter
    override fun getLayout(): Int = R.layout.fragment_stock_statistics

    override fun initView() {
        statesArr = ArrayList()
        setUpWareHousePieChart()
        initRecyclerView()
    }

    private fun setUpWareHousePieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
        pieChart.setExtraOffsets(10f, 15f, 10f, 15f)
        pieChart.setDragDecelerationFrictionCoef(0.95f)
        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleRadius(70f)
        pieChart.setTransparentCircleRadius(70f)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(resources.getColor(R.color.color_666))
        pieChart.setDrawCenterText(false)
        pieChart.setRotationEnabled(false)
        pieChart.setRotationAngle(45f)
        pieChart.setHighlightPerTapEnabled(true)
        val l = pieChart.getLegend()
        l.setEnabled(false)
    }

    override fun requestNet() {
        Handler().postDelayed({
            var list0 = ArrayList<WareHouseBean.WareHouseProportion>()
            for (i in 1..3) {
                var goodsProportion = WareHouseBean.WareHouseProportion("name" + i, i * 20f, "address" + i, i * 2, 0.333f);
                list0.add(goodsProportion)
            }
            setWareHousePieData(list0)

            refreshList(list0)
        }, 1000)
    }

    /**
     * 设置仓库饼图数据
     */
    private fun setWareHousePieData(datas: List<WareHouseBean.WareHouseProportion>?) {
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
        if (pieChart.getData() != null && pieChart.getData().getDataSetCount() > 0) {
            val pieDataSet = pieChart.getData().getDataSetByIndex(0) as PieDataSet
            pieDataSet.values = entries
            pieChart.getData().notifyDataChanged()
            pieChart.notifyDataSetChanged()
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
            pieChart.setData(data)
        }
        pieChart.invalidate()
    }


    /**
     * 初始化表单
     */
    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(getActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager

        adapter = WareHouseAdapter(R.layout.item_statistics_warehouse)
        adapter.addHeaderView(getHeaderView())
        recyclerView.adapter = adapter
    }

    /**
     * 获得头布局
     * @return
     */
    private fun getHeaderView(): View {
        val headerView = LayoutInflater.from(context).inflate(R.layout.item_statistics_warehouse, null)
        headerView.setBackgroundColor(resources.getColor(R.color.color_E6E6E6))
        headerView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.dp30)))
        return headerView
    }

    /**
     * 刷新表格数据
     * @param list
     */
    private fun refreshList(list: List<WareHouseBean.WareHouseProportion>?) {
        if (null == list) return
        if (list.isEmpty()) {
            statesArr.add(false)
        } else {
            statesArr.add(true)
        }
        checkHaveNotData()
        adapter.setNewData(list)
    }

    private fun checkHaveNotData() {
        if (statesArr.size < 4) return
        if (statesArr.contains(true)) {
            emptyView.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
        } else {
            emptyView.visibility = View.VISIBLE
            scrollView.visibility = View.GONE
        }
    }

}