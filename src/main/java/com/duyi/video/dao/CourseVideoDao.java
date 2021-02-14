package com.duyi.video.dao;

import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.CourseVideo;

import java.util.HashMap;
import java.util.List;

public interface CourseVideoDao {
    public int insertCourseVideo(CourseVideo courseVideo);
    public List<CourseVideo> findCourseVideoByCondition(HashMap<String, Object> map);
}
