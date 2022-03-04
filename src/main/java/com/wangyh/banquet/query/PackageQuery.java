package com.wangyh.banquet.query;

import com.wangyh.banquet.base.BaseQuery;

/**
 * 套餐的查询类
 */
public class PackageQuery extends BaseQuery {

    private String packageName;

    private Integer lpackagePrice;

    private Integer gpackagePrice;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getLpackagePrice() {
        return lpackagePrice;
    }

    public void setLpackagePrice(Integer lpackagePrice) {
        this.lpackagePrice = lpackagePrice;
    }

    public Integer getGpackagePrice() {
        return gpackagePrice;
    }

    public void setGpackagePrice(Integer gpackagePrice) {
        this.gpackagePrice = gpackagePrice;
    }
}
