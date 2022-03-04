package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.DishesTypeQuery;
import com.wangyh.banquet.service.DishesTypeService;
import com.wangyh.banquet.vo.DishesType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 菜品类别控制层
 */
@Controller
@RequestMapping("/dishestype")
public class DishesTypeController extends BaseController {

    @Resource
    private DishesTypeService dishesTypeService;

    //打开菜品类别管理页面
    @RequestMapping("/index")
    public String index() {
        return "dishestype/dishestype";
    }

    //分页多条件查询菜品类别
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(DishesTypeQuery dishesTypeQuery) {
        return dishesTypeService.queryByParamsForTable(dishesTypeQuery);
    }

    //查询所有的菜品类别
    @RequestMapping("queryAllDishesType")
    @ResponseBody
    public List<Map<String, Object>> queryAllDishesType(Integer dishesId) {
        return dishesTypeService.queryAllDishesType(dishesId);
    }

    //添加菜品类别
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addDishesType(DishesType dishesType) {
        dishesTypeService.addDishesType(dishesType);
        return success("菜品类别添加成功！");
    }

    //打开菜品类别添加/修改页面
    @RequestMapping("/toAddOrUpdateDishesTypePage")
    public String toAddOrUpdateDishesTypePage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询菜品类别对象
        if (id != null) {
            //通过id查询菜品类别对象
            DishesType dishesType = dishesTypeService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("dishesTypeInfo", dishesType);
        }
        return "dishestype/add_update";
    }

    //更新菜品类别
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateDishesType(DishesType dishesType) {
        dishesTypeService.updateDishesType(dishesType);
        return success("菜品类别更新成功！");
    }

    //删除菜品类别
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deletePrckage(Integer[] ids) {
        dishesTypeService.deleteByIds(ids);
        return success("菜品类别删除成功！");
    }

    //打开菜品类别统计页面
    @RequestMapping("/dishestypecount")
    public String dishestypecount() {
        return "report/dishestypecount";
    }

    //统计菜品类别的菜品数量
    @RequestMapping("countDishesNumberByDishesType")
    @ResponseBody
    public Map<String, Object> countDishesNumberByDishesType() {
        return dishesTypeService.countDishesNumberByDishesType();
    }

}
