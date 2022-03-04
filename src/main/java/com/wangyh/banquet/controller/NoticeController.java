package com.wangyh.banquet.controller;

import com.wangyh.banquet.base.BaseController;
import com.wangyh.banquet.base.ResultInfo;
import com.wangyh.banquet.query.NoticeQuery;
import com.wangyh.banquet.service.NoticeService;
import com.wangyh.banquet.vo.Notice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 待办控制层
 */
@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController {

    @Resource
    private NoticeService noticeService;

    //打开待办页面
    @RequestMapping("/index")
    public String index() {
        return "system/notice";
    }

    //分页多条件查询待办列表
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> seleteByParams(NoticeQuery noticeQuery) {
        return noticeService.queryByParamsForTable(noticeQuery);
    }

    //添加待办
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addNotice(Notice notice) {
        noticeService.addNotice(notice);
        return success("待办添加成功！");
    }

    //打开待办添加/修改页面
    @RequestMapping("/toAddOrUpdateNoticePage")
    public String toAddOrUpdateNoticePage(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询用户对象
        if (id != null) {
            //通过id查询用户对象
            Notice notice = noticeService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("noticeInfo", notice);
        }
        return "system/add_update";
    }

    //更新待办
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateNotice(Notice notice) {
        noticeService.updateNotice(notice);
        return success("待办更新成功！");
    }

    //根据ID查询待办
    @RequestMapping("/selectNoticeById")
    public String selectNoticeById(Integer id, HttpServletRequest request) {
        //判断id是否为空，不为空表示更新操作，查询用户对象
        if (id != null) {
            //通过id查询用户对象
            Notice notice = noticeService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("noticeInfo", notice);
        }
        noticeService.selectByPrimaryKey(id);
        return "system/notice_date";
    }

    //删除待办
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteNotice(Integer[] ids) {
        noticeService.deleteByIds(ids);
        return success("待办删除成功！");
    }

}
