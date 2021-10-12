package com.city.oa.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.city.oa.dto.CommonResult;
import com.city.oa.model.CommodityFind;
import com.city.oa.model.CommoditySale;
import com.city.oa.model.Words;
import com.city.oa.server.ICommodityFindService;
import com.city.oa.server.ICommoditySaleServer;
import com.city.oa.server.IPhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Resource
    ICommoditySaleServer saleServer;

    @Resource
    ICommodityFindService findService;

    @Resource
    IPhotoService photoServer;


    @PostMapping("/sale/add")
    CommonResult<String> addCommoditySale(HttpServletRequest request,
                                          CommoditySale commoditySale,
                                          @RequestParam(value = "file", required = false) MultipartFile file) {
        int id = saleServer.addCommoditySale(commoditySale, request);
        if (file != null && !file.isEmpty())
            photoServer.addPhotoForId(id, file);
        return new CommonResult<>(200, String.valueOf(id), "ok");
    }

    @PostMapping("/sale/update")
    CommonResult<String> updateCommoditySale(CommoditySale commoditySale,
                                             @RequestParam(value = "file", required = false) MultipartFile file) {
        if (commoditySale.getId() != null) {
            saleServer.updateCommoditySale(commoditySale);
            if (file != null && !file.isEmpty()) {
                photoServer.modifyPhotoForId(commoditySale.getId(), file);
            }
            return new CommonResult<>(200, "商品更新成功！", null);
        }
        return new CommonResult<>(400, "商品更新失败！", null);
    }

    @GetMapping("/sale/{id}/isExist")
    CommonResult<String> isExistCommoditySale(@PathVariable("id") int id, @RequestParam("request") HttpServletRequest request) {
        int isExist = saleServer.isExistCommoditySale(id);
        if (isExist == 0)
            return new CommonResult<>(404, "商品不存在！", null);
        return new CommonResult<>(200, "商品存在！", null);
    }

    @GetMapping("/sale/{id}/getStatus")
    CommonResult<Integer> getCommoditySaleStatus(@PathVariable("id") int id) {
        Integer status = saleServer.getCommoditySaleStatus(id);
        return new CommonResult<>(200, "ok", status);
    }

    @GetMapping("/sale/{id}/getPrice")
    CommonResult<Double> getCommoditySalePrice(@PathVariable("id") int id) {
        Double price = saleServer.getPriceById(id);
        return new CommonResult<>(200, "获取商品价格成功！", price);
    }

    @GetMapping("/sale/{id}/delete")
    CommonResult<String> deleteCommoditySale(@PathVariable("id") int id) {
        saleServer.deleteCommoditySale(id);
        return new CommonResult<>(200, "商品删除成功！", null);
    }


    @GetMapping("/sale/status/update")
    CommonResult<String> updateCommoditySaleStatus(int id, int status) {
        saleServer.modifyCommoditySaleStatus(id, status);
        return new CommonResult<>(200, "商品状态修改成功！", null);
    }

    @GetMapping("/sale/recommend/to")
    CommonResult<String> updateCommoditySaleRecommend(int id) {
        saleServer.modifyCommoditySaleToRecommend(id);
        return new CommonResult<>(200, "设为推荐商品成功！", null);
    }

    @GetMapping("/sale/recommend/cancel")
    CommonResult<String> updateCommoditySaleCancelRecommend(int id) {
        saleServer.modifyCommoditySaleCancelRecommend(id);
        return new CommonResult<>(200, "取消推荐商品成功！", null);
    }

    @GetMapping("/sale/{id}/detail")
    CommonResult<CommoditySale> getCommoditySaleDetails(@PathVariable int id) {
        CommoditySale commoditySale = saleServer.getCommoditySaleDetails(id);
        System.out.println(commoditySale.toString());
        return new CommonResult<>(200, "查找成功", commoditySale);
    }

    @GetMapping("/sale/view/list")
    CommonResult<List<CommoditySale>> getCommodityRecommendByLabelWithPage(@RequestParam(defaultValue = "10", required = false) Integer row,
                                                                           @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getCommodityRecommendByLabelWithPage(row, page);
        int count = saleServer.getCountByCondition(null, null, null, null, 1, null, null);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/sale/label/view")
    CommonResult<List<CommoditySale>> getListCommoditySaleByViewCountAndLabelWithPage(int label,
                                                                                      @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                                      @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getListCommoditySaleByViewCountAndLabelWithPage(label, row, page);
        return new CommonResult<>(200, "查找成功", list);
    }

    @GetMapping("/sale/shopper")
    CommonResult<List<CommoditySale>> getCommoditySaleByShopperWithPage(HttpServletRequest request,
                                                                        @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                        @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getListCommoditySaleByShopperWithPage(request, row, page);
        int count = saleServer.getCountByCondition(null, null, request, 1, null, null, null);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/sale/label/list")
    CommonResult<List<CommoditySale>> getCommoditySaleByLabelWithPage(int label,
                                                                      @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                      @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getCommoditySaleByLabelWithPage(label, row, page);
        int count = saleServer.getCountByCondition(label, null, null, 1, null, null, null);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/sale/status/list")
    CommonResult<List<CommoditySale>> getCommoditySaleByStatusWithPage(@RequestParam(defaultValue = "1", required = false) int status,
                                                                       @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                       @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getCommoditySaleByStatusWithPage(status, row, page);
        int count = saleServer.getCountByCondition(null, null, null, status, null, null, null);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/sale/sold/list")
    CommonResult<List<CommoditySale>> getCommoditySoldByStatusWithPage(HttpServletRequest request,
                                                                       @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                       @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getCommoditySoldByStatusWithPage(request, row, page);
        int count = saleServer.getCountByCondition(null, null, request, 3, null, null, null);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/sale/condition")
    CommonResult<List<CommoditySale>> getCommoditySaleByKeyWithPage(@RequestParam(required = false) String key,
                                                                    @RequestParam(defaultValue = "0", required = false) double min,
                                                                    @RequestParam(defaultValue = "0", required = false) double max,
                                                                    @RequestParam(defaultValue = "0", required = false) int label,
                                                                    @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                    @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommoditySale> list = saleServer.getCommoditySaleByConditionWithPage(key, min, max, label, row, page);
        int count = saleServer.getCountByCondition(label, key, null, null, null, min, max);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/sale/words/add")
    CommonResult<String> addWordsForCommodity(Words words, HttpServletRequest request) {
        saleServer.addWordsForCommodity(words, request);
        return new CommonResult<>(200, "增加留言成功", "留言id:" + words.getId());
    }

    @GetMapping("/sale/words/{id}/delete")
    CommonResult<String> deleteWordsForCommodity(@PathVariable Integer id, HttpServletRequest request) {
        if (saleServer.deleteWordsForCommodity(id, request))
            return new CommonResult<>(200, "刪除留言成功", "ok");
        return new CommonResult<>(412, "刪除留言失败", "error");
    }

    @GetMapping("/sale/words/shopper")
    CommonResult<List<Words>> getWordsByShopper(HttpServletRequest request) {
        List<Words> list = saleServer.getWordsByShopper(request);
        return new CommonResult<>(200, "查找成功", list);
    }

    @GetMapping("/sale/words/{id}/commodity")
    CommonResult<List<Words>> getWordsByCommodity(@PathVariable int id) {
        List<Words> list = saleServer.getWordsByCommodity(id);
        return new CommonResult<>(200, "查找成功", list);
    }

    //求购商品的接口

    @PostMapping("/find/add")
    CommonResult<String> addCommodityFind(CommodityFind commodityFind, HttpServletRequest request) {
        int id = findService.addCommodityFind(commodityFind, request);
        return new CommonResult<>(200, String.valueOf(id), "ok");
    }

    @GetMapping("/find/{id}/delete")
    CommonResult<String> deleteCommodityFind(@PathVariable int id, HttpServletRequest request) {
        if (findService.deleteCommodityFind(id, request))
            return new CommonResult<>(200, "求购商品删除成功！", "ok");
        return new CommonResult<>(413, "求购商品删除失败！", "ok");
    }

    @GetMapping("/find/update")
    CommonResult<String> updateCommodityFind(CommodityFind commodityFind) {
        findService.updateCommodityFind(commodityFind);
        return new CommonResult<>(200, "求购商品更新成功！", "ok");
    }

    @GetMapping("/find/status/update")
    CommonResult<String> updateCommodityFindStatus(int id, int status) {
        findService.modifyCommodityFindStatus(id, status);
        return new CommonResult<>(200, "求购商品状态修改成功！", null);
    }

    @GetMapping("/find/{id}/detail")
    CommonResult<CommodityFind> getCommodityFindDetails(@PathVariable int id) {
        CommodityFind c = findService.getCommodityFindDetails(id);
        return new CommonResult<>(200, "ok！", c);
    }

    @GetMapping("/find/myFind")
    CommonResult<List<CommodityFind>> getCommodityFindByShopperWithPage(HttpServletRequest request,
                                                                        @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                        @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommodityFind> list = findService.getListCommodityFindByShopperWithPage(request, row, page);
        int count = findService.getCountByShopper(request);
        return new CommonResult<List<CommodityFind>>(200, String.valueOf(count), list);
    }

    @GetMapping("/find/condition")
    CommonResult<List<CommodityFind>> getCommodityFindByConditionWithPage(@RequestParam(defaultValue = "", required = false) String name,
                                                                          @RequestParam(defaultValue = "0", required = false) Integer shopperId,
                                                                          @RequestParam(defaultValue = "1", required = false) Integer status,
                                                                          @RequestParam(defaultValue = "0", required = false) Double minPrice,
                                                                          @RequestParam(defaultValue = "0", required = false) Double maxPrice,
                                                                          @RequestParam(defaultValue = "10", required = false) Integer row,
                                                                          @RequestParam(defaultValue = "1", required = false) Integer page) {
        List<CommodityFind> list = findService.getCommodityFindByConditionWithPage(name, shopperId, status, minPrice, maxPrice, row, page);
        int count = findService.getCountByCondition(name, shopperId, status, minPrice, maxPrice);
        return new CommonResult<>(200, String.valueOf(count), list);
    }

    @GetMapping("/find/count")
    CommonResult<Integer> getCommodityFindByShopperWithPage(@RequestParam String name,
                                                            @RequestParam(defaultValue = "0", required = false) Integer shopperId,
                                                            @RequestParam(defaultValue = "1", required = false) Integer status,
                                                            @RequestParam(defaultValue = "0", required = false) Double minPrice,
                                                            @RequestParam(defaultValue = "0", required = false) Double maxPrice) {
        int count = findService.getCountByCondition(name, shopperId, status, minPrice, maxPrice);
        return new CommonResult<>(200, String.valueOf(count), null);
    }

}