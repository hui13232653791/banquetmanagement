package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.DishesTypeMapper;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.vo.DishesType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜品类别服务层
 */
@Service
public class DishesTypeService extends BaseService<DishesType, Integer> {

    @Resource
    private DishesTypeMapper dishesTypeMapper;

    //查询所有的菜品类别
    public List<Map<String, Object>> queryAllDishesType(Integer dishesId) {
        return dishesTypeMapper.queryAllDishesType(dishesId);
    }

    // 添加菜品类别
    @Transactional(propagation = Propagation.REQUIRED)
    public void addDishesType(DishesType dishesType) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(dishesType.getDishesType()), "菜品类别名称不能为空！");
        //2、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(dishesTypeMapper.insertSelective(dishesType) < 1, "菜品类别添加失败！");
    }

    //更新菜品类别
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDishesType(DishesType dishesType) {
        // 判断公告ID是否为空，且数据存在
        AssertUtil.isTrue(null == dishesType.getId(), "待更新菜品类别不存在！");
        // 通过id查询数据
        DishesType temp = dishesTypeMapper.selectByPrimaryKey(dishesType.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新菜品类别不存在！");
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(dishesType.getDishesType()), "菜品类别名称不能为空！");
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(dishesTypeMapper.updateByPrimaryKeySelective(dishesType) != 1, "菜品类别更新失败！");
    }

    //菜品类别删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除菜品类别不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(dishesTypeMapper.deleteBatch(ids) != ids.length, "菜品类别删除失败！");
    }

    //统计菜品类别的菜品数量（饼状图数据处理）
    public Map<String, Object> countDishesNumberByDishesType() {
        Map<String, Object> map = new HashMap<>();
        // 查询客户构成数据的列表
        List<Map<String, Object>> dataList = dishesTypeMapper.countDishesNumberByDishesType();
        // 饼状图数据   数组（数组中是字符串）
        List<String> data1 = new ArrayList<>();
        // 饼状图的数据  数组（数组中是对象）
        List<Map<String, Object>> data2 = new ArrayList<>();
        // 判断数据列表 循环设置数据
        if (dataList != null && dataList.size() > 0) {
            // 遍历集合
            dataList.forEach(m -> {
                // 饼状图数据   数组（数组中是字符串）
                data1.add(m.get("dishes_type").toString());
                // 饼状图的数据  数组（数组中是对象）
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("name", m.get("dishes_type"));
                dataMap.put("value", m.get("count"));
                data2.add(dataMap);
            });
        }
        // 将X轴的数据集合与Y轴的数据集合，设置到map中
        map.put("data1", data1);
        map.put("data2", data2);
        return map;
    }

}
