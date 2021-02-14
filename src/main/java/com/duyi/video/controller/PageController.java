package com.duyi.video.controller;

import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.CourseType;
import com.duyi.video.entity.CourseVideo;
import com.duyi.video.service.CourseTopicService;
import com.duyi.video.service.CourseTypeService;
import com.duyi.video.service.CourseVideoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private  CourseTopicService courseTopicService;

    @Autowired
    private CourseTypeService courseTypeService;

    @Autowired
    private CourseVideoService courseVideoService;
    @RequestMapping(value = "/")
    public String indexPage(Model model){
        System.out.println("======");
        //显示首页前要对课程分类下的课程专题分页显示
        //首页一共有2个分类
        //  常用框架  courseTypeID=1 、 数据库  courseTypeId=3
        PageHelper.startPage(1,4);
        PageInfo<CourseTopic>  pageType1_topics=courseTopicService.getCourseTopicList(1);

        PageHelper.startPage(1,4);
        PageInfo<CourseTopic> pageType3_topics=courseTopicService.getCourseTopicList(3);
        //return "WEB-INF/jsp/index.jsp";
        model.addAttribute("pageType1_topics",pageType1_topics);
        model.addAttribute("pageType3_topics",pageType3_topics);
        model.addAttribute("clickNav",1);
        return "index";
    }

    @RequestMapping(value = "/forgetPage")
    public String forgetPage(){

        return "forget";
    }

    //跳转到课程专题页面
    @RequestMapping(value = "/topicList")
    public String topicList(Model model){
        //默认进入课程专题的页面的时候显示所有类型的课程
        //页面的导航栏---》全部 java 数据库  分布式
        PageHelper.startPage(1,16);
        PageInfo<CourseTopic> topicList=courseTopicService.getCourseTopicList();
        model.addAttribute("topicList",topicList);
        //获取 课程分类
        List<CourseType> courseTypeList =courseTypeService.getAllType();
        model.addAttribute("courseTypeList",courseTypeList);
        model.addAttribute("clickType",0);
        model.addAttribute("clickNac",2);
        return "topic_List";
    }

    //指定专题id，视频id播放视频
    @RequestMapping("/topic/{topicId}/{videoId}")
    public String videoPlay(@PathVariable Integer topicId, @PathVariable Integer videoId,Model model){
        //根据courseTopicId 获取courseTopic对象，包括（id,topic-name,views,vip_flag等等）
        CourseTopic courseTopic=courseTopicService.getCourseTopic(topicId);

        //根据courseTopicId获取对应的视频--->CourseVideo
        List<CourseVideo> courseVideoList= courseVideoService.getCourseVideo(topicId);

        //当跳转到视频页面时，点击哪一集就是那一集,courseVideo使用户点击播放的视频
        CourseVideo courseVideo=null;
        for(CourseVideo video:courseVideoList){
            if(video.getId()==videoId){
                courseVideo=video;
            }
        }
        model.addAttribute("courseVideo",courseVideo);
        model.addAttribute("courseTopic",courseTopic);
        model.addAttribute("courseVideoList",courseVideoList);
        return "course_video";
    }

    /**
     *  此方法 ，指定topicId，显示跳转到专题视频的页面
     * @param topicId 专题id ---> courseTopicId
     * @param model
     * @return
     */
    @RequestMapping("/topic/{topicId}")
    public String topicPage(@PathVariable Integer topicId, Model model){
        //根据courseTopicId 获取courseTopic对象，包括（id,topic-name,views,vip_flag等等）
        CourseTopic courseTopic=courseTopicService.getCourseTopic(topicId);

        //根据courseTopicId获取对应的视频--->CourseVideo
        List<CourseVideo> courseVideoList= courseVideoService.getCourseVideo(topicId);

        //当跳转到视频页面时，默认播放第一集
        CourseVideo courseVideo=courseVideoList.get(0);

        model.addAttribute("courseVideo",courseVideo);
        model.addAttribute("courseTopic",courseTopic);
        model.addAttribute("courseVideoList",courseVideoList);
        return "course_video";
    }

}

