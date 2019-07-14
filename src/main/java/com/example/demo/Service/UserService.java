package com.example.demo.Service;

import com.example.demo.Dao.UserMapper;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public interface UserService {

    public User selectByPrimaryKey(String username) ;

    User updateByPrimaryKeySelective(User record);

    int deleteByPrimaryKey(String username);

}
