package com.wangyh.banquet.service;

import com.wangyh.banquet.base.BaseService;
import com.wangyh.banquet.dao.UserMapper;
import com.wangyh.banquet.model.UserModel;
import com.wangyh.banquet.utils.*;
import com.wangyh.banquet.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户服务层
 */
@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    //用户登录
    public UserModel userLogin(String userName, String userPwd) {
        //1、参数判断，判断用户名和密码非空
        checkLoginParams(userName, userPwd);
        //2、调用数据访问层，通过用户名查询用户记录，返回用户对象
        User user = userMapper.queryUserByName(userName);
        //3、判断用户对象是否为空
        AssertUtil.isTrue(user == null, "用户名不存在");
        //4、判断密码是否正确
        checkUserPwd(userPwd, user.getUserPwd());
        //5、返回构建用户对象
        return buildUserInfo(user);
    }

    //构建需要返回客户端的用户对象
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        //设置加密的用户ID
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    //密码判断
    private void checkUserPwd(String userPwd, String pwd) {
        //将客户端传递的密码加密
        userPwd = Md5Util.encode(userPwd);
        //判断密码是否相等
        AssertUtil.isTrue(!userPwd.equals(pwd), "用户密码不正确！");
    }

    //用户登录参数判断
    private void checkLoginParams(String userName, String userPwd) {
        //验证用户姓名
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        //验证用户密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空！");
    }

    //用户修改密码
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassWord(Integer userId, String ordPwd, String newPwd, String repeatPwd) {
        //1、通过用户ID查询用户记录，返回用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        //判断用户是否存在
        AssertUtil.isTrue(null == user, "待更新记录不存在！");
        //2、密码参数校验
        checkPasswordParams(user, ordPwd, newPwd, repeatPwd);
        //3、设置用户新密码
        user.setUserPwd(Md5Util.encode(newPwd));
        //4、执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "修改密码失败！");
    }

    //修改密码的参数校验
    private void checkPasswordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        //判断原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空！");
        //判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)), "原始密码不正确！");
        //判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        //判断新密码是否与原始密码一致 （不允许新密码与原始密码）
        AssertUtil.isTrue(oldPwd.equals(newPwd), "新密码不能与原始密码相同！");
        //判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd), "确认密码不能为空！");
        //判断确认密码是否与新密码一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "确认密码与新密码不一致！");
    }

    //用户基本资料更新
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSetting(User user) {
        // 判断用户ID是否为空，且数据存在
        AssertUtil.isTrue(null == user.getId(), "待更新记录不存在！");
        // 通过id查询数据
        User temp = userMapper.selectByPrimaryKey(user.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");
        // 参数校验
        //判断用户手机号非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "手机号不能为空！");
        // 判断用户手机号格式
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机号格式不正确！");
        // 设置默认值
        user.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "用户基本资料更新失败！");
    }

    /**
     * 添加用户
     *  1、参数校验
     *      用户名userName     非空，唯一性
     *      邮箱email          非空
     *      手机号phone        非空，格式正确
     *  2、设置参数的默认值
     *      createDate        系统当前时间
     *      updateDate        系统当前时间
     *      默认密码           123456 -> md5加密
     *  3、执行添加操作，判断受影响的行数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        //1、参数校验
        checkUserParams(user.getUserName(), user.getEmail(), user.getPhone(), null);
        //2、设置参数的默认值
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        //3、执行添加操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1, "用户添加失败！");
    }

    /** 参数校验
     *      用户名userName     非空，唯一性
     *      邮箱email          非空
     *      手机号phone        非空，格式正确
     */
    private void checkUserParams(String userName, String email, String phone, Integer userId) {
        //判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空！");
        //判断用户名的唯一性
        //通过用户名查询用户对象
        User temp = userMapper.queryUserByName(userName);
        //如果用户对象为空，则表示用户名可用；如果用户对象不为空，则表示用户名不可用
        // 如果是添加操作，数据库中无数据，只要通过名称查到数据，则表示用户名被占用
        // 如果是修改操作，数据库中有对应的记录，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
        // 如果用户名存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(userId)), "用户名已存在，请重新输入！");
        //判断用户邮箱非空
        AssertUtil.isTrue(StringUtils.isBlank(email), "用户邮箱不能为空！");
        //判断用户手机号非空
        AssertUtil.isTrue(StringUtils.isBlank(phone), "用户手机号不能为空！");
        //判断用户手机号格式
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号格式不正确！");
    }

    /**
     * 更新用户
     *  1、参数校验
     *      判断用户ID是否为空，且数据存在
     *      用户名userName     非空，唯一性
     *      邮箱email          非空
     *      手机号phone        非空，格式正确
     *  2、设置参数的默认值
     *      updateDate        系统当前时间
     *  3、执行更新操作，判断受影响的行数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        // 判断用户ID是否为空，且数据存在
        AssertUtil.isTrue(null == user.getId(), "待更新记录不存在！");
        // 通过id查询数据
        User temp = userMapper.selectByPrimaryKey(user.getId());
        // 判断是否存在
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");
        // 参数校验
        checkUserParams(user.getUserName(), user.getEmail(),user.getPhone(),user.getId());
        // 设置默认值
        user.setUpdateDate(new Date());
        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "用户更新失败！");
    }

    //用户删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除记录不存在！");
        // 执行删除操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.deleteBatch(ids) != ids.length, "用户删除失败！");
    }

}
