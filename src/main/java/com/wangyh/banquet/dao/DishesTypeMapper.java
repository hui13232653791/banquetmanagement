package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.model.TreeDishes;
import com.wangyh.banquet.vo.DishesType;

import java.util.List;
import java.util.Map;

/**
 * 菜品类别数据操作层
 */
public interface DishesTypeMapper extends BaseMapper<DishesType, Integer> {

    //查询所有菜品类别
    List<Map<String, Object>> queryAllDishesType(Integer dishesId);

    //查询所有菜品类别
    List<TreeDishes> queryDishesType();

    //统计菜品类别的菜品数量
    List<Map<String, Object>> countDishesNumberByDishesType();

}