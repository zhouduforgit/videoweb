package com.duyi.video.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.video.dto.ResponseResult;
import com.duyi.video.dto.UserToken;
import com.duyi.video.entity.User;
import com.duyi.video.exception.UserException;
import com.duyi.video.service.UserService;
import com.duyi.video.util.Constant;
import com.duyi.video.util.VideoUtil;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/checkLogin",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult checkLogin(String email,String password){
        ResponseResult responseResult=new ResponseResult();
        User user=userService.login(email,password);
        if(user==null){
            responseResult.setCode(0);
        }else {
            responseResult.setCode(1);
        }
        return responseResult;
    }

    @RequestMapping("/login")
    public String login(String email, String password, String autoLogin,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response
                        ) throws UserException {
        if(!ReUtil.isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",email)){
            throw new UserException("邮箱格式不对");
        }
        if(StrUtil.isEmpty(password) || password.length()<6){
            throw new UserException("密码不规范");
        }
        User user=userService.login(email,password);
        if(user==null){
            throw new UserException("邮箱或密码错误");
        }
        //进入首页，显示邮箱
        session.setAttribute(Constant.SESSION_LOGINUSER,user);

        //自动登录 token(根据是否点击自动登录)
        if(!StrUtil.isEmpty(autoLogin) && "1".equalsIgnoreCase(autoLogin)){
            UserToken userToken=new UserToken(request,user);
            //生成token
            String token=userToken.getToken();
            //把token存入cookie
            Cookie cookie=new Cookie(Constant.COOKIE_LOGIN_TOKEN_NAME,token);

            //设置cookie的参数
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24);
            //cookie放入响应
            response.addCookie(cookie);

            //为了让token和user信息对应，创建UserToken对象，里面存储user和token的信息
            //创建hashMap集合(key---->token,value---->UserToken对象)存所有UserToken对象
            HashMap<String, UserToken> userTokenMap=new HashMap<>();
            userTokenMap.put(token,userToken);
            //创建application对象（因为自动登录的需要重新关闭浏览器，第二次以后访问页面直接登录i），
            // 相当于服务器只要开着就能访问到hashMap对象得到token，不能把hashMap存在session里
            // 比如关闭浏览器session就消失了
            ServletContext app=request.getServletContext();
            // 把map存入application里
            app.setAttribute(Constant.APPLICATION_TOKENS_USERS,userTokenMap);
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session,HttpServletResponse response,HttpServletRequest request){
        //退出登录
        session.removeAttribute(Constant.SESSION_LOGINUSER);
        //删除自动登录的功能，需要2方面
        // 1 删除浏览器的cookie
        Cookie cookie=new Cookie(Constant.COOKIE_LOGIN_TOKEN_NAME,"invalid");
        cookie.setMaxAge(1);
        cookie.setPath("/");
        response.addCookie(cookie);
        // 2 删除服务器的token
        // 服务器的token是上次存在浏览器端的cookie里的值（保存在application里，hashMap里）
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie ck:cookies){
                //request得到浏览器cookie存储的token名字
                String clientTokenName=ck.getName();
                if (Constant.COOKIE_LOGIN_TOKEN_NAME.equalsIgnoreCase(clientTokenName)){
                    //就拿到application里存储map集合
                    //这集合map  key---》String(token) ,value---UserToken对象（包含user信息和token信息）
                    ServletContext appli=request.getServletContext();
                    HashMap<String,UserToken> userTokenMap= (HashMap<String, UserToken>) appli.getAttribute(Constant.APPLICATION_TOKENS_USERS);
                    //删除map中的key---》（就是服务端token），服务器的token就是浏览器cookie的值
                    userTokenMap.remove(ck.getValue());
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping("/register")
    public String register(String email, String password, String vcode,
                           HttpSession session) throws UserException {
        //注册失败，提示错误页面
        if(!ReUtil.isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",email)){
            throw new UserException("邮箱格式不对");
        }
        if(StrUtil.isEmpty(password) || password.length()<6){
            throw new UserException("密码不规范");
        }
        String serverVcode= (String) session.getAttribute(Constant.SESSION_VCODE);
        if( StrUtil.isEmpty(serverVcode)) {
            throw new UserException("请求错误");
        }
        if(!vcode.equalsIgnoreCase(serverVcode)){
            throw new UserException("验证码错误");
        }
        //获取页面上的合法的经过过滤的email和password.
        // 先把email和password赋给一个user对象
        User user=new User();
        user.setEmail(email);
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        //数据库中真实创建的user
        User dbUser=userService.register(user);
        if(dbUser==null){
            //数据库新增用户失败，返回错误页面
            throw new UserException("数据库错误");
        }
        //用session保存用户信息
        session.setAttribute(Constant.SESSION_LOGINUSER,user);
        //注册成功，返回首页
        return "redirect:/";
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public ResponseResult checkEmail(String email){
        ResponseResult responseResult=new ResponseResult();
        //验证email有2个方面   合法性、是否存在
        boolean boo=userService.checkEmail(email);
        if(boo==true){
            //说明改账号没有注册
            responseResult.setCode(1);
            responseResult.setMessage("ok，可以使用改邮箱注册");
            return responseResult;
        }
        responseResult.setCode(-1);
        responseResult.setMessage("fail");
        return responseResult;
    }

    /**
     * 此类的设置用户新密码的操作
     * @param password 页面上输入的新密码
     * @param p 发送重置密码连接(url)的参数的值(=后面)
     * http://www.duyi.com/reSetPwd?p=cnB0PTM1MjYyMTJhMGMxMmM2YzQ4ODYzZmIyNTZiMjkyZmYwJnQ9MTYxMjg1MTI4Nzk1MSZlbWFpbD0xMjM0NTZ6ZEBxcS5jb20=
     * @return
     * @throws UserException
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public String resetPassword(String password,String p) throws UserException {
        String urlParam=Base64.decodeStr(p);
        //解析后 3526212a0c12c6c48863fb256b292ff0_1612851287951_123456zd@qq.com
        //拿出参数的 token 、 时间、 email
        String[] params=urlParam.split("_");
        String token=params[0];
        String time=params[1];
        String email=params[2];

        StringBuilder builder=new StringBuilder();
        builder.append(email);
        builder.append(time);
        builder.append(Constant.SECRET_KEY);
        String resetPasswordToken=DigestUtil.md5Hex(builder.toString());
        if(!resetPasswordToken.equals(token)){
            throw new UserException("连接的token不相等");
        }
        long now=System.currentTimeMillis();
        if ((now-Long.parseLong(time)) > 1000 *20){
            throw new UserException("连接超时");
        }
        //数据库设置新密码
        boolean bb=userService.resetPws2(email,password);
        if(bb=false){
            return "null";
        }
        return "reset password success!";
    }

    /**
     *  此方法主要是跳转到重置密码页面
     * 重置密码的url如下
     * http://www.duyi.com/reSetPwd?p=cnB0PTM1MjYyMTJhMGMxMmM2YzQ4ODYzZmIyNTZiMjkyZmYwJnQ9MTYxMjg1MTI4Nzk1MSZlbWFpbD0xMjM0NTZ6ZEBxcS5jb20=
     * @param p 发送重置密码连接的参数的值(=后面)
     * 需要解析p的值 ：
     *          rpt=3526212a0c12c6c48863fb256b292ff0&t=1612851287951&email=123456zd@qq.com
     *          3526212a0c12c6c48863fb256b292ff0_1612851287951_123456zd@qq.com
     * @return
     */
    @RequestMapping("/reSetPwdPage")
    public String reSetPwdPage(String p, Model model) throws UserException {
        String urlParam=Base64.decodeStr(p);
        //解析后 3526212a0c12c6c48863fb256b292ff0_1612851287951_123456zd@qq.com
        //拿出参数的 token 、 时间、 email
        String[] params=urlParam.split("_");
        String token=params[0];
        String time=params[1];
        String email=params[2];

        StringBuilder builder=new StringBuilder();
        builder.append(email);
        builder.append(time);
        builder.append(Constant.SECRET_KEY);
        String resetPasswordToken=DigestUtil.md5Hex(builder.toString());
        if(!resetPasswordToken.equals(token)){
            throw new UserException("连接的token不相等");
        }
        long now=System.currentTimeMillis();
        if ((now-Long.parseLong(time)) > 1000 *20){
            throw new UserException("连接超时");
        }
        model.addAttribute("p",p);
        return "resetPwd";
    }

    @RequestMapping("/findPwd")
    @ResponseBody
    public String findPwd(String email){
        //发送的邮件是为了发送连接
        //接连URL （重置密码的请求）
        String resetUrl="http://localhost:8080/reSetPwdPage";
        //url需要安全验证，加参数
        //2个方面
        // 1》不能伪造，url中携带token密钥是否与服务器的一样，
        // 2》请求不能过期
        StringBuilder builder=new StringBuilder();
        builder.append(email);
        String nowString=System.currentTimeMillis()+"";
        builder.append(nowString);
        builder.append(Constant.SECRET_KEY);
        String resetPasswordToken=DigestUtil.md5Hex(builder.toString());
        //rpt----》resetPasswordToken  ，t=时间
        //url的参数分为3段  第一段 token  第二段 时间  第三段  邮箱
        //String param="rpt="resetPasswordToken+"&t="+nowString+"&email="+email;
        String param=resetPasswordToken+"_"+nowString+"_"+email;
        String urlParam= Base64.encode(param);
        String url= resetUrl+"?p="+urlParam;
        System.out.println(url);
        return "email send";
    }
}
