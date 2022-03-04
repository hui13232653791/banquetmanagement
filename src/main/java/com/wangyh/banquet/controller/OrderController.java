package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.OrderQuery;
import com.wangyh.banquet.service.OrderService;
import com.wangyh.banquet.vo.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 订单控制层
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    //打开订单信息管理页面
    @RequestMapping("/index")
    public String index() {
        return "order/order";
    }

    //分页多条件查询订单列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(OrderQuery orderQuery) {
        return orderService.queryByParamsForTable(orderQuery);
    }

    //创建订单
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addOrder(Order order) {
        orderService.addOrder(order);
        return success("订单创建成功！");
    }

    //打开订单创建/修改页面
    @RequestMapping("/toAddOrUpdateOrderPage")
    public String toAddOrUpdateOrderPage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询菜品对象
        if (id != null) {
            //通过id查询订单对象
            Order order = orderService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("orderInfo", order);
        }
        return "order/add_update";
    }

    //更新订单
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateOrder(Order order) {
        orderService.updateOrder(order);
        return success("订单更新成功！");
    }

    //删除订单
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteOrder(Integer[] ids) {
        orderService.deleteByIds(ids);
        return success("订单删除成功！");
    }

    //归档订单
    @PostMapping("/endOrder")
    @ResponseBody
    public ResultInfo endOrder(Integer id) {
        Order order = orderService.selectByPrimaryKey(id);
        order.setState(1);
        orderService.updateOrder(order);
        return success("订单归档成功！");
    }

    //打开归档订单页面
    @RequestMapping("/endorder")
    public String endorder() {
        return "order/endorder";
    }

    //打开订单详情页面
    @RequestMapping("/toEndOrderInfoPage")
    public String toEndOrderInfoPage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询菜品对象
        if (id != null) {
            //通过id查询订单对象
            Order order = orderService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("orderInfo", order);
        }
        return "order/endorderinfo";
    }

    //打开订单销售额统计页面
    @RequestMapping("/orderinfo")
    public String orderinfo() {
        return "report/orderinfo";
    }

    //查询订单每年的销售额
    @RequestMapping("countOrderMake")
    @ResponseBody
    public Map<String, Object> countOrderMake(String yearTime) {
        return orderService.countOrderMake(yearTime);
    }

}
