package com.wangyh.banquet.query;

import com.wangyh.banquet.base.BaseQuery;

/**
 * 客户全部订单的查询类-订单信息
 */
public class CustomerOrderInfo extends BaseQuery {

    private String payState;

    private String khno;

    private String customerName;

    private Integer orderNumber;

    private Integer orderTotalprice;

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderTotalprice() {
        return orderTotalprice;
    }

    public void setOrderTotalprice(Integer orderTotalprice) {
        this.orderTotalprice = orderTotalprice;
    }
}
