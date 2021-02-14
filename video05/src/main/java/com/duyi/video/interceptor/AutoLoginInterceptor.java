package com.duyi.video.interceptor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.video.dto.UserToken;
import com.duyi.video.util.Constant;
import com.duyi.video.util.VideoUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  自动登录拦截器
 *  若浏览器的cookie里的token和服务器的token一样就自动登录
 */
public class AutoLoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //获取cookie
        Cookie[] cookies =request.getCookies();
        //没有cookie直接跳过拦截
        if (cookies==null){
            return true;
        }
        //有cookie没有登录就可以进行自动登录
        for(Cookie cookie:cookies){
            String clientTokenName=cookie.getName();
            //存在相同的cookie，
            if(Constant.COOKIE_LOGIN_TOKEN_NAME.equalsIgnoreCase(clientTokenName)) {
                String clientToken = cookie.getValue();
                //用户在访问网站时cookie中的token是合法的就自动登录
                //拿到存储所有token信息和对应USerToken信息的map,map存储在Application里
                ServletContext app = request.getServletContext();
                HashMap<String, UserToken> userTokenMap = (HashMap<String, UserToken>) app.getAttribute(Constant.APPLICATION_TOKENS_USERS);
                if (userTokenMap == null) {
                    //没有登录数据，直接跳过拦截
                    return true;
                }
                UserToken userToken = userTokenMap.get(clientToken);
                if (userToken == null) {
                    return true;
                }

                //服务器有用户的token信息
                //验证是否是同一个浏览去器请求的token
                // token的创建需要 ip、date、userAgent、email
                StringBuilder builder=new StringBuilder();
                builder.append(userToken.getUser().getEmail());
                builder.append(userToken.getNowString());
                builder.append(VideoUtil.getIPAddress(request));
                builder.append(request.getHeader("User-Agent"));
                builder.append(Constant.SECRET_KEY);
                String newServerToken= DigestUtil.md5Hex(builder.toString());

                if (! newServerToken.equalsIgnoreCase(clientToken)){
                    return true;
                }
                //验证是否过期
                String loginNowString = userToken.getNowString();
                //用户登录的时间戳
                long lnt = DateUtil.parseDateTime(loginNowString).getTime();
                long now = System.currentTimeMillis();
                long timeout = now - lnt;
                if (timeout > 60 * 60 * 24 * 1000) {
                    return true;
                }
                //token合法，实现自动登录
                HttpSession session = request.getSession(true);
                session.setAttribute(Constant.SESSION_LOGINUSER, userToken.getUser());
                break;
            }
        }
        return true;
    }
}




















