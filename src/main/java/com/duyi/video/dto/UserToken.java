package com.duyi.video.dto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.video.entity.User;
import com.duyi.video.util.Constant;
import com.duyi.video.util.VideoUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.Console;

/**
 *  此类（UserToken）是存储  user对象和对应的token
 *  token令牌需要很多元素  比如
 *      date时间
 *      userAgent 浏览器请求头信息的User-Agent
 *      ip地址
 *      email ----》user.getEmail()
 */
public class UserToken {
    private User user;

    private String nowString; //date时间
    private String userAgent;
    private String ip;
    private String token; //token的创建需要 ip、date、userAgent、email

    public UserToken(HttpServletRequest request,User user){
        //先给token赋值
        this.nowString= DateUtil.now();
        this.userAgent=request.getHeader("User-Agent");
        this.ip= VideoUtil.getIPAddress(request);
        //再位user赋值
        this.user=user;
        //buildToken这个方法要最后调用，因为token中包含Email，则需要User.getEmail(),所以user对象先创建
        buildToken();


    }
    private void buildToken(){
        StringBuilder builder=new StringBuilder();
        builder.append(user.getEmail());
        builder.append(nowString);
        builder.append(userAgent);
        builder.append(ip);
        builder.append(Constant.SECRET_KEY);

        this.token= (DigestUtil.md5Hex(builder.toString()));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNowString() {
        return nowString;
    }

    public void setNowString(String nowString) {
        this.nowString = nowString;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
