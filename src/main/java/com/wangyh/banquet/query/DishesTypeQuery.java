package com.wangyh.banquet.query;

import com.wangyh.banquet.base.BaseQuery;

/**
 * 菜品类别的查询类
 */
public class DishesTypeQuery extends BaseQuery {

    private String dishesType;

    private String dishesId;

    public String getDishesType() {
        return dishesType;
    }

    public void setDishesType(String dishesType) {
        this.dishesType = dishesType;
    }

    public String getDishesId() {
        return dishesId;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }
}
