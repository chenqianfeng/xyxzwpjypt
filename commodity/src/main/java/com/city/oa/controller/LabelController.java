package com.city.oa.controller;

import com.city.oa.dto.CommonResult;
import com.city.oa.model.Label;
import com.city.oa.server.ILabelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Resource
    ILabelService service;

    @GetMapping("/add")
    CommonResult<String> addLabel(String key){
        service.addLabel(key);
        return new CommonResult<>(200,"增加标签成功","ok");
    }

    @GetMapping("/{id}/delete")
    CommonResult<String> deleteLabelById(@PathVariable Integer id){
        service.deleteLabelById(id);
        return new CommonResult<>(200,"删除标签成功","ok");
    }

    @GetMapping("/list/get")
    CommonResult<List<Label>> selectLabelByPage(@RequestParam(defaultValue = "10",required = false) Integer row,@RequestParam(defaultValue = "1",required = false)Integer page){
        List<Label> list = service.selectLabelByPage(row, page);
        if(list.size()>0)
            return new CommonResult<>(200,"分页查找标签成功",list);
        return new CommonResult<>(404,"分页查找标签失败", null);
    }

    @GetMapping("/{id}/get")
    CommonResult<Label> selectLabelById(@PathVariable Integer id){
        return new CommonResult<>(200,"查找标签成功",service.selectLabelById(id));
    }

    @GetMapping("/key/get")
    CommonResult<Label> selectLabelByKey(String key){
        return new CommonResult<>(200,"查找标签成功",service.selectLabelByKey(key));
    }

    @GetMapping("/commodity/add")
    CommonResult<String> addLabelForCommodity(@RequestParam Integer cId,@RequestParam(value="lId[]")Integer[] lId){
        service.addLabelForCommodity(cId,lId);
        return new CommonResult<>(200,"增加成功","ok");
    }

    @GetMapping("/commodity/update")
    CommonResult<String> deleteLabelForCommodity(@RequestParam Integer cId,@RequestParam(value="lId[]")Integer[] lId){
        service.modifyLabelForCommodity(cId,lId);
        return new CommonResult<>(200,"删除成功","ok");
    }

    @GetMapping("/commodity/select")
    CommonResult<List<Label>> selectLabelByCommodityId(int cId){
        List<Label> labels = service.selectLabelByCommodityId(cId);
        return new CommonResult<>(200,"查找成功",labels);
    }

}
