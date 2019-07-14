package com.example.demo.Service.Impl;

import com.example.demo.Dao.UserMapper;
import com.example.demo.Service.UserService;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service("UserService")
//注解来统一指定value的值,这时候你在方法注解中就不用指定value值，优先级小于方法中的value。
//@CacheConfig(cacheNames = {"user"})
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    //value指定缓存的名字,此注解在已有相同key的情况下，会直接调用缓存
    @Cacheable(value = "user")
    public User selectByPrimaryKey(String username) {
        User myuser=this.userMapper.selectByPrimaryKey(username);
        System.out.println(myuser);
        return myuser;
    }


    //由于是更新同一个键值对，注意返回的需要跟查询同样为User对象，不要像平时一样返回int对象,可以使用result，也可以直接使用参数，eg：record.username
    @Override
    @CachePut(value = "user", key="#result.username")
    public User updateByPrimaryKeySelective(User record) {
        this.userMapper.updateByPrimaryKeySelective(record);
        return this.userMapper.selectByPrimaryKey(record.getUsername());
    }

    @Override
    public int deleteByPrimaryKey(String username) {
        return this.userMapper.deleteByPrimaryKey(username);
    }


}
