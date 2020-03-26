package com.liudl.community.service;

import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.User;
import com.liudl.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by TwistedFate on 2020/1/27 13:28
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, userExample1);
        }
    }

    public boolean createForSignIn(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneNumberEqualTo(user.getPhoneNumber());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl("http://pic2.zhimg.com/50/v2-fb824dbb6578831f7b5d92accdae753a_hd.jpg");
            userMapper.insert(user);
            return true;
        }else {
            //返回已注册
            return false;
        }
    }

    /**
     * 检查用户手机号是否存在,密码是否正确,返回检查信息
     * @param user
     * @return
     */
    public String checkUserInfo(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneNumberEqualTo(user.getPhoneNumber());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            return "phomeNumberNotExists";
        }else {
            User tempUser = users.get(0);
            if (tempUser.getPassword().equals(user.getPassword())){
                return "ok";
            }else {
                return "passwordWrong";
            }
        }
    }

    /**
     * 更新普通用户token并登录
     * @param user
     */
    public void loginCommonUser(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneNumberEqualTo(user.getPhoneNumber());
        User updateUser = new User();
        updateUser.setGmtModified(System.currentTimeMillis());
        updateUser.setToken(user.getToken());
        userMapper.updateByExampleSelective(updateUser, userExample);
    }
}
