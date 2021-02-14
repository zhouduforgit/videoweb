package com.duyi.video.service;

import com.duyi.video.entity.CourseVideo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CourseVideoService {
    public List<CourseVideo> getCourseVideo(Integer courseTopicId);


}
