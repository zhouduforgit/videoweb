package com.duyi.video.dao;

import com.duyi.video.entity.ToolsItem;
import com.duyi.video.entity.ToolsType;

import java.util.HashMap;
import java.util.List;

public interface ToolsTypeDao {
    public int insertToolsType(ToolsType toolsType);
    public List<ToolsType> findToolsTypeByCondition(HashMap<String, Object> map);
}
