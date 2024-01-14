package com.app.server.service;

import com.app.server.common.ApplicationCommonContex;
import com.app.server.dao.UserRepo;
import com.app.server.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in loadUserByUsername()");
            user = userRepo.findByUserName(username);
            if (user == null)
                throw new UsernameNotFoundException("user not found!");
            else
                ApplicationCommonContex.getInstance().addUserDetails(user);
            LOGGER.printf(Level.INFO, "Exit from loadUserByUsername()");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in loadUserByUsername,[%1$s]", ex.toString());
        }
        return new UserDetailsImpl(user);
    }
}
