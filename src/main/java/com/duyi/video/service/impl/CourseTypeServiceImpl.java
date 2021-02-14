package com.duyi.video.service.impl;

import com.duyi.video.dao.CourseTypeDao;
import com.duyi.video.entity.CourseType;
import com.duyi.video.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    CourseTypeDao courseTypeDao;
    public List<CourseType> getAllType() {
        List<CourseType> typeList=  courseTypeDao.findCourseTypeAll();
        return typeList;
    }
}
