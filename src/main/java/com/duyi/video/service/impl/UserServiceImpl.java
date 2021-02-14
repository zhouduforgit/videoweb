package com.duyi.video.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.video.dao.UserDao;
import com.duyi.video.entity.User;
import com.duyi.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    public boolean checkEmail(String email) {
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("email",email);
        List<User> userList=userDao.findUserByCondition(hashMap);
        if (userList==null || userList.size()==0){
            return true;  //用户不存在
        }
        return false;
    }

    public User register(User user) {
        user.setVipFlag(0);
        user.setCreateTime(new Date());
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        int nn= userDao.insertUser(user);
        if(nn!=1) {
            return null;
        }
        return user;
    }


    public User login(String email, String password) {
        HashMap<String,Object> hashMap=new HashMap<String, Object>();
        hashMap.put("email",email);
        hashMap.put("password",DigestUtil.md5Hex(password));
        List<User> userList= userDao.findUserByCondition(hashMap);
        if (userList==null || userList.size()==0){
            return null;
        }
        return userList.get(0);
    }

    public boolean resetPws(String email,String password){
        //现根据email查到user
        HashMap<String, Object> map =new HashMap<>();
        map.put("email",email);
        List<User> userList=userDao.findUserByCondition(map);
        if(userList==null && userList.size()==0){
            return false;
        }
        User user=userList.get(0);
        //得到user以后根据id改密码
        HashMap<String, Object> hashMap2=new HashMap<>();
        hashMap2.put("id",user.getId());
        hashMap2.put("password",DigestUtil.md5Hex(password));
        int nnn= userDao.updateUser(hashMap2);
        return nnn == 1?true:false;
    }

    public boolean resetPws2(String email,String password){
        HashMap<String, Object> map =new HashMap<>();
        map.put("email",email);
        map.put("password",DigestUtil.md5Hex(password));
        int nnn= userDao.updateUser2(map);
        return nnn == 1?true:false;
    }
}
