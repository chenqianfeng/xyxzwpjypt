package com.city.oa.filter;

import cn.hutool.json.JSONUtil;
import com.city.oa.dto.CommonResult;
import com.city.oa.utils.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class LoginFilter  implements Filter {

    private RedisUtil redis;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token = req.getHeader("token");
        redis = getBean(RedisUtil.class,req);
        Long expire = -1l;
        if (token!=null){
            expire = redis.ttl(token);
            redis.expire(token,30);
        }
        if (expire>0)
            chain.doFilter(request,response);
        else {
            String result = JSONUtil.parse(new CommonResult<String>(443, "你还没有登录，请先登录", null)).toString();
            res.setContentType("json/test;charset=utf-8");
            PrintWriter writer = res.getWriter();
            writer.write(result);
        }
    }
    public <T> T getBean(Class<T> clazz, HttpServletRequest request){
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return applicationContext.getBean(clazz);
    }


}