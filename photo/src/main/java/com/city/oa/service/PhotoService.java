package com.city.oa.service;

import com.city.oa.dao.IPhotoDao;
import com.city.oa.model.Photo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class PhotoService {


    @Resource
    IPhotoDao dao;

    public String upload(int id, MultipartFile file) {
        String filePath = System.getProperty("user.dir") + "\\photo\\src\\main\\resources\\photo\\";
        String fileType = file.getContentType();
        String original = file.getOriginalFilename();
        String lastname = original.substring(original.lastIndexOf("."));
        String fileName = filePath + id + "-" + new Date().getTime()+"."+lastname;
        File dest = new File(fileName);
        dao.addPhotoForCommodity(id, fileName, fileType);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功！";
    }

    public void delete(int id) {
        Photo photo = dao.getPhotoById(id);
        if(photo==null) return;
        String fileName = photo.getPhotoName();
        File file = new File(fileName);
        file.delete();
        dao.deletePhotoForCommodity(id);
    }


    public Photo getPhotoById(Integer id) {
        Photo photo = dao.getPhotoById(id);
        if(photo==null) return null;
        File file = new File(photo.getPhotoName());
        try {
            FileInputStream stream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);
            stream.close();
            photo.setPhoto(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photo;
    }
}
