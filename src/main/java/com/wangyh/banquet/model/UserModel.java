package com.wangyh.banquet.model;

/**
 * 用户登录后模型-设置Cookie
 */
public class UserModel {

    private String userName;
    private String trueName;
    private String userIdStr; //加密后的用户ID

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }
}
