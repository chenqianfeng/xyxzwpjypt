package com.city.oa.controller;

import com.city.oa.dto.CommonResult;
import com.city.oa.model.CommoditySale;
import com.city.oa.model.Question;
import com.city.oa.model.Shopper;
import com.city.oa.service.ShopperService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ShopperController {

    @Resource
    private ShopperService service;

    @GetMapping("/logon")
    public CommonResult<String> logon(Integer id, String password, HttpServletRequest request){
        String token = null;
        if (id!=null&&password!=null)
            token = service.logon(id,password,request);
        if (token==null)
            return new CommonResult<>(444,"用户名或密码错误，登录失败",null);
        else if (token.equals("")){
            System.out.println("已登录");
            return new CommonResult<>(401,"你已经登录，请勿重复登录",null);
        }
        System.out.println(token);
        return new CommonResult<>(200,"登录成功",token);
    }

    @GetMapping("/logon/phone")
    public CommonResult<String> logonByPhone(String phone, String password,HttpServletRequest request){
        String token = null;
        System.out.println(phone);
        if (phone!=null&&password!=null)
            token = service.logonByPhone(phone,password,request);
        if (token==null)
            return new CommonResult<>(444,"用户名或密码错误，登录失败",null);
        else if (token.equals(""))
            return new CommonResult<>(401,"你已经登录，请勿重复登录",null);
        return new CommonResult<>(200,"登录成功",token);
    }

    @PostMapping("/register")
    public CommonResult<String> register(Shopper shopper){
        service.register(shopper);
        return new CommonResult<>(200,"注册成功,您的账号是："+shopper.getId()+",您可以使用此账号登录","ok");
    }

    @PostMapping("/shopper/update")
    public CommonResult<Shopper> updateInformation(Shopper shopper,HttpServletRequest request){
        Shopper newShopper = service.update(shopper,request);
        return new CommonResult<>(200,"修改信息成功",newShopper);
    }

    @GetMapping("/shopper/detail")
    public CommonResult<Shopper> detail(HttpServletRequest request){
        return new CommonResult<>(200,"获得个人信息成功",service.getDetail(request));
    }

    @GetMapping("/shopper/logout")
    public CommonResult<String> logout(HttpServletRequest request){
        service.logout(request);
        return new CommonResult<>(200,"注销成功","ok");
    }


    @GetMapping("/retrieve/1")
    public CommonResult<String> retrievePasswordStep1(Integer id,String name,String email,String phone){
        if (service.step1(id,name,email,phone))
            if (service.haveQuestion(id))
                return new CommonResult<>(200, "信息核对成功", "step2");
            else
                return new CommonResult<>(200, "信息核对成功", "step3");
        else
            return new CommonResult<>(404, "信息核对失败", "error");
    }

    @GetMapping("/retrieve/2")
    public CommonResult<String> retrievePasswordStep2(Integer id,String answer){
        if (service.step2(id,answer))
            return new CommonResult<>(200, "密保答案正确", "ok");
        else return new CommonResult<>(404, "密保答案错误", "error");
    }

    @GetMapping("/retrieve/3")
    public CommonResult<String> retrievePasswordStep3(Integer id,String newPassword){
        service.step3(id,newPassword);
        return new CommonResult<>(200, "密码更新成功", "ok");
    }

    @GetMapping("/shopper/question/list")
    public CommonResult<List<Question>> questionList(@RequestParam(defaultValue = "10",required = false) Integer row,
                                                     @RequestParam(defaultValue = "1",required = false)Integer page){
        List<Question> questionList = service.questionList(row,page);
        return new CommonResult<>(200,"密保问题列表查看成功",questionList);
    }

    @GetMapping("/shopper/question/{id}")
    public CommonResult<Question> questionById(@PathVariable Integer id){
        Question question = service.getQuestionById(id);
        return new CommonResult<>(200,"密保问题查看成功",question);
    }

    @GetMapping("/shopper/question/reset")
    public CommonResult<String> resetQuestion(Integer id,Integer questionId,String answer){
        service.resetQuestion(id,questionId,answer);
        return new CommonResult<>(200, "密保问题更新成功", "ok");
    }

    @GetMapping("/shopper/collect/list")
    public CommonResult<List<CommoditySale>> collectList(HttpServletRequest request,
                                                         @RequestParam(defaultValue = "10",required = false) Integer row,
                                                         @RequestParam(defaultValue = "1",required = false)Integer page){
        List<CommoditySale> collectList = service.getCollectList(request,row,page);
        return new CommonResult<>(200,"收藏商品查看成功",collectList);
    }

    @GetMapping("/shopper/collect/add")
    public CommonResult<String> collectAdd(HttpServletRequest request,Integer commodityId){
        service.addCollect(request,commodityId);
        return new CommonResult<>(200,"收藏"+commodityId+"商品成功",null);
    }

    @GetMapping("/shopper/collect/delete")
    public CommonResult<CommoditySale> collectDelete(HttpServletRequest request,Integer commodityId){
        service.deleteCollect(request,commodityId);
        return new CommonResult<>(200,"取消收藏"+commodityId+"商品成功",null);
    }
}

