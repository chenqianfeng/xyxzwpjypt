package com.city.oa.controller;

import com.city.oa.dto.CommonResult;
import com.city.oa.model.Photo;
import com.city.oa.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@RequestMapping("/photo")
public class PhotoController {


    @Resource
    PhotoService service;

    @PostMapping("/add/{id}")
    public CommonResult<String> addPhotoForId(@PathVariable("id")int id, MultipartFile file) {
        if (file==null||file.isEmpty()) {
            return new CommonResult(444,"上传失败，请选择文件",null);
        }
        String date = service.upload(id, file);
        return new CommonResult(200,"上传成功",date);
    }
    @PostMapping("/modify/{id}")
    CommonResult<String> modifyPhotoForId(@PathVariable("id") int id,MultipartFile file){
        System.out.println(file.getSize());
        if (file==null||file.isEmpty()) {
            return new CommonResult(444,"上传失败，请选择文件",null);
        }
        service.delete(id);
        String date = service.upload(id, file);
        return new CommonResult(200,"修改成功",date);
    }
    @GetMapping("/delete/{id}")
    public CommonResult<String> deletePhotoForId(@PathVariable("id")int id) {
        service.delete(id);
        return new CommonResult(200,"删除成功","ok");
    }


    @GetMapping("/get/{id}")
    void getPhotoById(@PathVariable("id") Integer id, HttpServletResponse response)throws Exception{
        Photo photo = service.getPhotoById(id);
        if(photo!=null&&photo.getPhotoType()!=null) {
            response.setContentType(photo.getPhotoType());
            OutputStream out=response.getOutputStream();
            out.write(photo.getPhoto());
            out.flush();
            out.close();
        }

    }

}
