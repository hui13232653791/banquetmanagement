package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.model.UserModel;
import com.wangyh.banquet.query.UserQuery;
import com.wangyh.banquet.service.LoginfoService;
import com.wangyh.banquet.service.UserService;
import com.wangyh.banquet.utils.LoginUserUtil;
import com.wangyh.banquet.vo.Loginfo;
import com.wangyh.banquet.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制层
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private LoginfoService loginfoService;

    //用户单一登录。key为用户userName，value为用户登录时session(同一浏览器session相同)
    public static HashMap<String, HttpSession> sessionMap = new HashMap<>();

    //用户登录
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd,HttpSession session) {
        ResultInfo resultInfo = new ResultInfo();
        //调用service层登录方法
        UserModel userModel = userService.userLogin(userName, userPwd);
        //设置resultInfo的result的值 （将来数据返回给请求）
        resultInfo.setResult(userModel);
        //设置了全局异常类统一处理异常-GlobalExceptionResolver，不用再在这里处理

        //记录登录日志信息
        Loginfo loginfo = new Loginfo();
        //登录用户名
        loginfo.setLoginName(userName);
        //登录IP地址
        loginfo.setLoginIp(LoginUserUtil.getIpAddr(LoginUserUtil.getRequest()));
        //登录时间
        loginfo.setLoginDate(new Date());
        loginfoService.insertSelective(loginfo);

        //用户单一登录。用户登录成功时将用户信息及session存入sessionMap（同一浏览器session相同）。
        sessionMap.put(userName, session);

        return resultInfo;
    }

    //用户修改密码
    @PostMapping("/updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request, String oldPassword, String newPassword, String repeatPassword) {
        ResultInfo resultInfo = new ResultInfo();
        //获取cookie中的userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //调用service层修改方法
        userService.updatePassWord(userId, oldPassword, newPassword, repeatPassword);
        //设置了全局异常类统一处理异常-GlobalExceptionResolver，不用再在这里处理
        return resultInfo;
    }

    //进入修改密码的页面
    @RequestMapping("/toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    //进入基本资料的页面
    @RequestMapping("/toSettingPage")
    public String toSettingPage() {
        return "setting";
    }

    //更新用户基本资料
    @PostMapping("/updateSetting")
    @ResponseBody
    public ResultInfo updateSetting(User user) {
        userService.updateSetting(user);
        return success("基本资料更新成功！");
    }

    //分页多条件查询用户列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(UserQuery userQuery) {
        return userService.queryByParamsForTable(userQuery);
    }

    //进入用户列表页面
    @RequestMapping("/index")
    public String index() {
        return "user/user";
    }

    //添加用户
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addUser(User user) {
        userService.addUser(user);
        return success("用户添加成功！");
    }

    //打开用户添加/修改页面
    @RequestMapping("/toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询用户对象
        if (id != null) {
            //通过id查询用户对象
            User user = userService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("userInfo", user);
        }
        return "user/add_update";
    }

    //更新用户
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    //用户删除
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids) {
        userService.deleteByIds(ids);
        return success("用户删除成功！");
    }

}
