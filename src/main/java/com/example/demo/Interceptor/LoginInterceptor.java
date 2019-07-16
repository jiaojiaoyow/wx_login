package com.example.demo.Interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demo.DTO.ResultDTO;
import com.example.demo.Error.CommonErrorCode;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by codedrinker on 2018/12/2.
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<Object,Object> stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String url = request.getRequestURI();

        //允许login地址无需登陆即可访问
        if(url.indexOf("/api/login")>=0){
            return true;
        }

        //请求之前，验证通过返回true，验证失败返回false
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            makeFail(response);
            return false;
        }else{
            ValueOperations operations=stringRedisTemplate.opsForValue();
            User user=(User)operations.get(token);
            if (user == null) {
                makeFail(response);
                return false;
            }
        }

        return true;
    }

    private void makeFail(HttpServletResponse response) {
        ResultDTO resultDTO = ResultDTO.fail(CommonErrorCode.NO_USER);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(JSON.toJSONString(resultDTO));
            out.close();
        } catch (Exception e) {
            System.out.println("LoginInterceptor preHandle"+ e.toString());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
