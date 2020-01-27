package com.liudl.community.service;

import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by TwistedFate on 2020/1/27 13:28
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新
            user.setGmtModified(System.currentTimeMillis());
            user.setId(dbUser.getId());
            userMapper.update(user);
        }
    }
}
