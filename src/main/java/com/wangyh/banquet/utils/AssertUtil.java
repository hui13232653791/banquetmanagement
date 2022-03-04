package com.wangyh.banquet.utils;

import com.wangyh.banquet.exceptions.ParamsException;

public class AssertUtil {

    public  static void isTrue(Boolean flag,String msg){
        if(flag){
            throw new ParamsException(msg);
        }
    }

}
