package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.OrderMapper;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.vo.Order;
import com.wangyh.banquet.vo.Package;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 订单服务层
 */
@Service
public class OrderService extends BaseService<Order, Integer> {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PackageService packageService;

    // 创建订单
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrder(Order order) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(order.getKhno()), "订单客户不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(order.getpId()), "订单套餐不能为空！");
        AssertUtil.isTrue(null == order.getPackageNumber(), "套餐数量不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(order.getPayState()), "支付状态不能为空！");
        //2、设置参数
        String oId = "OD" + System.currentTimeMillis();
        order.setoId(oId);
        //设置订单总金额
        Package aPackage = packageService.selectByPrimaryKey(Integer.parseInt(order.getpId()));
        //套餐价格校验
        AssertUtil.isTrue(0 == aPackage.getPackagePrice(), "该套餐价格为0！请先定价");
        //订单总价
        int totalprice = aPackage.getPackagePrice() * order.getPackageNumber();
        order.setTotalprice(totalprice);
        order.setState(0);
        order.setCreateDate(new Date());
        order.setUpdateDate(new Date());
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(orderMapper.insertSelective(order) < 1, "订单创建失败！");
    }

    //更新订单
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrder(Order order) {
        // 判断订单ID是否为空，且数据存在
        AssertUtil.isTrue(null == order.getId(), "待更新订单不存在！");
        // 通过id查询数据
        Order temp = orderMapper.selectByPrimaryKey(order.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新订单不存在！");
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(order.getKhno()), "订单客户不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(order.getpId()), "订单套餐不能为空！");
        AssertUtil.isTrue(null == order.getPackageNumber(), "套餐数量不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(order.getPayState()), "支付状态不能为空！");
        // 设置默认值
        //设置订单总金额
        Package aPackage = packageService.selectByPrimaryKey(Integer.parseInt(order.getpId()));
        Integer totalprice = aPackage.getPackagePrice() * order.getPackageNumber();
        order.setTotalprice(totalprice);
        order.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(orderMapper.updateByPrimaryKeySelective(order) != 1, "订单更新失败！");
    }

    //订单删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除订单不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(orderMapper.deleteBatch(ids) != ids.length, "订单删除失败！");
    }

    //查询订单每年的销售额（折线图数据处理）
    public Map<String, Object> countOrderMake(String yearTime) {
        Map<String, Object> map = new HashMap<>();
        // 查询客户构成数据的列表
        List<Map<String, Object>> dataList = orderMapper.selectTotalePriceByYear(yearTime);
        // 折线图X轴数据  数组
        List<String> data1 = new ArrayList<>();
        // 折线图Y轴数据  数组
        List<Integer> data2 = new ArrayList<>();
        // 判断数据列表 循环设置数据
        if (dataList != null && dataList.size() > 0) {
            // 遍历集合
            dataList.forEach(m -> {
                // 获取"level"对应的数据，设置到X轴的集合中
                data1.add(m.get("month").toString());
                // 获取"total"对应的数据，设置到Y轴的集合中
                data2.add(Integer.parseInt(m.get("totalprice").toString()));
            });
        }
        // 将X轴的数据集合与Y轴的数据集合，设置到map中
        map.put("data1", data1);
        map.put("data2", data2);
        return map;
    }

}
