package com.jacky.lighttalker;

/**
 * created by jiangshiyu
 * date: 2020/1/14
 */
public class UserService implements IUserService {
    @Override
    public String search(int hashCode) {
        return "User:" + hashCode;
    }
}
