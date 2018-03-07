package com.thn.erp.statistics;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.view.CustomScrollView;
import com.thn.erp.view.ListViewForScrollView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lilin on 2017/6/16.
 * 销售额
 */

public class SaleAmountFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener{
    private static final float LINE_WIDTH=1f;
    private static final float TEXT_SIZE=6f;
    private static final float VALUE_TEXT_SIZE=9f;
    private int color;
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.currentSaleAccount)
    TextView currentSaleAccount;
    @BindView(R.id.currentSaleDate)
    TextView currentSaleDate;
    @BindView(R.id.currentOrders)
    TextView currentOrders;
    @BindView(R.id.currentOrderDate)
    TextView currentOrderDate;
    private List<SalesTrendsBean.DataBean> datas;
    @BindView(R.id.saleAmountChart)
    LineChart saleAmountChart;
    @BindView(R.id.listViewForScrollView)
    ListViewForScrollView listViewForScrollView;
    @BindView(R.id.tvSaleAmountPeriod)
    TextView tvSaleAmountPeriod;
    @BindView(R.id.ivTriangle)
    ImageView ivTriangle;

    @BindView(R.id.currentTime)
    TextView currentTime;

    @BindView(R.id.tvSaleRank)
    TextView tvSaleRank;

    @BindView(R.id.emptyView)
    LinearLayout emptyView;

    private boolean isLoadSaleAmount = true;
    private boolean isLoadSaleOrder = true;
    private int currentClickedId = -1;
    private ArrayList<Boolean> statesArr;//没有数据放入false，有数据放入true
    @Override
    protected int getLayout() {
        return R.layout.fragment_sale_amount;
    }

    @Override
    protected void initView() {
        color=getResources().getColor(R.color.color_ff5a5f);
        statesArr=new ArrayList<>();
        setUpSaleAmountChart();
        setUpSaleOrderChart();
        setUpSaleOrderIn24Chart();
        listViewForScrollView.setFocusable(false);
        View view = View.inflate(activity, R.layout.header_sale_top100, null);
        listViewForScrollView.addHeaderView(view);
    }

    @OnClick({R.id.llSaleAmountPeriod,R.id.llSaleOrderPeriod,R.id.llSaleOrder24,R.id.llSaleRank})
    void onViewClicked(View v){
        switch (v.getId()){
            case R.id.llSaleAmountPeriod:
                currentClickedId = R.id.llSaleAmountPeriod;
                getCalendarTime();
                break;
            case R.id.llSaleOrderPeriod:
                currentClickedId = R.id.llSaleOrderPeriod;
                getCalendarTime();
                break;
            case R.id.llSaleOrder24:
                currentClickedId = R.id.llSaleOrder24;
                getCalendarTime();
                break;
            case R.id.llSaleRank:
                currentClickedId = R.id.llSaleRank;
                getCalendarTime();
                break;
            default:
                break;
        }
    }

    private void getCalendarTime() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SaleAmountFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setYearRange(2000,now.get(Calendar.YEAR));
        dpd.setMaxDate(now);
        dpd.setFirstDayOfWeek(Calendar.MONDAY, Calendar.MONDAY);
        dpd.setAccentColor(color);
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        if(currentClickedId<0) return;
        String start_time = String.format("%04d-%02d-%02d",year,monthOfYear+1,dayOfMonth);
        String end_time = String.format("%04d-%02d-%02d",yearEnd,monthOfYearEnd+1,dayOfMonthEnd);
        String dateStr = String.format("%s至%s",start_time,end_time);
        switch (currentClickedId){
            case R.id.llSaleAmountPeriod:
                isLoadSaleOrder=false;
                isLoadSaleAmount=true;
                tvSaleAmountPeriod.setText(dateStr);
                getSaleAmountData(start_time,end_time);
                break;
            case R.id.llSaleOrderPeriod:
                isLoadSaleOrder = true;
                isLoadSaleAmount=false;
                tvSaleOrderPeriod.setText(dateStr);
                getSaleAmountData(start_time,end_time);
                break;
            case R.id.llSaleOrder24:
                tvSaleOrder24.setText(dateStr);
                getSaleOrdersIn24Data(start_time,end_time);
                break;
            case R.id.llSaleRank:
                tvSaleRank.setText(dateStr);
                getProductsTop100Data(start_time,end_time);
            default:
                break;
        }

    }

    /**
     * 设置24小时内订单表
     */
    private void setUpSaleOrderIn24Chart() {
        XAxis xAxis = ordersIn24Chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ordersIn24Chart.getLegend().setEnabled(false);
        ordersIn24Chart.setScaleEnabled(false);
        ordersIn24Chart.getDescription().setEnabled(false);
        int axisColor = getResources().getColor(R.color.color_222);
        xAxis.setAxisLineColor(axisColor);
        xAxis.setAxisLineWidth(LINE_WIDTH);
        xAxis.setTextSize(TEXT_SIZE);
        xAxis.setDrawGridLines(false);
        MyMarkerView mv = new MyMarkerView(activity,R.layout.custom_marker_view);
        mv.setChartView(ordersIn24Chart);
        ordersIn24Chart.setMarker(mv);
        xAxis.setValueFormatter((float value, AxisBase axis) -> {
            if (null==datas) return "";
            if (datas.size()>0){
                return String.valueOf(in24HoursDatas.get((int) value).time)+"点";
            }else {
                return "";
            }
        });
        //设置是否显示x轴
        xAxis.setEnabled(true);
        YAxis leftAxis = ordersIn24Chart.getAxisLeft();
        leftAxis.setAxisLineColor(axisColor);
        leftAxis.setAxisLineWidth(LINE_WIDTH);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        YAxis yAxisRight = ordersIn24Chart.getAxisRight();
        yAxisRight.setEnabled(false);
    }

    /**
     * 销售订单
     */
    private void setUpSaleOrderChart() {
        XAxis xAxis = orderChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        orderChart.getLegend().setEnabled(false);
        orderChart.setScaleEnabled(false);
        orderChart.getDescription().setEnabled(false);
        orderChart.setPinchZoom(true);
        int axisColor = getResources().getColor(R.color.color_222);
        xAxis.setAxisLineColor(axisColor);
        xAxis.setAxisLineWidth(LINE_WIDTH);
        xAxis.setTextSize(TEXT_SIZE);
        MyMarkerView mv = new MyMarkerView(activity,R.layout.custom_marker_view);
        mv.setChartView(orderChart);
        orderChart.setMarker(mv);
        xAxis.setValueFormatter((float value, AxisBase axis) -> {
            if (null==datas) return "";
            if (datas.size()>0){
                return String.valueOf(datas.get((int) value).time.substring(5));
            }else {
                return "";
            }
        });

        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);
        YAxis leftAxis = orderChart.getAxisLeft();
        leftAxis.setAxisLineColor(axisColor);
        leftAxis.setAxisLineWidth(LINE_WIDTH);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisMinimum(0);
        YAxis yAxisRight = orderChart.getAxisRight();
        yAxisRight.setEnabled(false);
    }

    @Override
    protected void installListener() {
        saleAmountChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                currentSaleAccount.setText(String.format("销售额：%s",e.getY()));
                if (null==datas) return;
                currentSaleDate.setText(datas.get((int)e.getX()).time);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        orderChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                currentOrders.setText(String.format("订单数：%s",(int)e.getY()));
                if (null==datas) return;
                currentOrderDate.setText(datas.get((int)e.getX()).time);
            }

            @Override
            public void onNothingSelected() {
            }
        });

        ordersIn24Chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                current24Orders.setText(String.format("订单数：%s",(int)e.getY()));
                if (null==in24HoursDatas) return;
                currentTime.setText(in24HoursDatas.get((int)e.getX()).time+"点");
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setUpSaleAmountChart() {
        XAxis xAxis = saleAmountChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        saleAmountChart.setScaleEnabled(false);
        saleAmountChart.getLegend().setEnabled(false);
        saleAmountChart.getDescription().setEnabled(false);
        int axisColor = getResources().getColor(R.color.color_222);
        xAxis.setAxisLineColor(axisColor);
        xAxis.setAxisLineWidth(LINE_WIDTH);
        xAxis.setTextSize(TEXT_SIZE);
        xAxis.setValueFormatter((float value, AxisBase axis) -> {
            if (datas==null) return null;
            if (datas.size()>0){
                return String.valueOf(datas.get((int) value).time.substring(5));
            }else {
                return "";
            }
        });
        MyMarkerView mv = new MyMarkerView(activity,R.layout.custom_marker_view);
        mv.setChartView(saleAmountChart); // For bounds control
        saleAmountChart.setMarker(mv); // Set the marker to the chart
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);
        YAxis leftAxis = saleAmountChart.getAxisLeft();
        leftAxis.setAxisLineColor(axisColor);
        leftAxis.setAxisLineWidth(LINE_WIDTH);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawZeroLine(true);
        YAxis yAxisRight = saleAmountChart.getAxisRight();
        yAxisRight.setEnabled(false);
