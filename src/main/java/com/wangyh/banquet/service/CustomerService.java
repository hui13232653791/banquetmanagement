package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.CustomerMapper;
import com.wangyh.banquet.query.CustomerOrderInfo;
import com.wangyh.banquet.utils.AssertUtil;
import com.wangyh.banquet.utils.PhoneUtil;
import com.wangyh.banquet.vo.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户服务层
 */
@Service
public class CustomerService extends BaseService<Customer, Integer> {

    @Resource
    private CustomerMapper customerMapper;

    // 添加客户
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomer(Customer customer) {
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(customer.getCustomerName()), "客户姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()), "手机电话不能为空！");
        // 手机号码 phone   格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(customer.getPhone()), "手机号码格式不正确！");
        //2、设置参数
        // 客户编号   KH + 时间戳
        String khno = "KH" + System.currentTimeMillis();
        customer.setKhno(khno);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(customerMapper.insertSelective(customer) < 1, "客户添加失败！");
    }

    //更新客户
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer) {
        // 判断公告ID是否为空，且数据存在
        AssertUtil.isTrue(null == customer.getId(), "待更新客户不存在！");
        // 通过id查询数据
        Customer temp = customerMapper.selectByPrimaryKey(customer.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新客户不存在！");
        //1、参数校验
        AssertUtil.isTrue(StringUtils.isBlank(customer.getCustomerName()), "客户姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()), "手机电话不能为空！");
        // 手机号码 phone   格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(customer.getPhone()), "手机号码格式不正确！");
        // 设置默认值
        customer.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer) != 1, "客户更新失败！");
    }

    //客户删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除客户不存在！");
        // 执行删除待办操作，判断受影响的行数
        AssertUtil.isTrue(customerMapper.deleteBatch(ids) != ids.length, "客户删除失败！");
    }

    //查询所有的客户
    public List<Map<String, Object>> queryAllCustomer(Integer orderId) {
        return customerMapper.queryAllCustomer(orderId);
    }

    //通过客户ID查询客户的订单信息
    public CustomerOrderInfo queryCustomerOrderInfo(Integer id){
        return customerMapper.queryCustomerOrderInfo(id);
    }

}
