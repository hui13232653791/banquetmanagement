package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.query.CustomerOrderInfo;
import com.wangyh.banquet.vo.Customer;

import java.util.List;
import java.util.Map;

/**
 * 客户数据操作层
 */
public interface CustomerMapper extends BaseMapper<Customer, Integer> {

    //查询所有客户
    List<Map<String, Object>> queryAllCustomer(Integer orderId);

    //通过客户ID查询客户的订单信息
    CustomerOrderInfo queryCustomerOrderInfo(Integer id);

}