package com.duyi.video.controller;

import cn.hutool.core.util.RandomUtil;
import com.duyi.video.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;

@Controller
public class VcodeController {
    @RequestMapping("/vcode")
    public void createCode(HttpServletResponse response, HttpSession session){

        BufferedImage image=new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
        Graphics2D gd=image.createGraphics(); //gd是画笔
        gd.setColor(Color.white);
        gd.drawRect(0,0,80,30);
        Font font=new Font("宋体",Font.BOLD,25);
        gd.setFont(font);

        StringBuilder builder=new StringBuilder();
        for(int i=0;i<4;i++){
            int ri=RandomUtil.randomInt(10);
            char v=Constant.VCODE_ARRAY[ri];
            //红绿蓝随机取值
            gd.setColor(new Color(
                    RandomUtil.randomInt(255),
                    RandomUtil.randomInt(255),
                    RandomUtil.randomInt(255)
            ));
            //画验证码
            gd.drawString(""+v,(i+1)*15,25);
            builder.append(v);
        }

        //把验证码保存再session里面
        session.setAttribute(Constant.SESSION_VCODE,builder.toString());

        //以图片的形式返回
        response.setContentType("image/jpeg");
        //设置图片不缓存
        response.setDateHeader("Expires",-1);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        //输出图片
        try {
            ServletOutputStream ous = response.getOutputStream();
            ImageIO.write(image,"jpeg",ous);
            ous.flush();
            ous.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
