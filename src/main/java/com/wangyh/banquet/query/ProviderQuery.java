package com.wangyh.banquet.query;

import com.wangyh.banquet.base.BaseQuery;

/**
 * 供应商的查询类
 */
public class ProviderQuery extends BaseQuery {

    private String pno;

    private String providerName;

    private String foodName;

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
