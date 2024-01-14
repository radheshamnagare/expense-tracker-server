package com.app.server.dao;

import com.app.server.entity.User;
import com.app.server.request.UserLoginRequst;
import com.app.server.request.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginUserDao {

    UserDetails isValidLoginCredential(UserLoginRequst userLoginReq, HttpServletRequest request);

    boolean saveUser(UserRegisterRequest request);

    User findUserDetailsByUserName(String userName);

    boolean isUserNameExist(String userName);
}
