package com.duyi.video.service;

import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.CourseVideo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CourseTopicService {
    //根据课程分类的id选择对应课程专题
    //public List<CourseTopic> getCourseTopics(Integer courseTypeId);
    public PageInfo<CourseTopic> getCourseTopicList(Integer courseTypeId);
    public PageInfo<CourseTopic> getCourseTopicList();

    public CourseTopic getCourseTopic(Integer courseTopicId);

    //根据用户的关键词 搜索课程专题视频
    public PageInfo<CourseTopic> searchTopic(String keyword);
}
