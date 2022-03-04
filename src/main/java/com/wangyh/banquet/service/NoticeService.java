package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.NoticeMapper;
import com.wangyh.banquet.utils.*;
import com.wangyh.banquet.vo.Notice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 待办服务层
 */
@Service
public class NoticeService extends BaseService<Notice, Integer> {

    @Resource
    private NoticeMapper noticeMapper;

    // 添加待办
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNotice(Notice notice) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(notice.getTitle()), "待办标题不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(notice.getContent()), "待办内容不能为空！");
        //2、设置参数
        notice.setCreateDate(new Date());
        notice.setUpdateDate(new Date());
        notice.setOpenName(CookieUtil.getCookieValue(LoginUserUtil.getRequest(), "userName")+" - "+CookieUtil.getCookieValue(LoginUserUtil.getRequest(), "trueName"));
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(noticeMapper.insertSelective(notice) < 1, "待办添加失败！");
    }

    //更新待办
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateNotice(Notice notice) {
        // 判断公告ID是否为空，且数据存在
        AssertUtil.isTrue(null == notice.getId(), "待更新待办不存在！");
        // 通过id查询数据
        Notice temp = noticeMapper.selectByPrimaryKey(notice.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新待办不存在！");
        // 参数校验
        AssertUtil.isTrue(StringUtils.isBlank(notice.getTitle()), "待办标题不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(notice.getContent()), "待办内容不能为空！");
        // 设置默认值
        notice.setUpdateDate(new Date());
        notice.setOpenName(CookieUtil.getCookieValue(LoginUserUtil.getRequest(), "trueName"));
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(noticeMapper.updateByPrimaryKeySelective(notice) != 1, "待办更新失败！");
    }

    //待办删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除待办不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(noticeMapper.deleteBatch(ids) != ids.length, "待办删除失败！");
    }

}