//        totalSaleLineChart.animateX(2500);
//        totalSaleLineChart.invalidate();
    }


    @Override
    protected void requestNet() {
        String end_time = DateUtil.getSpecifiedDayBefore(new Date(),1);
        String start_time=DateUtil.getSpecifiedMonthBefore(end_time,1);
        LogUtil.e("end_time="+end_time+";start_time="+start_time);
        tvSaleAmountPeriod.setText(String.format("%s至%s",start_time,end_time));
        tvSaleOrderPeriod.setText(String.format("%s至%s",start_time,end_time));
        tvSaleOrder24.setText(String.format("%s至%s",start_time,end_time));
        tvSaleRank.setText(String.format("%s至%s",start_time,end_time));
        isLoadSaleAmount=true;
        isLoadSaleOrder=true;
        getSaleAmountData(start_time,end_time);
        getProductsTop100Data(start_time,end_time);
        getSaleOrdersIn24Data(start_time,end_time);
    }

    /**
     * 获取24小时内订单数
     * @param start_time
     * @param end_time
     */
    private void getSaleOrdersIn24Data(String start_time, String end_time) {
        if (TextUtils.isEmpty(start_time)) return;
        if (TextUtils.isEmpty(end_time)) return;
        HashMap<String, String> params = ClientParamsAPI.getSalesOrdersIn24Params(start_time, end_time);
        HttpRequest.sendRequest(HttpRequest.GET, URL.ORDERS_IN_24HOURS, params, new HttpRequestCallback() {

            @Override
            public void onSuccess(String json) {
                OrdersIn24HoursBean  ordersIn24HoursBean= JsonUtil.fromJson(json, OrdersIn24HoursBean.class);
                if (null == ordersIn24HoursBean) return;
                if (ordersIn24HoursBean.meta.status_code==Constants.SUCCESS){
                    if (null!=in24HoursDatas) in24HoursDatas.clear();
                    in24HoursDatas=ordersIn24HoursBean.data;
                    setOrdersIn24HoursData(in24HoursDatas);
                }else {
                    ToastUtils.showError(ordersIn24HoursBean.meta.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
            }
        });
    }



    /**
     * 获取top100产品列表
     * @param start_time
     * @param end_time
     */
    private void getProductsTop100Data(String start_time, String end_time) {
        if (TextUtils.isEmpty(start_time)) return;
        if (TextUtils.isEmpty(end_time)) return;
        HashMap<String, String> params = ClientParamsAPI.getSalesTop100Params(start_time, end_time);
        HttpRequest.sendRequest(HttpRequest.GET, URL.SALES_TOP100, params, new HttpRequestCallback() {

            @Override
            public void onSuccess(String json) {
                SaleTop100Bean saleTop100Bean = JsonUtil.fromJson(json, SaleTop100Bean.class);
                if (null == saleTop100Bean) return;
                if (saleTop100Bean.meta.status_code== Constants.SUCCESS){
                    refreshList(saleTop100Bean.data);
                }else {
                    ToastUtils.showError(saleTop100Bean.meta.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    /**
     * 刷新表格数据
     *
     * @param list
     */
    private void refreshList(List<SaleTop100Bean.DataBean> list) {
        if (null==list) return;
        if (list.size()==0) {
            statesArr.add(false);
        }else {
            statesArr.add(true);
        }
        checkHaveNotData();
        ProductsTop100Adapter adapter = new ProductsTop100Adapter(list, activity);
        listViewForScrollView.setAdapter(adapter);
    }


    /**
     * 获得销售数据
     */
    private void getSaleAmountData(String start_time, String end_time) {
        if (TextUtils.isEmpty(start_time)) return;
        if (TextUtils.isEmpty(end_time)) return;
        HashMap<String, String> params = ClientParamsAPI.getSalesTrendsRequestParams(start_time, end_time);
        HttpRequest.sendRequest(HttpRequest.GET, URL.SALES_TRENDS, params, new HttpRequestCallback() {

            @Override
            public void onSuccess(String json) {
                SalesTrendsBean salesTrendsBean = JsonUtil.fromJson(json, SalesTrendsBean.class);
                if (salesTrendsBean.meta.status_code== Constants.SUCCESS){
                    if (null!=datas) datas.clear();
                    datas = salesTrendsBean.data;
                    if (isLoadSaleAmount) setSaleAmountChartData(datas);
                    if (isLoadSaleOrder) setSaleOrderChartData(datas);
                }else {
                    ToastUtils.showError(salesTrendsBean.meta.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    /**
     * 设置订单数量表数据
     *
     * @param datas
     */
    private void setSaleOrderChartData(List<SalesTrendsBean.DataBean> datas) {
        if (null==datas) {
            statesArr.add(false);
            return;
        }
        if (datas.size()==0){
            statesArr.add(false);
        } else {
            statesArr.add(true);
        }
        checkHaveNotData();
        ArrayList<Entry> values = new ArrayList<>();
        int len = datas.size();

        for (int i = 0; i < len; i++) {
            SalesTrendsBean.DataBean dataBean = datas.get(i);
            Integer counts = Integer.valueOf(dataBean.order_count);
            if (i==0) {
                currentOrders.setText(String.format("订单数：%s",counts));
                currentOrderDate.setText(dataBean.time);
            }
            values.add(new Entry(i,counts));
        }

        LineDataSet set;

        if (orderChart.getData() != null &&
                orderChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) orderChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            orderChart.getData().notifyDataChanged();
            orderChart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, "");
            set.setDrawIcons(false);
            set.setCubicIntensity(0.2f);
            set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set.setColor(color);
//            set.setCircleColor(color);
            set.setLineWidth(LINE_WIDTH);
//            set.setCircleRadius(3f);
            set.setDrawCircles(false);
            set.setDrawCircleHole(false);
            set.setValueTextSize(VALUE_TEXT_SIZE);
            set.setValueTextColor(color);
            set.setDrawValues(false);
            setLineChartFillStyle(set);
            set.setValueFormatter((float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) -> String.valueOf((int) value));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            LineData data = new LineData(dataSets);
            orderChart.setData(data);
        }
        orderChart.invalidate();
    }

    /**
     * 设置linechart填充样式
     * @param set
     */
    private void setLineChartFillStyle(LineDataSet set) {
        set.setDrawFilled(true);
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(activity, R.drawable.shape_linechart_fill_color);
            set.setFillDrawable(drawable);
        } else {
            set.setFillColor(getResources().getColor(R.color.color_66fee178));
        }
    }


    /**
     * 设置销售额表数据
     *
     * @param datas
     */
    private void setSaleAmountChartData(List<SalesTrendsBean.DataBean> datas) {
        if (null==datas) {
            statesArr.add(false);
            return;
        }
        if (datas.size()==0) {
            statesArr.add(false);
        }else {
            statesArr.add(true);
        }
        checkHaveNotData();
        ArrayList<Entry> values = new ArrayList<>();
        int len = datas.size();
        for (int i = 0; i < len; i++) {
            SalesTrendsBean.DataBean dataBean = datas.get(i);
            Float money = Float.valueOf(dataBean.sum_money);
            if (i==0) {
                currentSaleAccount.setText(String.format("销售额：%s",money));
                currentSaleDate.setText(dataBean.time);
            }
            values.add(new Entry(i,money));
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
            set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set.setColor(color);
//            set.setCircleColor(color);
            set.setLineWidth(LINE_WIDTH);
//            set.setCircleRadius(3f);
            set.setDrawCircles(false);
            set.setDrawCircleHole(false);
            set.setValueTextSize(VALUE_TEXT_SIZE);
            set.setValueTextColor(color);
            set.setDrawValues(false);
            setLineChartFillStyle(set);
            set.setValueFormatter((float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) -> String.valueOf((int)value));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            LineData data = new LineData(dataSets);
            saleAmountChart.setData(data);
        }
        saleAmountChart.invalidate();
    }

    /**
     * 设置24小时订单数据
     * @param datas
     */
    private void setOrdersIn24HoursData(List<OrdersIn24HoursBean.DataBean> datas) {
        ArrayList<Entry> values = new ArrayList<>();
        int len = datas.size();
        if (len==0) {
            statesArr.add(false);
        }else {
            statesArr.add(true);
        }
        checkHaveNotData();
        for (int i = 0; i < len; i++) {
            OrdersIn24HoursBean.DataBean dataBean = datas.get(i);
            Integer counts = Integer.valueOf(dataBean.order_count);
            if (i==0) {
                current24Orders.setText(String.format("订单数：%s",counts));
                currentTime.setText(dataBean.time);
            }
            values.add(new Entry(i,counts));
        }

        LineDataSet set;

        if (ordersIn24Chart.getData() != null &&
                ordersIn24Chart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) ordersIn24Chart.getData().getDataSetByIndex(0);
            set.setValues(values);
            ordersIn24Chart.getData().notifyDataChanged();
            ordersIn24Chart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, "");
            set.setDrawIcons(false);
            set.setCubicIntensity(0.2f);
            set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set.setColor(color);
            set.setDrawCircles(false);
//            set.setCircleColor(color);
            set.setLineWidth(LINE_WIDTH);
//            set.setCircleRadius(1f);
            set.setDrawCircleHole(false);
            set.setValueTextSize(VALUE_TEXT_SIZE);
            set.setValueTextColor(color);
            set.setDrawValues(false);
            setLineChartFillStyle(set);
            set.setValueFormatter((float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) -> String.valueOf((int) value));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            LineData data = new LineData(dataSets);
            ordersIn24Chart.setData(data);
        }
        ordersIn24Chart.invalidate();
    }

    private void checkHaveNotData(){
        LogUtil.e(statesArr.size()+"==="+statesArr);
        if (statesArr.size()<4) return;
        if (statesArr.contains(true)){
            emptyView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }
}
