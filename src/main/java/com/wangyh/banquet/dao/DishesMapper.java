package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.model.TreeDishes;
import com.wangyh.banquet.vo.Dishes;

import java.util.List;

/**
 * 菜品数据操作层
 */
public interface DishesMapper extends BaseMapper<Dishes, Integer> {

    //查询所有的菜品列表
    List<TreeDishes> queryAllDishes();

}