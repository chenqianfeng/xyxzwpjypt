package com.city.oa.filter;

import cn.hutool.json.JSONUtil;
import com.city.oa.dto.CommonResult;
import com.city.oa.model.Shopper;
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
public class AdminFilter implements Filter {

    private RedisUtil redis;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token = req.getHeader("token");
        redis = getBean(RedisUtil.class,req);
        Shopper shopper = JSONUtil.parseObj(redis.get(req.getHeader("token"))).toBean(Shopper.class);
        if (shopper.getPower()>1)
            chain.doFilter(request,response);
        else
        {
            String result = JSONUtil.parse(new CommonResult<>(409, "你没有管理员权限", null)).toString();
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
