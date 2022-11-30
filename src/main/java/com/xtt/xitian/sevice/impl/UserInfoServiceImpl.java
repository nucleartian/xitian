package com.xtt.xitian.sevice.impl;

import com.xtt.xitian.dao.UserInfoDao;
import com.xtt.xitian.model.UserInfo;
import com.xtt.xitian.sevice.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}