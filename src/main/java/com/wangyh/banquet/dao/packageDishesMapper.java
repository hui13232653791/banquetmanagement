package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.vo.packageDishes;

import java.util.List;

/**
 * 套餐分配菜品数据操作层
 */
public interface packageDishesMapper extends BaseMapper<packageDishes, Integer> {

    //通过套餐ID查询套餐对应的菜品记录
    Integer countDishesBypackageId(Integer packageId);

    //通过套餐ID删除对应的菜品记录
    void deleteDishesByPackageId(Integer packageId);

    //查询套餐拥有的所有菜品ID的集合
    List<Integer> queryPackageHasDishesIdByPackageId(Integer packageId);

}