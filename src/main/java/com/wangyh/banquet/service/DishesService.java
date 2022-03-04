package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.DishesMapper;
import com.wangyh.banquet.dao.DishesTypeMapper;
import com.wangyh.banquet.dao.packageDishesMapper;
import com.wangyh.banquet.model.TreeDishes;
import com.wangyh.banquet.query.DishesTypeQuery;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.vo.Dishes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 菜品服务层
 */
@Service
public class DishesService extends BaseService<Dishes, Integer> {

    @Resource
    private DishesMapper dishesMapper;

    @Resource
    private packageDishesMapper packageDishesMapper;

    @Resource
    private DishesTypeMapper dishesTypeMapper;

    // 添加菜品
    @Transactional(propagation = Propagation.REQUIRED)
    public void addDishes(Dishes dishes) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(dishes.getDishesName()), "菜品名称不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(dishes.getDishesPrice()), "菜品价格不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(dishes.getDishesType()), "菜品类别不能为空！");
        //2、设置参数
        dishes.setCreateDate(new Date());
        dishes.setUpdateDate(new Date());
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(dishesMapper.insertSelective(dishes) < 1, "菜品添加失败！");
    }

    //更新菜品
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDishes(Dishes dishes) {
        // 判断菜品ID是否为空，且数据存在
        AssertUtil.isTrue(null == dishes.getId(), "待更新菜品不存在！");
        // 通过id查询数据
        Dishes temp = dishesMapper.selectByPrimaryKey(dishes.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新套餐不存在！");
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(dishes.getDishesName()), "菜品名称不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(dishes.getDishesPrice()), "菜品价格不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(dishes.getDishesType()), "菜品类别不能为空！");
        // 设置默认值
        dishes.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(dishesMapper.updateByPrimaryKeySelective(dishes) != 1, "菜品更新失败！");
    }

    //菜品删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除菜品不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(dishesMapper.deleteBatch(ids) != ids.length, "菜品删除失败！");
    }

    //查询所有的菜品表
    public List<TreeDishes> queryAllDishes(Integer packageId) {
        //查询所有的菜品列表
        List<TreeDishes> treeDishesList = dishesMapper.queryAllDishes();
        //查询所有的菜品类别列表
        List<TreeDishes> treeDisheTypesList = dishesTypeMapper.queryDishesType();
        //合并list
        treeDishesList.addAll(treeDisheTypesList);

        //查询指定套餐已经授权过的菜品列表（查询套餐拥有的菜品ID）
        List<Integer> dishesIds = packageDishesMapper.queryPackageHasDishesIdByPackageId(packageId);
        // 判断套餐是否拥有菜品ID
        if (dishesIds != null && dishesIds.size() > 0) {
            // 循环所有的菜品表，判断套餐拥有的菜品ID中是否有匹配的，如果有，则设置checked属性为true
            treeDishesList.forEach(treeDishes -> {
                // 判断角色拥有的资源ID中是否有当前遍历的资源ID
                if (dishesIds.contains(treeDishes.getId())) {
                    // 如果包含你，则说明角色授权过，设置checked为true
                    treeDishes.setChecked(true);
                }
            });
        }

        return treeDishesList;
    }

}
