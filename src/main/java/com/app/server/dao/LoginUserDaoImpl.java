package com.app.server.dao;

import com.app.server.common.ConstantPool;
import com.app.server.entity.User;
import com.app.server.request.UserLoginRequst;
import com.app.server.request.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Repository
public class LoginUserDaoImpl implements LoginUserDao{
    private static final Logger LOGGER = LogManager.getLogger(LoginUserDaoImpl.class);
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public UserDetails isValidLoginCredential(UserLoginRequst userLoginReq, HttpServletRequest request) {
        UserDetails userDetails=null;
        try {
            userDetails = userDetailsService.loadUserByUsername(userLoginReq.getUserId());
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in isvalidLoginCredential", ex.toString());
        }
        return userDetails;
    }

    private String getEncryptedPassword(String password) {
        String encryptedPassword = "";
        try {
            encryptedPassword = passwordEncoder.encode(password.trim());
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in getEncryptedPassword,[%1$s]", ex.toString());
        }
        return encryptedPassword;
    }

    private User setUser(UserRegisterRequest request) {
        User user = new User();
        try {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUserName(request.getUserName());
            user.setPassword(getEncryptedPassword(request.getPassword()));
            user.setDomain(request.getDomain());
            user.setEnable(true);
            user.setAccountExpire(false);
            user.setAccountLock(false);
            user.setEmailId(request.getEmailAddress());
        } catch (Exception ex) {
            LOGGER.printf(Level.INFO, "Exception setUser,[%1$s]", ex.toString());
        }
        return user;
    }

    @Override
    public boolean saveUser(UserRegisterRequest request) {
        try {
            User user = setUser(request);
            if (userRepo.save(user) != null) {
                return true;
            }
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in saveUser ,[%1$s]", ex.toString());
        }
        return false;
    }

    @Override
    public User findUserDetailsByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public boolean isUserNameExist(String userName) {
        return !userRepo.findUserName(userName).isEmpty();
    }

}
