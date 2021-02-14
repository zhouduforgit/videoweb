package com.duyi.video.dao;

import com.duyi.video.entity.CourseTopic;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;

import java.util.HashMap;
import java.util.List;

public interface CourseTopicDao {
    public int insertCourseTopic(CourseTopic courseTopic);
    public List<CourseTopic> findCourseTopicByCondition(HashMap<String, Object> map);
    public List<CourseTopic> findCourseTopicByIds(List<Integer> idList);
}
