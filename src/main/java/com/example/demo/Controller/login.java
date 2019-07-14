package com.example.demo.Controller;


import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/")
public class login {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("toLogin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @RequestMapping("login")
    public ModelAndView login(User user){
        /**
         * 为了方便，直接非注解生成键值对的方法写在这里
         */
        //生成一个建，并设置他的过期时间
        ValueOperations operations=redisTemplate.opsForValue();
        operations.set("1","2");
        //这里是设置键的有效日期
        redisTemplate.expire("1", 123L, TimeUnit.SECONDS);
        System.out.println(operations.get("1"));

        //其他部分
        User user1=userService.selectByPrimaryKey(user.getUsername());
        if(user.getPassword().equals(user1.getPassword())){
            return new ModelAndView("success");
        }
        else {
            return new ModelAndView("fail");
        }
    }

    @RequestMapping("edit")
    public ModelAndView edit(User user){
        User myuser=userService.updateByPrimaryKeySelective(user);
        if(myuser.getUsername()!=null){
            return new ModelAndView("success");
        }
        else {
            return new ModelAndView("fail");
        }
    }

    @RequestMapping("logout")
    @CacheEvict(value = "user",key = "#user.username", beforeInvocation = false)
    public ModelAndView logout(User user){
        User myUser=userService.selectByPrimaryKey(user.getUsername());
        if(user.getPassword().equals(myUser.getPassword())){
            userService.deleteByPrimaryKey(user.getUsername());
            return new ModelAndView("success");
        }
        else {
            return new ModelAndView("fail");
        }
    }
}



