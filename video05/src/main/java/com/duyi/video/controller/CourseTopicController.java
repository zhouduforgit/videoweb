package com.duyi.video.controller;

import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.CourseType;
import com.duyi.video.service.CourseTopicService;
import com.duyi.video.service.CourseTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CourseTopicController {
    @Autowired
    private CourseTopicService courseTopicService;

    @Autowired
    private CourseTypeService courseTypeService;

    @RequestMapping("/topicList/{courseTypeId}")
    public String topicList(@PathVariable Integer courseTypeId, Model model, Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 16);
        PageInfo<CourseTopic> topicList = courseTopicService.getCourseTopicList(courseTypeId);
        model.addAttribute("topicList", topicList);

        List<CourseType> courseTypeList = courseTypeService.getAllType();
        //单独点击某个分类，topic_List页面默认显示全部，没有具体的
        //所以要在导航栏加上所有的课程分类，并且显示选中状态
        model.addAttribute("courseTypeList", courseTypeList);
        model.addAttribute("clickType", courseTypeId);
        return "topic_List";
    }

}
