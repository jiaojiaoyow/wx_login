package com.example.demo.Adapter;

import com.alibaba.fastjson.JSON;

import com.example.demo.DTO.SessionDTO;
import com.example.demo.Error.CommonErrorCode;
import com.example.demo.Error.ErrorCodeException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by codedrinker on 2018/11/24.
 */
@Service
public class WechatAdapter {
    private final Logger logger = LoggerFactory.getLogger(WechatAdapter.class);

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;


    public SessionDTO jscode2session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, appid, secret, code))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                SessionDTO sessionDTO = JSON.parseObject(execute.body().string(), SessionDTO.class);
                return sessionDTO;
            } else {
                throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
            }

        } catch (IOException e) {
            throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
        }
    }
}
