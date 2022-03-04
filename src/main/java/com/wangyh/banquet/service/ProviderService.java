package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.ProviderMapper;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.utils.PhoneUtil;
import com.wangyh.banquet.vo.Provider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 供应商服务层
 */
@Service
public class ProviderService extends BaseService<Provider,Integer> {

    @Resource
    private ProviderMapper providerMapper;

    // 添加供应商
    @Transactional(propagation = Propagation.REQUIRED)
    public void addProvider(Provider provider) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(provider.getProviderName()), "供应商姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(provider.getPhone()), "手机电话不能为空！");
        // 手机号码 phone   格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(provider.getPhone()), "手机号码格式不正确！");
        //2、设置参数
        // 供应商编号   P + 时间戳
        String pno = "P" + System.currentTimeMillis();
        provider.setPno(pno);
        provider.setState(0);
        provider.setCreateDate(new Date());
        provider.setUpdateDate(new Date());
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(providerMapper.insertSelective(provider) < 1, "供应商添加失败！");
    }

    //更新供应商
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProvider(Provider provider) {
        // 判断公告ID是否为空，且数据存在
        AssertUtil.isTrue(null == provider.getId(), "待更新供应商不存在！");
        // 通过id查询数据
        Provider temp = providerMapper.selectByPrimaryKey(provider.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新供应商不存在！");
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(provider.getProviderName()), "供应商姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(provider.getPhone()), "手机电话不能为空！");
        // 手机号码 phone   格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(provider.getPhone()), "手机号码格式不正确！");
        // 设置默认值
        provider.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(providerMapper.updateByPrimaryKeySelective(provider) != 1, "供应商更新失败！");
    }

    //供应商删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除供应商不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(providerMapper.deleteBatch(ids) != ids.length, "供应商删除失败！");
    }

}
