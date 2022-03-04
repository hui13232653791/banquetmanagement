package com.wangyh.banquet.dao;

import com.wangyh.banquet.base.BaseMapper;
import com.wangyh.banquet.vo.User;

/**
 * 用户数据操作层
 */
public interface UserMapper extends BaseMapper<User, Integer> {

    //通过用户名查询用户记录，返回用户对象
    User queryUserByName(String userName);

}