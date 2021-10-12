package com.city.oa.service;

import com.city.oa.config.FeignConfiguration;
import com.city.oa.dto.CommonResult;
import com.city.oa.model.CommoditySale;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "provider-commodity",configuration = FeignConfiguration.class)
public interface ICommoditySaleService {

    @GetMapping("/commodity/sale/{id}/isExist")
    CommonResult<String> isExistCommoditySale(@PathVariable("id") int id);

    @GetMapping("/commodity/sale/{id}/getStatus")
    CommonResult<Integer> getCommoditySaleStatus(@PathVariable("id") int id);

    @GetMapping("/commodity/sale/{id}/getPrice")
    CommonResult<Double> getCommoditySalePrice(@PathVariable("id") int id);

    @GetMapping("/commodity/sale/status/update")
    CommonResult<String> updateCommoditySaleStatus(@RequestParam("id") int id,@RequestParam("status") int status);

    @GetMapping("/commodity/sale/{id}/detail")
    CommonResult<CommoditySale> getCommoditySaleDetails(@PathVariable("id") int id);
}
