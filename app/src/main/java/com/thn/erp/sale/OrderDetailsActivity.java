package com.thn.erp.sale;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.basemodule.tools.GlideUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.OrderData;
import com.thn.erp.sale.bean.OrderDetailData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.CustomItemLayout;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListenerConfirmBack;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.textView_sumary)
    TextView textViewSumary;
    @BindView(R.id.linearLayout_sumary)
    LinearLayout linearLayoutSumary;
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.rlAddress)
    RelativeLayout rlAddress;
    @BindView(R.id.itemFreightCompany)
    CustomItemLayout itemFreightCompany;
    @BindView(R.id.itemFreightNum)
    CustomItemLayout itemFreightNum;
    @BindView(R.id.itemPayInfo)
    CustomItemLayout itemPayInfo;
    @BindView(R.id.freight)
    CustomItemLayout freight;
    @BindView(R.id.sumPrice)
    CustomItemLayout sumPrice;
    @BindView(R.id.reducedPrice)
    CustomItemLayout reducedPrice;
    @BindView(R.id.realPay)
    CustomItemLayout realPay;
    @BindView(R.id.tvCallService)
    TextView tvCallService;
    @BindView(R.id.tvCancelOrder)
    TextView tvCancelOrder;
    @BindView(R.id.tvPayOrder)
    TextView tvPayOrder;
    @BindView(R.id.rlBottom)
    RelativeLayout rlBottom;
    @BindView(R.id.llGoods)
    LinearLayout llGoods;

    private String mOptFragmentFlag;//跳回碎片列表时，用该值选中跳来之前的那个碎片
    private RelativeLayout mLogisticsCompanyLayout;//物流公司
    private RelativeLayout mLogisticsNumberLayout;//物流单号
    private TextView mLogisticsNumber, mLogisticsCompany;
    private THNWaittingDialog dialog;
    private TextView mCounty;
    private TextView mTown;
    private OrderData.DataBean.OrdersBean ordersBean;
    private RelativeLayout rlContainer;

    private void toShopOrderListActivity() {
//        Intent in = new Intent(OrderDetailsActivity.this, ShopOrderListActivity.class);
//        in.putExtra("optFragmentFlag", mOptFragmentFlag);
//        OrderDetailsActivity.this.startActivity(in);
//        OrderDetailsActivity.this.finish();
    }

    @Override
    protected void getIntentData() {
        ordersBean = getIntent().getParcelableExtra(OrderDetailsActivity.class.getSimpleName());
//        mOptFragmentFlag = getIntent().getStringExtra("optFragmentFlag");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    protected void initView() {
        customHeadView.setCenterTxtShow("订单详情");
        dialog = new THNWaittingDialog(this);
//        mBottomLayout.setOnClickListener(this);
//        mCall.setOnClickListener(this);
//        mRightButton.setOnClickListener(this);
//        mLeftButton.setOnClickListener(this);
    }


    protected void requestNet() {
        HashMap<String, String> params = ClientParamsAPI.getOrderDetailParams();
        String url = URL.BASE_URL + "orders/" + ordersBean.rid;
        HttpRequest.sendRequest(HttpRequest.GET, url, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                OrderDetailData orderDetailData = JsonUtil.fromJson(json, OrderDetailData.class);
                if (orderDetailData.success == true) {
                    updateUI(orderDetailData.data);
                } else {
                    ToastUtils.showError(orderDetailData.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    /**
     * @param data
     */
    private void updateUI(OrderDetailData.DataBean data) {
        tvName.setText("收货人：" + data.buyer_name);
        tvAddress.setText("收货地址：" + data.buyer_country + data.buyer_province + data.buyer_city + data.buyer_address);
        tvPhone.setText(data.buyer_phone);
        itemFreightNum.setRightMoreImgStyle(false);
        itemFreightNum.setTVStyle(0, "订单号：" + data.rid, R.color.color_222);

        freight.setRightMoreImgStyle(false);
        freight.setTVStyle(0, "运费：", R.color.color_222);
        freight.setTVRightTxt("￥" + data.freight, R.color.color_222);

        sumPrice.setRightMoreImgStyle(false);
        sumPrice.setTVStyle(0, "商品总额：", R.color.color_222);
        sumPrice.setTVRightTxt("￥" + data.total_amount, R.color.color_222);

        reducedPrice.setRightMoreImgStyle(false);
        reducedPrice.setTVStyle(0, "优惠总额：", R.color.color_222);
        reducedPrice.setTVRightTxt("￥" + data.discount_amount, R.color.color_222);

        realPay.setRightMoreImgStyle(false);
        realPay.setTVStyle(0, "实付金额：", R.color.color_222);
        realPay.setTVRightTxt("￥" + data.pay_amount, R.color.color_222);
        llGoods.removeAllViews();
        View view;
        for (OrderData.DataBean.OrdersBean.ItemsBean item : ordersBean.items) {
            view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_goods_adapter, null);
            GoodsItemHolder itemHolder = new GoodsItemHolder(view);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp120)));
            llGoods.addView(view);
            itemHolder.goodsName.setText(item.product_name);
            GlideUtil.loadImage(item.cover, itemHolder.ivCover);
            itemHolder.tvNum.setText("编号：" + item.rid);
            itemHolder.price.setText("￥" + item.sale_price);
            itemHolder.stockNum.setText("数量：" + item.quantity);
        }
    }

    /**
     * 订单包含的商品项
     */
    class GoodsItemHolder {
        @BindView(R.id.ivCover)
        ImageView ivCover;
        @BindView(R.id.goodsName)
        TextView goodsName;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.stockNum)
        TextView stockNum;

        public GoodsItemHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


    private void refreshUI() {
        initOrderStatus();
        setBottomData();
        setOrderGoodsList();
    }

    private void setOrderGoodsList() {
//        int exist_sub_order = orderDetailBean.getExist_sub_order();
//        if (mContainerLayout != null) {
//            mContainerLayout.removeAllViews();
//        }
//        if (exist_sub_order == 1) { //有子订单
//            List<OrderDetailBean.SubOrdersBean> sub_orders = orderDetailBean.getSub_orders();
//
//            View inflate = LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.layout_goods_details_up_sub, null);
//            ((TextView) inflate.findViewById(R.id.textView_suborder_number)).setText(orderDetailBean.getRid());
//            mContainerLayout.addView(inflate);
//
//            for (int m = 0; m < sub_orders.size(); m++) {
//                LinearLayout subOrderView = (LinearLayout) LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.layout_goods_details_order_multi, null);
//                TextView textView1 = (TextView) subOrderView.findViewById(R.id.textView_order_number);
//                TextView textView2 = (TextView) subOrderView.findViewById(R.id.textView_order_status);
//                TextView textViewExpress = (TextView) subOrderView.findViewById(R.id.textView_express);
//                TextView textViewExpressCode = (TextView) subOrderView.findViewById(R.id.textView_express_code);
//                LinearLayout linearLayoutContainerGoods = (LinearLayout) subOrderView.findViewById(R.id.linearLayout_container_subOrder_details);
//                TextView textViewExpressTracking = (TextView) subOrderView.findViewById(R.id.textView_express_tracking);
//                OrderDetailBean.SubOrdersBean subOrdersEntity = sub_orders.get(m);
//
//                textView1.setText(subOrdersEntity.getId());
//                textView2.setVisibility(View.INVISIBLE);
//
//                if (TextUtils.isEmpty(subOrdersEntity.getExpress_company()) || TextUtils.isEmpty(subOrdersEntity.getExpress_no())) {
//                    LinearLayout llExpress = (LinearLayout) subOrderView.findViewById(R.id.linearLayout_express_container);
//                    llExpress.removeAllViews();
//                    TextView textViewHint = new TextView(OrderDetailsActivity.this);
//                    textViewHint.setText("暂时没有物流信息");
//                    llExpress.setGravity(Gravity.CENTER);
//                    llExpress.addView(textViewHint);
//                } else {
//                    textViewExpress.setText(subOrdersEntity.getExpress_company());
//                    textViewExpressCode.setText(subOrdersEntity.getExpress_no());
//                    textViewExpressTracking.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //  查看物流信息
//                            Intent intent = new Intent(OrderDetailsActivity.this, OrderTrackActivity.class);
//                            intent.putExtra("rid", orderDetailBean.getRid());
//                            intent.putExtra("express_no", orderDetailBean.getExpress_no());
//                            intent.putExtra("express_caty", orderDetailBean.getExpress_caty());
//                            intent.putExtra("express_company", orderDetailBean.getExpress_company());
//                            startActivity(intent);
//                        }
//                    });
//                }
//
//                TextView orderNumberTitle = (TextView) subOrderView.findViewById(R.id.textView_order_code_title);
//                orderNumberTitle.setText("子订单号: ");
//
//                final List<OrderDetailBean.SubOrdersBean.ItemsBean> items = subOrdersEntity.getItems();
//                setSubOrderGoodsItem(linearLayoutContainerGoods, items);
//                mContainerLayout.addView(subOrderView);
//            }
//        } else {
//            View subOrderView = View.inflate(OrderDetailsActivity.this, R.layout.layout_goods_details_order_single, null);
//            TextView textView1 = (TextView) subOrderView.findViewById(R.id.textView_order_number);
//            TextView textView2 = (TextView) subOrderView.findViewById(R.id.textView_order_status);
//            TextView textViewExpress = (TextView) subOrderView.findViewById(R.id.textView_express);
//            TextView textViewExpressCode = (TextView) subOrderView.findViewById(R.id.textView_express_code);
//            LinearLayout linearLayoutContainerGoods = (LinearLayout) subOrderView.findViewById(R.id.linearLayout_container_subOrder_details);
//            TextView textViewExpressTracking = (TextView) subOrderView.findViewById(R.id.textView_express_tracking);
//
//            textView1.setText(orderDetailBean.getRid());
//            textView2.setVisibility(View.INVISIBLE);
//
//            if (TextUtils.isEmpty(orderDetailBean.getExpress_company()) || TextUtils.isEmpty(orderDetailBean.getExpress_no())) {
//                LinearLayout llExpress = (LinearLayout) subOrderView.findViewById(R.id.linearLayout_express_container);
//                llExpress.removeAllViews();
//                TextView textViewHint = new TextView(OrderDetailsActivity.this);
//                textViewHint.setText("暂时没有物流信息");
//                llExpress.setGravity(Gravity.CENTER);
//                llExpress.addView(textViewHint);
//            } else {
//                textViewExpress.setText(orderDetailBean.getExpress_company());
//                textViewExpressCode.setText(orderDetailBean.getExpress_no());
//                textViewExpressTracking.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //  查看物流信息
//                        Intent intent = new Intent(OrderDetailsActivity.this, OrderTrackActivity.class);
//                        intent.putExtra("rid", orderDetailBean.getRid());
//                        intent.putExtra("express_no", orderDetailBean.getExpress_no());
//                        intent.putExtra("express_caty", orderDetailBean.getExpress_caty());
//                        intent.putExtra("express_company", orderDetailBean.getExpress_company());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            final List<OrderDetailBean.ItemsBean> itemsEntityList = orderDetailBean.getItems();
//            setGoodsItem(linearLayoutContainerGoods, itemsEntityList);
//            mContainerLayout.addView(subOrderView);
//        }
    }

    private void setBottomData() {
//        if (orderDetailBean.delivery_type == 2) {//自提
//            rlContainer.setVisibility(View.GONE);
//        } else {//线上下单
//            rlContainer.setVisibility(View.VISIBLE);
//        }
//        OrderDetailBean.ExpressInfoBean express_info = orderDetailBean.getExpress_info();
//        if (null == express_info) return;
//        mDeliverMan.setText(String.format("收货人：%s", express_info.getName()));
//        mProvince.setText(express_info.getProvince());
//        mCity.setText(express_info.getCity());
//        mCounty.setText(TextUtils.isEmpty(express_info.getCounty()) ? "" : express_info.getCounty());
//        mTown.setText(TextUtils.isEmpty(express_info.getTown()) ? "" : express_info.getTown());
//        mDetailsAddress.setText(express_info.getAddress());
//        mPhone.setText(express_info.getPhone());
    }


    @OnClick({R.id.tvCallService, R.id.tvPayOrder, R.id.tvCancelOrder})
    void PerformClick(View v) {
        switch (v.getId()) {
            case R.id.tvCallService:
                new DefaultDialog(OrderDetailsActivity.this, "联系客服：400-879-8751", activity.getResources().getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
                    @Override
                    public void clickRight() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:4008798751"));
                        startActivity(intent);
                    }
                });
                break;
        }
    }


    /**
     * 加载订单产品条目
     *
     * @param linearLayoutContainerGoods
     * @param itemsEntityList
     */
