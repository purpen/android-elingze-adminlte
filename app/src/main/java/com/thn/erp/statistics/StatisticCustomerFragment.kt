package com.thn.erp.statistics

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.borax12.materialdaterangepicker.date.DatePickerDialog
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.thn.erp.R
import com.thn.erp.base.BaseFragment
import com.thn.erp.statistics.adapter.CustomerAreaAdapter
import com.thn.erp.statistics.bean.CustomerGradeBean
import kotlinx.android.synthetic.main.fragment_statistics_customer.*
import java.util.*

class StatisticCustomerFragment : BaseFragment(),DatePickerDialog.OnDateSetListener {
    private lateinit var statesArr: ArrayList<Boolean>
    private lateinit var adapter: CustomerAreaAdapter
    override fun getLayout(): Int = R.layout.fragment_statistics_customer

    override fun initView() {
        statesArr = ArrayList()
        setUpCustomerAreaChart()
        setUpCustomerGradePieChart()
        initRecyclerView()
    }

    /**
     * 初始化客户地域分布柱状图
     */
    private fun setUpCustomerAreaChart() {
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        val description = Description()
        description.isEnabled = false
        barChart.description = description
        barChart.setPinchZoom(false)
        barChart.setMaxVisibleValueCount(60)
        barChart.setScaleEnabled(false)
        barChart.isDragEnabled = true
        barChart.isHighlightPerDragEnabled = true
        barChart.setDrawGridBackground(false)
        barChart.axisRight.isEnabled = false
        barChart.zoom(34/7f, 1f, 0f, 0f)
        //X轴 样式
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.granularity = 1f//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setCenterAxisLabels(true)//字体下面的标签 显示在每个直方图的中间
        xAxis.setLabelCount(7, true)//一个界面显示10个Lable。那么这里要设置11个
        xAxis.textSize = 10f
        xAxis.xOffset = 10f


        //Y轴样式
        val leftAxis = barChart.axisLeft
        leftAxis.labelCount = 10
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.spaceBottom = 0f

        leftAxis.setDrawZeroLine(false)
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)//背景线
        leftAxis.axisLineColor = resources.getColor(R.color.colorPrimaryDark)

        val l = barChart.legend
        l.isEnabled = false
    }


    /**
     * 初始化客户等级饼图
     */
    private fun setUpCustomerGradePieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(10f, 15f, 10f, 15f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = 70f
        pieChart.transparentCircleRadius = 70f
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(resources.getColor(R.color.color_666))
        pieChart.setDrawCenterText(false)
        pieChart.isRotationEnabled = false
        pieChart.rotationAngle = 45f
        pieChart.isHighlightPerTapEnabled = true
        val l = pieChart.legend
        l.isEnabled = false
    }

    override fun installListener() {
        llContainer.setOnClickListener { view-> when{
            view.id==R.id.llContainer -> getCalendarTime()
            else-> {}
        } }
    }

    /**
     * 选取日期
     */
    private fun getCalendarTime() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.setYearRange(2010, now.get(Calendar.YEAR))
        dpd.maxDate = now
        dpd.setFirstDayOfWeek(Calendar.MONDAY, Calendar.MONDAY)
        dpd.accentColor = resources.getColor(R.color.color_27AE59)
        dpd.show(activity.fragmentManager, DatePickerDialog::class.java.simpleName)
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int, yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int) {
        val start_time = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
        val end_time = String.format("%04d-%02d-%02d", yearEnd, monthOfYearEnd + 1, dayOfMonthEnd)
        tvTime.text = String.format("%s 至 %s", start_time, end_time)

        requestNet()
    }

    override fun requestNet() {
        Handler().postDelayed({
            var list0 = ArrayList<CustomerGradeBean>()
            for (i in 1..34) {
                var goodsProportion = CustomerGradeBean("grade$i", "province$i", i * 20, i * 100f, 0.333f);
                list0.add(goodsProportion)
            }

            setCustomerGradeBarData(list0)

            setCustomerGradePieData(list0)

            refreshList(list0)
        }, 1000)
    }


    inner class MyXAxisValueFormatter(private val mValues: List<String>) : IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            var x = value.toInt()
            if (x < 0)
                x = 0
            if (x >= mValues.size)
                x = mValues.size - 1
            return mValues[x]
        }
    }

    private fun setCustomerGradeBarData(list: List<CustomerGradeBean>) {


        var names = ArrayList<String>()


        val entries = ArrayList<BarEntry>()

        var index = 0
        for (data in list) {
            ++index
            names.add(data.province)
            entries.add(BarEntry(index.toFloat(), data.quantity.toFloat()))
        }

        barChart.xAxis.valueFormatter = MyXAxisValueFormatter(names)

        val set1: BarDataSet
        if (barChart.data != null && barChart.data.dataSetCount > 0) {
            set1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = entries
            barChart.data.notifyDataChanged()
        } else {
            set1 = BarDataSet(entries, "")
            set1.setColors(ColorTemplate.rgb("#52B778"))
            val dataSets = java.util.ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.25f
            barChart.data = data
        }
        barChart.notifyDataSetChanged()
    }


    /**
     * 设置仓库饼图数据
     */
    private fun setCustomerGradePieData(datas: List<CustomerGradeBean>?) {
        if (datas == null) return
        val entries = ArrayList<PieEntry>()
        for (data in datas) {
            entries.add(PieEntry(data.proportion, "${data.grade}${data.quantity}位"))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 0f
        dataSet.selectionShift = 3f
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.color_27AE59))
        colors.add(resources.getColor(R.color.color_ffe583))
        colors.add(resources.getColor(R.color.color_ff686a))
        dataSet.colors = colors

        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLineColor = resources.getColor(R.color.color_666)
        dataSet.valueLinePart1Length = 0.3f
        dataSet.valueLinePart2Length = 0.5f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        if (pieChart.data != null && pieChart.data.dataSetCount > 0) {
            val pieDataSet = pieChart.data.getDataSetByIndex(0) as PieDataSet
            pieDataSet.values = entries
            pieChart.data.notifyDataChanged()
            pieChart.notifyDataSetChanged()
        } else {
            val data = PieData(dataSet)
            data.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
                val iv = value.toInt() * 100
                val dv = (value * 100).toInt()
                if (dv - iv == 0) {
                    String.format("%d%%", (iv * 0.01).toInt())
                } else {
                    String.format("%.2f%%", value)
                }
            }
            data.setValueTextSize(12f)
            data.setValueTextColor(resources.getColor(R.color.color_222))
            pieChart.data = data
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

        adapter = CustomerAreaAdapter(R.layout.item_statistics_customer)
        adapter.addHeaderView(getHeaderView())
        recyclerView.adapter = adapter
    }

    /**
     * 获得头布局
     * @return
     */
    private fun getHeaderView(): View {
        val headerView = LayoutInflater.from(context).inflate(R.layout.item_statistics_customer, null)
        headerView.background = resources.getDrawable(R.drawable.border_radius5_e6e6e6_bg_fafafa)
        headerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.dp30))
        return headerView
    }

    /**
     * 刷新表格数据
     * @param list
     */
    private fun refreshList(list: List<CustomerGradeBean>?) {
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