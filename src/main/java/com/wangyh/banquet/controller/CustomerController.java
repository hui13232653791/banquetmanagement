package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.CustomerOrderInfo;
import com.wangyh.banquet.query.CustomerQuery;
import com.wangyh.banquet.service.CustomerService;
import com.wangyh.banquet.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 客户控制层
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    //打开客户信息管理页面
    @RequestMapping("/index")
    public String index() {
        return "customer/customer";
    }

    //分页多条件查询客户列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(CustomerQuery customerQuery) {
        return customerService.queryByParamsForTable(customerQuery);
    }

    //添加客户
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addCustomer(Customer customer) {
        customerService.addCustomer(customer);
        return success("客户添加成功！");
    }

    //打开客户添加/修改页面
    @RequestMapping("/toAddOrUpdateCustomerPage")
    public String toAddOrUpdateCustomerPage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询用户对象
        if (id != null) {
            //通过id查询客户对象
            Customer customer = customerService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("customerInfo", customer);
        }
        return "customer/add_update";
    }

    //更新客户
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
        return success("客户更新成功！");
    }

    //删除客户--更新操作
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer[] ids) {
        customerService.deleteByIds(ids);
        return success("客户删除成功！");
    }

    //查询所有的客户
    @RequestMapping("queryAllCustomer")
    @ResponseBody
    public List<Map<String, Object>> queryAllCustomer(Integer orderId) {
        return customerService.queryAllCustomer(orderId);
    }

    //打开该客户的订单信息页面
    @RequestMapping("/toCustomerOrderInfoPage")
    public String toCustomerOrderInfoPage(Integer id, HttpServletRequest request){
        //通过客户ID查询客户的订单信息
        CustomerOrderInfo customerOrderInfo = customerService.queryCustomerOrderInfo(id);
        //将对象设置到请求域中
        request.setAttribute("customerOrderInfo",customerOrderInfo);
        return "customer/customer_orderInfo";
    }


}
