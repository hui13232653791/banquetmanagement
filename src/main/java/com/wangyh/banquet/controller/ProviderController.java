package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.ProviderQuery;
import com.wangyh.banquet.service.ProviderService;
import com.wangyh.banquet.vo.Provider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 供应商控制层
 */
@Controller
@RequestMapping("/provider")
public class ProviderController extends BaseController {

    @Resource
    private ProviderService providerService;

    //打开供应商信息管理页面
    @RequestMapping("/index")
    public String index() {
        return "provider/provider";
    }

    //分页多条件查询供应商列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(ProviderQuery providerQuery) {
        return providerService.queryByParamsForTable(providerQuery);
    }

    //添加供应商
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addProvider(Provider provider) {
        providerService.addProvider(provider);
        return success("供应商添加成功！");
    }

    //打开供应商添加/修改页面
    @RequestMapping("/toAddOrUpdateProviderPage")
    public String toAddOrUpdateProviderPage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询用户对象
        if (id != null) {
            //通过id查询供应商对象
            Provider provider = providerService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("providerInfo", provider);
        }
        return "provider/add_update";
    }

    //更新客户
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateProvider(Provider provider) {
        providerService.updateProvider(provider);
        return success("供应商更新成功！");
    }

    //删除供应商=更新操作
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteProvider(Integer[] ids) {
        providerService.deleteByIds(ids);
        return success("供应商删除成功！");
    }

}
