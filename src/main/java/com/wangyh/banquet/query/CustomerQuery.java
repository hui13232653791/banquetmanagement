package com.wangyh.banquet.query;

import com.wangyh.banquet.base.BaseQuery;

/**
 * 客户的查询类
 */
public class CustomerQuery extends BaseQuery {

    private String khno;

    private String customerName;

    private String phone;

    private String address;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
