package com.city.oa.dao;

import com.city.oa.model.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPhotoDao {
    void addPhotoForCommodity(@Param("id") Integer id, @Param("fileName")String fileName, @Param("fileType")String fileType);
    void deletePhotoForCommodity(Integer id);
    Photo getPhotoById(Integer id);
}
