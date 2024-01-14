package com.app.server.service;

import com.app.server.request.UserLoginRequst;
import com.app.server.request.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginUserService  {

    UserDetails isValidLoginCredential(UserLoginRequst userLoginRequst, HttpServletRequest request);
    boolean saveUser(UserRegisterRequest request);

    boolean isUserNameAlreadyExist(String userName);

}
