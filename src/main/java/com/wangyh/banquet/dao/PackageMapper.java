package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.vo.Package;

import java.util.List;
import java.util.Map;

/**
 * 套餐数据操作层
 */
public interface PackageMapper extends BaseMapper<Package, Integer> {

    //查询所有套餐
    List<Map<String, Object>> queryAllPackage(Integer orderId);

}