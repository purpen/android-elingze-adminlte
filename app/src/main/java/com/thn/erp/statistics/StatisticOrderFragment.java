package com.thn.erp.statistics;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.thn.basemodule.tools.DateUtil;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.basemodule.tools.LogUtil;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.statistics.bean.OrderTrendsBean;
import com.thn.erp.statistics.bean.SaleDaysTrendsBean;
import com.thn.erp.view.CustomScrollView;
import com.thn.erp.view.MyMarkerView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lilin on 2017/6/16.
 * 统计订单
 */

public class StatisticOrderFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private static final float LINE_WIDTH = 1f;
    private static final float TEXT_SIZE = 9f;
    private static final float VALUE_TEXT_SIZE = 9f;
    @BindView(R.id.tvMoneyToday)
    TextView tvMoneyToday;
    @BindView(R.id.tvRateToday)
    TextView tvRateToday;

    @BindView(R.id.tvMoney7Day)
    TextView tvMoney7Day;
    @BindView(R.id.tvRate7Day)
    TextView tvRate7Day;

    @BindView(R.id.tvMoney30Days)
    TextView tvMoney30Days;
    @BindView(R.id.tvRate30Day)
    TextView tvRate30Day;


    private int color;
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.currentSaleAccount)
    TextView currentSaleAccount;
    @BindView(R.id.currentSaleDate)
    TextView currentSaleDate;
    private List<OrderTrendsBean.DataBean.OrderQuantityDataBean> datas;
    @BindView(R.id.saleAmountChart)
    LineChart saleAmountChart;
    @BindView(R.id.tvSaleAmountPeriod)
    TextView tvSaleAmountPeriod;

    @BindView(R.id.ivTriangle)
    ImageView ivTriangle;

    @BindView(R.id.emptyView)
    LinearLayout emptyView;

    private boolean isLoadSaleAmount = true;
    private boolean isLoadSaleOrder = true;
    private int currentClickedId = -1;
    private ArrayList<Boolean> statesArr;//没有数据放入false，有数据放入true


    @Override
    protected int getLayout() {
        return R.layout.fragment_statistics_order;
    }

    @Override
    protected void initView() {
        color = getResources().getColor(R.color.color_27AE59);
        statesArr = new ArrayList<>();
        setUpSaleAmountChart();
    }

    @OnClick({R.id.llSaleAmountPeriod})
    void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.llSaleAmountPeriod:
                currentClickedId = R.id.llSaleAmountPeriod;
                getCalendarTime();
                break;
            default:
                break;
        }
    }

    /**
     * 选取日期
     */
    private void getCalendarTime() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                StatisticOrderFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setYearRange(2010, now.get(Calendar.YEAR));
        dpd.setMaxDate(now);
        dpd.setFirstDayOfWeek(Calendar.MONDAY, Calendar.MONDAY);
        dpd.setAccentColor(color);
        dpd.show(activity.getFragmentManager(), DatePickerDialog.class.getSimpleName());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        if (currentClickedId < 0) return;
        String start_time = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
        String end_time = String.format("%04d-%02d-%02d", yearEnd, monthOfYearEnd + 1, dayOfMonthEnd);
        String dateStr = String.format("%s 至 %s", start_time, end_time);
        switch (currentClickedId) {
            case R.id.llSaleAmountPeriod:
                isLoadSaleOrder = false;
                isLoadSaleAmount = true;
                tvSaleAmountPeriod.setText(dateStr);
                getOrderAmountData(start_time, end_time);
                break;
            default:
                break;
        }

    }

    @Override
    protected void installListener() {
        saleAmountChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                currentSaleAccount.setText(String.format("订单量：%s", e.getY()));
                if (null == datas || datas.isEmpty()) return;
                int time = datas.get((int) e.getX()).time;
                currentSaleDate.setText(DateUtil.getDateByTimestamp(time));
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * 初始化销售额图表
     */
    private void setUpSaleAmountChart() {

        saleAmountChart.setDragEnabled(true);
        //x轴
        XAxis xAxis = saleAmountChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        saleAmountChart.setScaleEnabled(false);
        saleAmountChart.getLegend().setEnabled(false);
        saleAmountChart.getDescription().setEnabled(false);
        int axisColor = getResources().getColor(R.color.color_E5E5E5);
        xAxis.setAxisLineColor(axisColor);
        xAxis.setAxisLineWidth(LINE_WIDTH);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(TEXT_SIZE);
//        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1);
        //sxAxis.setXOffset(10);
//        xAxis.setCenterAxisLabels(true);
        //每页显示7个标签
        xAxis.setLabelCount(7, true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if (datas == null) return null;
                LogUtil.e("value==" + value);
                if (datas.size() > 0) {
                    if (value >= datas.size()) return "";
                    int v = (int) value;
                    OrderTrendsBean.DataBean.OrderQuantityDataBean bean = datas.get(v);
                    return DateUtil.getDateByTimestamp(bean.time).substring(5);
                } else {
                    return "";
                }
            }
        });


        MyMarkerView mv = new MyMarkerView(activity, R.layout.custom_marker_view);
        mv.setChartView(saleAmountChart);
        saleAmountChart.setMarker(mv);


        //设置y轴值
        YAxis leftAxis = saleAmountChart.getAxisLeft();
        leftAxis.setAxisLineColor(axisColor);
        leftAxis.setAxisLineWidth(LINE_WIDTH);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setSpaceBottom(0);
        leftAxis.setZeroLineColor(axisColor);
        YAxis yAxisRight = saleAmountChart.getAxisRight();
        yAxisRight.setEnabled(false);
