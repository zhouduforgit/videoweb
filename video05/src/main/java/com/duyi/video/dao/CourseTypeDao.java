package com.duyi.video.dao;

import com.duyi.video.entity.CourseType;

import java.util.HashMap;
import java.util.List;

public interface CourseTypeDao {
    public int insertCourseType(CourseType courseType);
    public List<CourseType> findCourseTypeByCondition(HashMap<String, Object> map);

    public List<CourseType>  findCourseTypeAll();

}
