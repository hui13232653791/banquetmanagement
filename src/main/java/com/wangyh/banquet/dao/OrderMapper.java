package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.vo.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单数据操作层
 */
public interface OrderMapper extends BaseMapper<Order, Integer> {

    //查询订单每年的销售额（折线图数据处理）
    List<Map<String, Object>> selectTotalePriceByYear(String yearTime);

}