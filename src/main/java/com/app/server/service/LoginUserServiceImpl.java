package com.app.server.service;

import com.app.server.dao.LoginUserDao;
import com.app.server.entity.User;
import com.app.server.request.UserLoginRequst;
import com.app.server.request.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserServiceImpl implements LoginUserService{
    private static final Logger LOGGER = LogManager.getLogger(LoginUserServiceImpl.class);
    @Autowired
    LoginUserDao loginUserDao;

    @Override
    public UserDetails isValidLoginCredential(UserLoginRequst userLoginReq, HttpServletRequest request) {
        return loginUserDao.isValidLoginCredential(userLoginReq,request);
    }

    @Override
    public boolean saveUser(UserRegisterRequest request) {
        return loginUserDao.saveUser(request);
    }

    @Override
    public boolean isUserNameAlreadyExist(String userName) {
        return false;
    }
}
