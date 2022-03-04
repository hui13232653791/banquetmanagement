package com.wangyh.banquet.vo;

/**
 * 菜品类型的实体类
 */
public class DishesType{
    private Integer id;

    private String dishesType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDishesType() {
        return dishesType;
    }

    public void setDishesType(String dishesType) {
        this.dishesType = dishesType == null ? null : dishesType.trim();
    }

}