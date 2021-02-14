package com.duyi.video.dao;

import com.duyi.video.entity.CourseTopic;
import com.duyi.video.entity.ToolsItem;

import java.util.HashMap;
import java.util.List;

public interface ToolsItemDao {
    public int insertToolsItem(ToolsItem toolsItem);
    public List<ToolsItem> findToolsItemByCondition(HashMap<String, Object> map);
}
