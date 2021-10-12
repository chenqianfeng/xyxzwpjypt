package com.city.oa.server;


import com.city.oa.dto.CommonResult;
import com.city.oa.model.Photo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient("provider-photo")
public interface IPhotoService {

    @PostMapping(value = "/photo/add/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult<String> addPhotoForId(@PathVariable("id")int id, @RequestPart MultipartFile file) ;

    @GetMapping("/photo/delete/{id}")
    CommonResult<String> deletePhotoForId(@PathVariable("id")int id) ;

    @PostMapping(value = "/photo/modify/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    CommonResult<String> modifyPhotoForId(@PathVariable("id") Integer id,@RequestPart MultipartFile file);

    @GetMapping("/photo/get/{id}")
    CommonResult<Photo> getPhotoById(@PathVariable("id") Integer id);
}
