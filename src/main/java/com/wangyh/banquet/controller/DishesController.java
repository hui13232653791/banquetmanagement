package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.model.TreeDishes;
import com.wangyh.banquet.query.DishesQuery;
import com.wangyh.banquet.service.DishesService;
import com.wangyh.banquet.vo.Dishes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 菜品控制层
 */
@Controller
@RequestMapping("/dishes")
public class DishesController extends BaseController {

    @Resource
    private DishesService dishesService;

    //打开菜品信息管理页面
    @RequestMapping("/index")
    public String index() {
        return "dishes/dishes";
    }

    //分页多条件查询菜品列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(DishesQuery dishesQuery) {
        return dishesService.queryByParamsForTable(dishesQuery);
    }

    //添加菜品
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addDishes(Dishes dishes) {
        dishesService.addDishes(dishes);
        return success("菜品添加成功！");
    }

    //打开菜品添加/修改页面
    @RequestMapping("/toAddOrUpdateDishesPage")
    public String toAddOrUpdateDishesPage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询菜品对象
        if (id != null) {
            //通过id查询菜品对象
            Dishes dishes = dishesService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("dishesInfo", dishes);
        }
        return "dishes/add_update";
    }

    //更新菜品
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateDishes(Dishes dishes) {
        dishesService.updateDishes(dishes);
        return success("菜品更新成功！");
    }

    //删除菜品
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteDishes(Integer[] ids) {
        dishesService.deleteByIds(ids);
        return success("菜品删除成功！");
    }

    //查询所有的菜品列表
    @RequestMapping("/queryAllDishes")
    @ResponseBody
    public List<TreeDishes> queryAllDishes(Integer packageId) {
        return dishesService.queryAllDishes(packageId);
    }

}
