package com.example.demo.Controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.Adapter.WechatAdapter;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.ResultDTO;
import com.example.demo.DTO.SessionDTO;
import com.example.demo.DTO.TokenDTO;
import com.example.demo.Error.CommonErrorCode;
import com.example.demo.Error.ErrorCodeException;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.Util.DateUtil;
import com.example.demo.Util.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class Wx_Login {

    @Autowired
    private WechatAdapter wechatAdapter;

    @Autowired
    private UserService userService;



    @RequestMapping("api/login")
    public ResultDTO login(@RequestBody LoginDTO loginDTO){
        try{
            //获取openid和session_key
            SessionDTO sessionDTO=wechatAdapter.jscode2session(loginDTO.getCode());
            //检验传过来的数据是否已被篡改
           // DigestUtil.checkDigest(loginDTO.getRawData(),loginDTO.getCode(),loginDTO.getSignature());
            //获取数据
            User user= JSON.parseObject(loginDTO.getRawData(),User.class);
            //生成用户个人的token
            String token= UUID.randomUUID().toString();
            Date date = new Date();
            //将user传到数据库中
            user.setToken(token);
            user.setUid(DateUtil.change_str(date));
            userService.insertSelective(user);
            TokenDTO data = new TokenDTO();
            data.setToken(token);

            return ResultDTO.ok(data);
        }catch (ErrorCodeException e){
            return ResultDTO.fail(e);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }

}
