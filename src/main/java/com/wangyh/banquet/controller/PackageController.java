package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.PackageQuery;
import com.wangyh.banquet.service.PackageService;
import com.wangyh.banquet.vo.Package;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 套餐控制层
 */
@Controller
@RequestMapping("/package")
public class PackageController extends BaseController {

    @Resource
    private PackageService packageService;

    //打开套餐信息管理页面
    @RequestMapping("/index")
    public String index() {
        return "package/package";
    }

    //分页多条件查询套餐列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(PackageQuery packageQuery) {
        return packageService.queryByParamsForTable(packageQuery);
    }

    //添加套餐
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addPackage(Package packages) {
        packageService.addPackage(packages);
        return success("套餐添加成功！");
    }

    //打开套餐添加/修改页面
    @RequestMapping("/toAddOrUpdatePackagePage")
    public String toAddOrUpdatePackagePage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询套餐对象
        if (id != null) {
            //通过id查询客户对象
            Package packages = packageService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("packageInfo", packages);
        }
        return "package/add_update";
    }

    //更新套餐
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updatePackage(Package packages) {
        packageService.updatePackage(packages);
        return success("套餐更新成功！");
    }

    //删除套餐
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deletePrckage(Integer[] ids) {
        packageService.deleteByIds(ids);
        return success("套餐删除成功！");
    }

    //套餐分配菜品-授权
    @RequestMapping("/addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer packageId, Integer[] dIds) {
        packageService.addGrant(packageId, dIds);
        return success("分配菜品成功！");
    }

    //打开分配菜品页面
    @RequestMapping("/toAddGrantPage")
    public String toAddGrantPage(Integer packageId, HttpServletRequest request) {
        //将需要授权的套餐ID设置到请求域中
        request.setAttribute("packageId", packageId);
        return "package/grant";
    }

    //查询所有的套餐
    @RequestMapping("queryAllPackage")
    @ResponseBody
    public List<Map<String, Object>> queryAllPackage(Integer orderId) {
        return packageService.queryAllPackage(orderId);
    }

}
