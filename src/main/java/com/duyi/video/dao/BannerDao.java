package com.duyi.video.dao;

import com.duyi.video.entity.Banner;

import java.util.HashMap;
import java.util.List;

public interface BannerDao {
    public int insertBanner(Banner banner);
    public List<Banner> findBannerByCondition(HashMap<String, Object> map);

}
