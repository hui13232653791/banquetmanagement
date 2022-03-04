package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.LoginfoMapper;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.vo.Loginfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 登录日志服务层
 */
@Service
public class LoginfoService extends BaseService<Loginfo, Integer> {

    @Resource
    private LoginfoMapper loginfoMapper;

    //登录日志删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除记录不存在！");
        // 执行删除操作，判断受影响的行数
        AssertUtil.isTrue(loginfoMapper.deleteBatch(ids) != ids.length, "登录日志删除失败！");
    }

}
