package com.duyi.test;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.TolerantMap;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.video.dao.*;
import com.duyi.video.entity.*;
import com.duyi.video.service.CourseTopicService;
import com.duyi.video.util.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})

public class DaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BannerDao bannerDao;

    @Autowired
    private CourseTypeDao courseTypeDao;

    @Autowired
    private CourseTopicDao courseTopicDao;

    @Autowired
    private CourseVideoDao courseVideoDao;

    @Autowired
    private ToolsItemDao toolsItemDao;

    @Autowired
    private ToolsTypeDao toolsTypeDao;

    @Autowired
    private CourseTopicService courseTopicService;
    @Test
    public void testUser(){
        User user=new User();
        user.setCreateTime(new Date());
        user.setEmail("email");
        user.setId(1);
        user.setPassword("123");
        user.setVipFlag(0);
        int num= userDao.insertUser(user);
        System.out.println(num);
    }

    @Test
    public void testUserSelect(){
        HashMap<String,Object> hashMap=new HashMap<>();
        //hashMap.put("email","1234567@qq.com");
        hashMap.put("password",DigestUtil.md5Hex("123456zd"));
        List<User> user=userDao.findUserByCondition(hashMap);
        System.out.println(user);
    }

    @Test
    public void testUserUpdate(){
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("password","534534534");
        hashMap.put("id",1);
        int num=userDao.updateUser(hashMap);
        System.out.println(num);
    }

    //=====================================================
    @Test
    public void testBannerInsert(){
        Banner banner=new Banner();
        banner.setCreateTime(new Date());
        banner.setFlag(1);
        banner.setTargetUrl("target");
        banner.setType(1);
        int ss=bannerDao.insertBanner(banner);
        System.out.println(ss);
    }

    @Test
    public void testBannerSelect(){
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("target_url","target");
        List<Banner> banners=bannerDao.findBannerByCondition(hashMap);
        System.out.println(banners);
    }

    //===================课程类别=================
    @Test
    public void testCourseTypeInsert(){
        CourseType courseType=new CourseType();
        courseType.setFlag(1);
        courseType.setName("lalal");
        courseType.setCreateTime(new Date());
        int sss= courseTypeDao.insertCourseType(courseType);
        System.out.println(sss);
    }
    @Test
    public void testCourseTypeSelect(){
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("name","lalal");
        List<CourseType> courseTypes=courseTypeDao.findCourseTypeByCondition(hashMap);
        System.out.println(courseTypes);
    }

    //======================================================
    @Test
    public void testCourseTopicInsert(){
        for(int i=13;i<=44;i++) {
            CourseTopic courseTopic = new CourseTopic();
            courseTopic.setTopicName("redis"+i);
            courseTopic.setViews(193+i);
            courseTopic.setVipFlag(1);
            courseTopic.setCourseTypeId(2);
            courseTopic.setCreateTime(new Date());
            courseTopic.setFlag(1);
            courseTopic.setImgUrl("http://localhost:8080/static/img/456.jpg");
            courseTopic.setPptUrl("www.baidu.com");

            System.out.println(courseTopicDao.insertCourseTopic(courseTopic));
        }
    }

    @Test
    public void testCourseTopicSelect(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("views",123);
        List<CourseTopic> topics=courseTopicDao.findCourseTopicByCondition(map);
        System.out.println(topics);
    }

    //=======================================================
    @Test
    public void testCourseVideoInsert(){
        for (int i = 1; i < 22; i++) {
            CourseVideo courseVideo = new CourseVideo();
            courseVideo.setVideoName("Spring" + i);
            courseVideo.setFreeFlag(RandomUtil.randomInt(0, 2));
            courseVideo.setCourseTopicId(i);
            courseVideo.setCreateTime(new Date());
            courseVideo.setFlag(1);
            courseVideo.setPlayUrl("//player.bilibili.com/player.html?aid=712169146&bvid=BV1iD4y1o7dD&cid=238287362&page=1");

            int code = courseVideoDao.insertCourseVideo(courseVideo);
            System.out.println(code);
        }
    }
    @Test
    public void testPage(){
        //Page{count=true, pageNum=1, pageSize=4, startRow=0, endRow=4, total=12, pages=3, reasonable=false, pageSizeZero=false}
        // [CourseTopic{id=1, topicName='java1', views=124, vipFlag=1, courseTypeId=1, topicIntro='null', flag=1, pptUrl='www.baidu.com', createTime=Tue Feb 09 18:42:38 GMT+08:00 2021},
        // CourseTopic{id=2, topicName='java2', views=125, vipFlag=1, courseTypeId=1, topicIntro='null', flag=1, pptUrl='www.baidu.com', createTime=Tue Feb 09 18:42:38 GMT+08:00 2021},
        // CourseTopic{id=3, topicName='java3', views=126, vipFlag=1, courseTypeId=1, topicIntro='null', flag=1, pptUrl='www.baidu.com', createTime=Tue Feb 09 18:42:38 GMT+08:00 2021},
        // CourseTopic{id=4, topicName='java4', views=127, vipFlag=1, courseTypeId=1, topicIntro='null', flag=1, pptUrl='www.baidu.com', createTime=Tue Feb 09 18:42:38 GMT+08:00 2021}]
        PageHelper.startPage(1,4);
        PageInfo<CourseTopic> pageInfo= courseTopicService.getCourseTopicList(1);
        System.out.println(pageInfo.getList());
    }

    @Test
    public void testCourseVideoSelect(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("flag",2);
        List<CourseVideo> courseVideos= courseVideoDao.findCourseVideoByCondition(map);
        System.out.println(courseVideos);
    }

    //============================================
    @Test
    public void testToolsItemInsert(){
        ToolsItem toolsItem=new ToolsItem();
        toolsItem.setName("ndsdsa");
        toolsItem.setFlag(1);
        toolsItem.setImgUrl("treqwq");
        System.out.println(toolsItemDao.insertToolsItem(toolsItem));
    }
    @Test
    public void testToolsItemSelect(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("name","ndsdsa");
        List<ToolsItem> tt=toolsItemDao.findToolsItemByCondition(map);
        System.out.println(tt);
    }

    //=================================================
    @Test
    public void testToolsTypeInsert(){
        ToolsType toolsType=new ToolsType();
        toolsType.setName("ndsdsa");
        toolsType.setFlag(1);
        toolsType.setCreateTime(new Date());
        System.out.println(toolsTypeDao.insertToolsType(toolsType));
    }
    @Test
    public void testToolsTypeSelect(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("name","ndsdsa");
        List<ToolsType> tt=toolsTypeDao.findToolsTypeByCondition(map);
        System.out.println(tt);
    }

    @Test
    public void t16(){
        //发送的邮件是为了发送连接
        //接连URL （重置密码的请求）
        String email="123456zd@qq.com";
        String resetUrl="http://www.duyi.com/reSetPwd";
        //url需要安全验证，加参数
        //2个方面
        // 1》不能伪造，url中携带token密钥是否与服务器的一样，
        // 2》请求不能过期
        StringBuilder builder=new StringBuilder();
        builder.append(email);
        String nowString=""+System.currentTimeMillis();
        builder.append(nowString);
        builder.append(Constant.SECRET_KEY);
        String resetPasswordToken=DigestUtil.md5Hex(builder.toString());
        String param="rpt="+resetPasswordToken+"&t="+nowString+"&email="+email;
        //原始参数
        System.out.println(resetUrl+"?"+param);
        //加密后的参数
        String urlParam= Base64.encode(param);
        String url= resetUrl+"?p="+urlParam;
        System.out.println(url);
        System.out.println(Base64.decodeStr(urlParam));
    }
}
