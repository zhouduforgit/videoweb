package com.duyi.video.service.impl;

import com.duyi.video.dao.CourseTopicDao;
import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.CourseVideo;
import com.duyi.video.service.CourseTopicService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class CourseTopicServiceImpl implements CourseTopicService {
    @Autowired
    CourseTopicDao courseTopicDao;
    public PageInfo<CourseTopic> getCourseTopicList(Integer courseTypeId) {
        HashMap<String, Object> map =new HashMap<>();
        map.put("courseTypeId",courseTypeId);
        map.put("flag",1);
        List<CourseTopic> courseTopicList= courseTopicDao.findCourseTopicByCondition(map);
        PageInfo<CourseTopic> pageInfo=new PageInfo<>(courseTopicList);
        return pageInfo;
    }

    public PageInfo<CourseTopic> getCourseTopicList() {
        HashMap<String, Object> map =new HashMap<>();

        map.put("flag",1);
        List<CourseTopic> courseTopicList= courseTopicDao.findCourseTopicByCondition(map);
        PageInfo<CourseTopic> pageInfo=new PageInfo<>(courseTopicList);
        return pageInfo;
    }

    public CourseTopic getCourseTopic(Integer courseTopicId){
        List<Integer> idList=new ArrayList<>();
        idList.add(courseTopicId);
        List<CourseTopic>  list=courseTopicDao.findCourseTopicByIds(idList);
        return list.get(0);
    }
    public PageInfo<CourseTopic> searchTopic(String keyword) {
        HashMap<String, Object> map =new HashMap<>();
        map.put("topicName",keyword);
        map.put("flag",1);

        List<CourseTopic> list=  courseTopicDao.findCourseTopicByCondition(map);
        PageInfo<CourseTopic> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
