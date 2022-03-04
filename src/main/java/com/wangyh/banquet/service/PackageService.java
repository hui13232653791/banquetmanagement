package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.DishesMapper;
import com.wangyh.banquet.dao.PackageMapper;
import com.wangyh.banquet.dao.packageDishesMapper;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.vo.Dishes;
import com.wangyh.banquet.vo.Package;
import com.wangyh.banquet.vo.packageDishes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 套餐服务层
 */
@Service
public class PackageService extends BaseService<Package,Integer> {

    @Resource
    private PackageMapper packageMapper;

    @Resource
    private DishesMapper dishesMapper;

    @Resource
    private packageDishesMapper packageDishesMapper;

    // 添加套餐
    @Transactional(propagation = Propagation.REQUIRED)
    public void addPackage(Package packages) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(packages.getPackageName()), "套餐名称不能为空！");
        //2、设置参数
        packages.setCreateDate(new Date());
        packages.setUpdateDate(new Date());
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(packageMapper.insertSelective(packages) < 1, "套餐添加失败！");
    }

    //更新套餐
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePackage(Package packages) {
        // 判断公告ID是否为空，且数据存在
        AssertUtil.isTrue(null == packages.getId(), "待更新套餐不存在！");
        // 通过id查询数据
        Package temp = packageMapper.selectByPrimaryKey(packages.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新套餐不存在！");
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(packages.getPackageName()), "套餐名称不能为空！");
        // 设置默认值
        packages.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(packageMapper.updateByPrimaryKeySelective(packages) != 1, "套餐更新失败！");
    }

    //套餐删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除套餐不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(packageMapper.deleteBatch(ids) != ids.length, "套餐删除失败！");
    }

    /**
     * 套餐分配菜品-授权
     *
     *  将对应的套餐ID与菜品ID，添加到对应的套餐-菜品表中
     *      直接添加菜品：不合适，会出现重复的菜品数据
     *      推荐使用：
     *          先将已有的菜品记录删除，再将需要设置的菜品记录添加
     *          1、通过套餐ID查询对应的菜品记录
     *          2、如果菜品记录存在，则删除对应的套餐拥有的菜品记录
     *          3、如果没有菜品记录，则添加菜品记录 (批量添加)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer packageId, Integer[] dIds) {
        // 1、通过套餐ID查询对应的菜品记录
        Integer count = packageDishesMapper.countDishesBypackageId(packageId);
        // 2、如果菜品记录存在，则删除对应的套餐拥有的菜品记录
        if (count > 0) {
            // 删除菜品记录
            packageDishesMapper.deleteDishesByPackageId(packageId);
        }
        // 3、如果没有菜品记录，则添加菜品记录
        int packagePrice = 0;
        if (dIds != null &&  dIds.length > 0) {
            // 定义packageDishes集合
            List<packageDishes> packageDishesList = new ArrayList<>();
            // 遍历资源ID数组
            for(Integer dId: dIds) {
                packageDishes packageDishes = new packageDishes();
                packageDishes.setdId(dId);
                packageDishes.setpId(packageId);
                // 将对象设置到集合中
                packageDishesList.add(packageDishes);
                //统计套餐价格,dId大于50表示排除菜品类别的dId
                if (dId > 50){
                    Dishes dishes = dishesMapper.selectByPrimaryKey(dId);
                    int pishesPrice = Integer.parseInt(dishes.getDishesPrice());
                    packagePrice += pishesPrice;
                }
            }
            // 执行批量添加操作，判断受影响的行数
            AssertUtil.isTrue(packageDishesMapper.insertBatch(packageDishesList) != packageDishesList.size(), "菜品分配失败！");
            //套餐分配菜品后，更新套餐包含的菜品数量和套餐价格
            Package aPackage = packageMapper.selectByPrimaryKey(packageId);
            aPackage.setDishesNumber(packageDishesMapper.countDishesBypackageId(packageId));
            aPackage.setPackagePrice(packagePrice);
            packageMapper.updateByPrimaryKeySelective(aPackage);
        }
    }

    //查询所有的套餐
    public List<Map<String, Object>> queryAllPackage(Integer orderId) {
        return packageMapper.queryAllPackage(orderId);
    }

}
