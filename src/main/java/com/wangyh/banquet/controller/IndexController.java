package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.service.UserService;
import com.wangyh.banquet.utils.LoginUserUtil;
import com.wangyh.banquet.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 主页控制层
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    //系统登录页
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    // 系统界面欢迎页
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    //后端管理主页面
    @RequestMapping("/main")
    public String main(HttpServletRequest request) {
        //获取cookie中的用户Id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //查询用户对象，设置session作用域
        User user = userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("user", user);
        return "main";
    }

}