//    private void setGoodsItem(LinearLayout linearLayoutContainerGoods, final List<OrderDetailBean.ItemsBean> itemsEntityList) {
//        for (int k = 0; k < itemsEntityList.size(); k++) {
//            View subOrderGoodsItemView = View.inflate(OrderDetailsActivity.this, R.layout.layout_goods_details, null);
//            ImageView imageViewGoods = (ImageView) subOrderGoodsItemView.findViewById(R.id.imageView_goods);
//            TextView textViewGoodsDescription = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_goods_description);
//            TextView textViewSpecification = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_specification);
//            TextView textViewStatus = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_status);
//            TextView textViewPrice = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_price);
//            TextView textView1 = (TextView) subOrderGoodsItemView.findViewById(R.id.textView1);
//            TextView textView2 = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_button_status);
//            GlideUtils.displayImage(itemsEntityList.get(k).getCover_url(), imageViewGoods);
//
//            textViewGoodsDescription.setText(itemsEntityList.get(k).getName());
//            if ("null".equals(itemsEntityList.get(k).getSku_name())) {
//                textViewSpecification.setVisibility(View.INVISIBLE);
//            } else {
//                textViewSpecification.setText(itemsEntityList.get(k).getSku_name() + String.format(" * %s", itemsEntityList.get(k).getQuantity()));
//            }
//
//            String refund_label = itemsEntityList.get(k).getRefund_label();
//            if (TextUtils.isEmpty(refund_label)) {
//                textViewStatus.setVisibility(View.GONE);
//            } else {
//                textViewStatus.setText(refund_label);
//                textViewStatus.setVisibility(View.VISIBLE);
//            }
//            textViewPrice.setText(String.format("¥%s", itemsEntityList.get(k).getSale_price()));
//
//            final int refund_button = itemsEntityList.get(k).getRefund_button();
//            switch (refund_button) { //退款行为：0.隐藏；1.退款；2.退货／款
//                case 0:
//                    textView2.setVisibility(View.INVISIBLE);
////                    textViewStatus.setVisibility(View.GONE);
//                    break;
//                case 1:
//                    textView2.setText("退款");
//                    break;
//                case 2:
//                    textView2.setText("退货");
//                    break;
//            }
//
//            final int y = k;
//            subOrderGoodsItemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(OrderDetailsActivity.this, BuyGoodsDetailsActivity.class);
//                    intent.putExtra("id", String.valueOf(itemsEntityList.get(y).getProduct_id()));
//                    OrderDetailsActivity.this.startActivity(intent);
//                }
//            });
//            textView2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = null;
//                    if (refund_button == 1) {
//                        intent = new Intent(OrderDetailsActivity.this, ChargeBackActivity.class);
//                    } else if (refund_button == 2) {
//                        intent = new Intent(OrderDetailsActivity.this, SalesReturnActivity.class);
//                    }
//                    if (intent != null) {
//                        intent.putExtra(KEY.R_ID, orderDetailBean.getRid());
//                        int sku = itemsEntityList.get(y).getSku();
//                        intent.putExtra(KEY.SKU_ID, String.valueOf(sku));
//                        startActivity(intent);
//                    }
//                }
//            });
//            linearLayoutContainerGoods.addView(subOrderGoodsItemView);
//        }
//    }

    /**
     * 加载子订单产品条目
     */
