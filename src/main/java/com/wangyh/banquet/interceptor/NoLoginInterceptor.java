package com.wangyh.banquet.interceptor;

import com.wangyh.banquet.controller.UserController;
import com.wangyh.banquet.dao.UserMapper;
import com.wangyh.banquet.exceptions.NoLoginException;
import com.wangyh.banquet.utils.CookieUtil;
import com.wangyh.banquet.utils.LoginUserUtil;
import com.wangyh.banquet.vo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 非法访问拦截
 *      继承HandlerInterceptorAdapter适配器
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserMapper userMapper;

    /**
     * 拦截用户是否是登录状态
     *  在目标方法（目标资源）执行前，执行的方法
     *
     *  方法返回布尔类型：
     *      如果返回true，表示目标方法可以被执行
     *      如果返回false，表示阻止目标方法执行
     *
     *  如果判断用户是否是登录状态：
     *      1. 判断cookie中是否存在用户信息（获取用户ID）
     *      2. 数据库中是否存在指定用户ID的值
     *
     *  如果用户是登录状态，则允许目标方法执行；
     *  如果用户是非登录状态，则抛出未登录异常 （在全局异常中做判断，如果是未登录异常，则跳转到登录页面）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie中的用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userMapper.selectByPrimaryKey(userId);
        // 判断用户ID是否为空，且数据库中存在该ID的用户记录
        if (null == userId || user == null) {
            // 抛出未登录异常
            throw new NoLoginException();
        }else{
            // 用户单一登录。账号已在web端登录后，再次登录相同账号将踢掉前一次的登录信息。
            // 拿当前session对象与通过userName获取的HttpSession对象去对比，如果不一致则异地登录,此时给出提示并且跳转到登录页面。
            HttpSession httpSession = UserController.sessionMap.get(user.getUserName());
            if(!httpSession.getId().equals(request.getSession().getId())){
                //清除cookie
                CookieUtil.deleteCookie("userName",request,response);
                CookieUtil.deleteCookie("trueName",request,response);
                CookieUtil.deleteCookie("userIdStr",request,response);
                //设置request，response字符集，否则输出的script会乱码
                request.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(
                        "<script type='text/javascript'>"
                                + "alert('您的账号已在异地登录，请重新登录!'); "
                                + "top.location.href='"
                                + request.getContextPath()+ "/index" + "';</script>");
                return false;
            }
            return true;
        }
    }

}