//        totalSaleLineChart.animateX(2500);
//        totalSaleLineChart.invalidate();
    }


    @Override
    protected void requestNet() {
        String end_time = DateUtil.getSpecifiedDayBefore(new Date(), 1);
        String start_time = DateUtil.getSpecifiedMonthBefore(end_time, 1);
        LogUtil.e("end_time=" + end_time + ";start_time=" + start_time);
        tvSaleAmountPeriod.setText(String.format("%s 至 %s", start_time, end_time));
        isLoadSaleAmount = true;
        isLoadSaleOrder = true;
        getSaleTrendData();
        getOrderAmountData(start_time, end_time);
    }

    /**
     * 获取今天，过去7天，过去30天销售数据
     */
    private void getSaleTrendData() {
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.SALES_DAYS_TRENDS,params,new HttpRequestCallback() {

            @Override
            public void onSuccess(String json) {
                SaleDaysTrendsBean saleDaysTrendsBean = JsonUtil.fromJson(json, SaleDaysTrendsBean.class);
                if (saleDaysTrendsBean.status.code == Constants.SUCCESS) {
                    setSaleTrendsData(saleDaysTrendsBean.data);
                } else {
                    ToastUtil.showError(saleDaysTrendsBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    /**
     * 7days,30days数据
     *
     * @param data
     */
    private void setSaleTrendsData(SaleDaysTrendsBean.DataBean data) {
        if (data==null) return;
        tvMoneyToday.setText(String.format(Locale.CHINESE,"￥%,.2f",data.today.sale_amount));
        tvMoney7Day.setText(String.format(Locale.CHINESE,"￥%,.2f",data.seven_days.sale_amount));
        tvMoney30Days.setText(String.format(Locale.CHINESE,"￥%,.2f",data.thirty_days.sale_amount));

        tvRateToday.setText(String.format(Locale.CHINESE,"日同比 %.0f%%",data.today.day_yoy));
        tvRate7Day.setText(String.format(Locale.CHINESE,"七日同比 %.0f%%",data.seven_days.week_yoy));
        tvRate30Day.setText(String.format(Locale.CHINESE,"月同比 %.0f%%",data.thirty_days.month_yoy));

        if (data.today.day_yoy>=0){
            tvRateToday.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_statistic_rise, 0);
        }else {
            tvRateToday.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_statistic_decline, 0);
        }


        if (data.seven_days.week_yoy>=0){
            tvRate7Day.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_statistic_rise, 0);
        }else {
            tvRate7Day.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_statistic_decline, 0);
        }

        if (data.thirty_days.month_yoy>=0){
            tvRate30Day.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_statistic_rise, 0);
        }else {
            tvRate30Day.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_statistic_decline, 0);
        }
    }

    /**
     * 获得销售额表数据
     */
    private void getOrderAmountData(String start_time, String end_time) {
        if (TextUtils.isEmpty(start_time)) return;
        if (TextUtils.isEmpty(end_time)) return;
        HashMap<String, String> params = ClientParamsAPI.getOrderTrendsRequestParams(start_time, end_time);
        HttpRequest.sendRequest(HttpRequest.POST, URL.STATISTIC_ORDER_TRENDS, params, new HttpRequestCallback() {

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                OrderTrendsBean salesTrendsBean = JsonUtil.fromJson(json, OrderTrendsBean.class);
                if (salesTrendsBean.status.code == Constants.SUCCESS) {
                    if (null != datas) datas.clear();
                    datas = salesTrendsBean.data.order_quantity_data;
                    //测试数据
                    for (int i = 0; i < 30; i++) {
                        OrderTrendsBean.DataBean.OrderQuantityDataBean bean = new OrderTrendsBean.DataBean.OrderQuantityDataBean();
                        bean.order_quantity = (int) (Math.random() * 1000);
                        bean.time = 1526745600 + 24 * 3600 * i;
                        datas.add(bean);
                    }
                    saleAmountChart.zoom(30 / 7, 1f, 0, 0);
                    if (isLoadSaleAmount) setOrderAmountChartData(datas);
                } else {
                    ToastUtil.showError(salesTrendsBean.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtil.showError(R.string.network_err);
            }
        });
    }


    /**
     * 设置linechart填充样式
     * @param set
     */
//    private void setLineChartFillStyle(LineDataSet set) {
//        set.setDrawFilled(true);
//        if (Utils.getSDKInt() >= 18) {
//            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.shape_linechart_fill_color);
//            set.setFillDrawable(drawable);
//        } else {
//            set.setFillColor(getResources().getColor(R.color.color_66fee178));
//        }
//    }


    /**
     * 设置订单量表数据
     *
     * @param datas
     */
    private void setOrderAmountChartData(List<OrderTrendsBean.DataBean.OrderQuantityDataBean> datas) {
        if (null == datas) {
            statesArr.add(false);
            return;
        }
        if (datas.size() == 0) {
            statesArr.add(false);
        } else {
            statesArr.add(true);
        }
        checkHaveNotData();
        ArrayList<Entry> values = new ArrayList<>();
        int len = datas.size();
        for (int i = 0; i < len; i++) {
            OrderTrendsBean.DataBean.OrderQuantityDataBean dataBean = datas.get(i);
            int quantity = dataBean.order_quantity;
            if (i == 0) {
                currentSaleAccount.setText(String.format("订单量：%s", quantity));
                currentSaleDate.setText(DateUtil.getDateByTimestamp(dataBean.time));
            }
            values.add(new Entry(i, quantity));
        }

        LineDataSet set;

        if (saleAmountChart.getData() != null &&
                saleAmountChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) saleAmountChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            saleAmountChart.getData().notifyDataChanged();
            saleAmountChart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, "");
            set.setDrawIcons(false);
            set.setCubicIntensity(0.2f);
            set.setMode(LineDataSet.Mode.LINEAR);
            set.setColor(color);
//            set.setCircleColor(color);
            set.setLineWidth(LINE_WIDTH);
//            set.setCircleRadius(3f);
            set.setDrawCircles(false);
            set.setDrawCircleHole(false);
            set.setValueTextSize(VALUE_TEXT_SIZE);
            set.setValueTextColor(color);
            set.setDrawValues(false);
            set.setHighLightColor(color);
            set.enableDashedHighlightLine(15f, 5f, 0f);
//            setLineChartFillStyle(set);
            set.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return String.valueOf((int) value);
                }
            });
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            LineData data = new LineData(dataSets);
            saleAmountChart.setData(data);
        }
        saleAmountChart.invalidate();
    }

    private void checkHaveNotData() {
        LogUtil.e(statesArr.size() + "===" + statesArr);
        if (statesArr.size() < 4) return;
        if (statesArr.contains(true)) {
            emptyView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }
}