//    private void setSubOrderGoodsItem(LinearLayout linearLayoutContainerGoods, final List<OrderDetailBean.SubOrdersBean.ItemsBean> items) {
//        for (int k = 0; k < items.size(); k++) {
//            View subOrderGoodsItemView = View.inflate(OrderDetailsActivity.this, R.layout.layout_goods_details, null);
//            ImageView imageViewGoods = (ImageView) subOrderGoodsItemView.findViewById(R.id.imageView_goods);
//            TextView textViewGoodsDescription = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_goods_description);
//            TextView textViewSpecification = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_specification);
//            TextView textViewStatus = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_status);
//            TextView textViewPrice = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_price);
//            TextView textView1 = (TextView) subOrderGoodsItemView.findViewById(R.id.textView1);
//            TextView textView2 = (TextView) subOrderGoodsItemView.findViewById(R.id.textView_button_status);
//            GlideUtils.displayImage(items.get(k).getCover_url(), imageViewGoods);
//
//            textViewGoodsDescription.setText(items.get(k).getName());
//            if ("null".equals(items.get(k).getSku_name())) {
//                textViewSpecification.setVisibility(View.INVISIBLE);
//            } else {
//                textViewSpecification.setText(items.get(k).getSku_name() + String.format(" * %s", items.get(k).getQuantity()));
//            }
//            textViewStatus.setText(items.get(k).getRefund_label());
//            textViewPrice.setText(String.format("¥%s", items.get(k).getSale_price()));
////            textView1.setText(String.format("× %s", items.get(k).getQuantity()));
//            final int refund_button = items.get(k).getRefund_button();
//            switch (refund_button) { //退款行为：0.隐藏；1.退款；2.退货／款
//                case 0:
//                    textView2.setVisibility(View.INVISIBLE);
//                    break;
//                case 1:
//                    textView2.setText("退款");
//                    break;
//                case 2:
//                    textView2.setText("退货");
//                    break;
//            }
//
//            final int y = k;
//            subOrderGoodsItemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(OrderDetailsActivity.this, BuyGoodsDetailsActivity.class);
//                    intent.putExtra("id", String.valueOf(items.get(y).getProduct_id()));
//                    OrderDetailsActivity.this.startActivity(intent);
//                }
//            });
//            textView2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = null;
//                    if (refund_button == 1) {
//                        intent = new Intent(OrderDetailsActivity.this, ChargeBackActivity.class);
//                    } else if (refund_button == 2) {
//                        intent = new Intent(OrderDetailsActivity.this, SalesReturnActivity.class);
//                    }
//                    if (intent != null) {
//                        intent.putExtra(KEY.R_ID, orderDetailBean.getRid());
//                        intent.putExtra(KEY.SKU_ID, String.valueOf(items.get(y).getSku()));
//                        startActivity(intent);
//                    }
//                }
//            });
//            linearLayoutContainerGoods.addView(subOrderGoodsItemView);
//        }
//    }
    private void initOrderStatus() {
//        String rid1 = orderDetailBean.getRid();
//        double pay_money = orderDetailBean.getPay_money();
//        mOrderNum.setText(rid1);
//        mPayMoney.setText("¥" + pay_money);//实付金额
//
//        String payment_method = orderDetailBean.getTrade_site_name();
//        if (TextUtils.isEmpty(payment_method)) {
//            ((RelativeLayout) mPayway.getParent()).setVisibility(View.GONE);
//        } else {
//            mPayway.setText(payment_method);
//        }
//
//        String summary = orderDetailBean.getSummary();
//        if (TextUtils.isEmpty(summary)) {
//            linearLayoutSumary.setVisibility(View.GONE);
//        } else {
//            textViewSumary.setText(summary);
//        }
//
//        mTotalMoney.setText("¥" + orderDetailBean.getTotal_money());//商品总额
//        mFreight.setText("¥" + orderDetailBean.getFreight());//运费
//        if (!TextUtils.isEmpty(orderDetailBean.getExpress_no())) {
//            mLogisticsCompanyLayout.setVisibility(View.VISIBLE);
//            mLogisticsNumberLayout.setVisibility(View.VISIBLE);
//            mLogisticsNumber.setText(orderDetailBean.getExpress_no());
//            mLogisticsCompany.setText(orderDetailBean.getExpress_company());
//        }
//
//        mLogisticsCompanyLayout.setVisibility(View.GONE);
//        mLogisticsNumberLayout.setVisibility(View.GONE);
//
//        final double paymoney = orderDetailBean.getPay_money();
//        final int status = orderDetailBean.getStatus();
//        final String rid = mRid;//订单唯一编号
//        String deleteOrder = "删除订单", payNow = "立即支付", applyForRefund = "申请退款", confirmReceived = "确认收货", publishEvaluate = "发表评价", cancelOrder = "取消订单";
//        switch (status) {
//            case 0://已取消状态
//                mLeftButton.setVisibility(View.INVISIBLE);
//                mRightButton.setText(deleteOrder);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DefaultDialog(OrderDetailsActivity.this, App.getString(R.string.hint_dialog_delete_order_title), App.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getdeleteOrderNetRequestParams(rid);
//                                HttpRequest.post(params, URL.MY_DELETE_ORDER, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//                break;
//            case 1://待付款
//                mLeftButton.setText(cancelOrder);
//                mLeftButton.setVisibility(View.VISIBLE);
//                mRightButton.setText(payNow);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent paynowIntent = new Intent(OrderDetailsActivity.this, PayWayActivity.class);
//                        paynowIntent.putExtra("paymoney", String.valueOf(paymoney));
//                        paynowIntent.putExtra("orderId", rid);
//                        startActivity(paynowIntent);
//                    }
//                });
//
//                mLeftButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DefaultDialog(OrderDetailsActivity.this, App.getString(R.string.hint_dialog_cancel_order_title), App.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getcancelOrderNetRequestParams(rid);
//                                HttpRequest.post(params, URL.MY_CANCEL_ORDER, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//                break;
//            case 10://待发货
//                // 取消底部申请退款按钮
//                mLeftButton.setText(applyForRefund);
//                mLeftButton.setVisibility(View.INVISIBLE);
//
//                mRightButton.setText("提醒发货");
//                mRightButton.setVisibility(View.VISIBLE);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!mDialog.isShowing()) {
//                            mDialog.show();
//                        }
//                        HashMap<String, String> params = ClientDiscoverAPI.gettixingFahuoRequestParams(rid);
//                        HttpRequest.post(params, URL.SHOPPING_ALERT_SEND_GOODS, new GlobalDataCallBack() {
//                            @Override
//                            public void onSuccess(String json) {
//                                mDialog.dismiss();
//                                HttpResponse netBean = JsonUtil.fromJson(json, HttpResponse.class);
//                                if (netBean.isSuccess()) {
//                                    ToastUtils.showSuccess("提醒发货成功!");
//                                } else {
//                                    ToastUtils.showInfo(netBean.getMessage());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(String error) {
//                                mDialog.dismiss();
//                                ToastUtils.showError("网络错误");
//                            }
//                        });
//                    }
//                });
//                break;
//            case 13://已退款
//                mLeftButton.setVisibility(View.INVISIBLE);
//                mRightButton.setText(deleteOrder);
//                mRightButton.setVisibility(View.INVISIBLE);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DefaultDialog(OrderDetailsActivity.this, App.getString(R.string.hint_dialog_delete_order_title), App.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getdeleteOrderNetRequestParams(rid);
//                                HttpRequest.post(params, URL.MY_DELETE_ORDER, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//
//                                    }
//                                });
//                            }
//                        });
//
//                    }
//                });
//                break;
//            case 15://待收货
//                mLeftButton.setVisibility(View.INVISIBLE);
//                mRightButton.setText(confirmReceived);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String title = App.getString(R.string.hint_dialog_confirm_receipt_title);
//                        String[] textButtons = App.getStringArray(R.array.text_dialog_button);
//                        new DefaultDialog(OrderDetailsActivity.this, title, textButtons, new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getconfirmReceiveNetRequestParams(rid);
//                                HttpRequest.post(params, URL.SHOPPING_TAKE_DELIVERY, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//
//                                    }
//                                });
//                            }
//                        });
//
//                    }
//                });
//                break;
//            case 16://待评价
//                mLeftButton.setText(deleteOrder);
//                mLeftButton.setVisibility(View.INVISIBLE);
//                mRightButton.setText(publishEvaluate);//发表评价
//                mLeftButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DefaultDialog(OrderDetailsActivity.this, App.getString(R.string.hint_dialog_delete_order_title), App.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getdeleteOrderNetRequestParams(rid);
//                                HttpRequest.post(params, URL.MY_DELETE_ORDER, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//
//                                    }
//                                });
//                            }
//                        });
//
//                    }
//                });
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent in = new Intent(OrderDetailsActivity.this, PublishEvaluateActivity.class);
//                        in.putExtra("rid", rid);
//                        OrderDetailsActivity.this.startActivity(in);
//                    }
//                });
//                break;
//            case 20://已完成状态
//                mLeftButton.setVisibility(View.INVISIBLE);
//                mRightButton.setText(deleteOrder);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        new DefaultDialog(OrderDetailsActivity.this, App.getString(R.string.hint_dialog_delete_order_title), App.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getdeleteOrderNetRequestParams(rid);
//                                HttpRequest.post(params, URL.MY_DELETE_ORDER, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//
//                                    }
//                                });
//                            }
//                        });
//
//                    }
//                });
//                break;
//            case -1://已过期
//                mLeftButton.setVisibility(View.INVISIBLE);
//                mRightButton.setText(deleteOrder);
//                mRightButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DefaultDialog(OrderDetailsActivity.this, App.getString(R.string.hint_dialog_delete_order_title), App.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {
//                            @Override
//                            public void clickRight() {
//                                HashMap<String, String> params = ClientDiscoverAPI.getdeleteOrderNetRequestParams(rid);
//                                HttpRequest.post(params, URL.MY_DELETE_ORDER, new GlobalDataCallBack() {
//                                    @Override
//                                    public void onSuccess(String json) {
//                                        toShopOrderListActivity();
//                                    }
//
//                                    @Override
//                                    public void onFailure(String error) {
//
//                                    }
//                                });
//                            }
//                        });
//
//                    }
//                });
//                break;
//            case 12://从退货的查看详情点进来，底部那俩按钮都要消失
//                rlBottom.setVisibility(View.GONE);
//                break;
//        }
    }

}
