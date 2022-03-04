package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.LoginfoQuery;
import com.wangyh.banquet.service.LoginfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 登录日志控制层
 */
@Controller
@RequestMapping("/loginfo")
public class LoginfoController extends BaseController {

    @Resource
    private LoginfoService loginfoService;

    //打开登录日志页面
    @RequestMapping("/index")
    public String index() {
        return "system/loginfo";
    }

    //分页多条件查询登录日志列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(LoginfoQuery loginfoQuery) {
        return loginfoService.queryByParamsForTable(loginfoQuery);
    }

    //登录日志删除
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids) {
        loginfoService.deleteByIds(ids);
        return success("登录日志删除成功！");
    }

}
