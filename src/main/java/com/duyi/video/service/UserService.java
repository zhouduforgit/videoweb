package com.duyi.video.service;

import com.duyi.video.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public boolean checkEmail(String email);

    public User register(User user);

    public User login(String email,String password);

    public boolean resetPws(String email,String password);

    public boolean  resetPws2(String email,String password);
}
