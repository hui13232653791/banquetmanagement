package com.wangyh.banquet.query;

import com.wangyh.banquet.base.BaseQuery;

/**
 * 菜品的查询类
 */
public class DishesQuery extends BaseQuery {

    private String dishesName;

    private String dishesType;

    private Integer ldishesPrice;

    private Integer gdishesPrice;

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public String getDishesType() {
        return dishesType;
    }

    public void setDishesType(String dishesType) {
        this.dishesType = dishesType;
    }

    public Integer getLdishesPrice() {
        return ldishesPrice;
    }

    public void setLdishesPrice(Integer ldishesPrice) {
        this.ldishesPrice = ldishesPrice;
    }

    public Integer getGdishesPrice() {
        return gdishesPrice;
    }

    public void setGdishesPrice(Integer gdishesPrice) {
        this.gdishesPrice = gdishesPrice;
    }
}
