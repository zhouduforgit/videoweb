package com.duyi.video.dao;

import com.duyi.video.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    public int insertUser(User user);
    public List<User> findUserByCondition(HashMap<String, Object> map);

    public int updateUser(HashMap<String, Object> map);

    public int updateUser2(HashMap<String, Object> map);
}
