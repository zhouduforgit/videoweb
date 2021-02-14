package com.duyi.video.service.impl;

import com.duyi.video.dao.CourseVideoDao;
import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.CourseVideo;
import com.duyi.video.service.CourseVideoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CourseVideoServiceImpl implements CourseVideoService {

    @Autowired
    CourseVideoDao courseVideoDao;
    public List<CourseVideo> getCourseVideo(Integer courseTopicId) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("courseTopicId",courseTopicId);
        map.put("flag",1);
        List<CourseVideo>  courseVideoList=courseVideoDao.findCourseVideoByCondition(map);
        return courseVideoList;
    }


}
