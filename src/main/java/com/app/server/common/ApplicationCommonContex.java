package com.app.server.common;

import com.app.server.entity.User;
import com.app.server.modal.JwtHelper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ApplicationCommonContex {
    private final static Logger LOGGER = LogManager.getLogger(ApplicationCommonContex.class);
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    private HttpServletRequest request;
    private static ConfigurableApplicationContext applicationContextInit;
    private static ApplicationCommonContex INSTANCE;
    private ConcurrentMap<String, User> loginUserMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        loginUserMap.clear();
        applicationContextInit = applicationContext;
    }

    static void initInstance() {
        if (applicationContextInit != null)
            INSTANCE = applicationContextInit.getBean(ApplicationCommonContex.class);
        else
            INSTANCE = new ApplicationCommonContex();
    }

    public static ApplicationCommonContex getInstance() {
        try {
            LOGGER.printf(Level.INFO, "Entry in getInstance()");
            if (INSTANCE == null)
                initInstance();
            LOGGER.printf(Level.INFO, "Exit from getInstance()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in getInstance(),[%1$s]", exception.toString());
        }
        return INSTANCE;
    }

    public void addUserDetails(User user) {
        try {
            LOGGER.printf(Level.INFO, "Entry in addUserDetails()");
            if (loginUserMap != null) {
                loginUserMap.put(user.getUserName(), user);
            }
            LOGGER.printf(Level.INFO, "Exit from addUserDetails()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in addUserDetails(),[%1$s]", exception.toString());
        }
    }

    public int getUserIdByUserName(String userName) {
        try {
            LOGGER.printf(Level.INFO, "Entry in getUserIdByUserName()");
            if (loginUserMap != null && loginUserMap.containsKey(userName.trim())) {
                return loginUserMap.get(userName.trim()).getId();
            }
            LOGGER.printf(Level.INFO, "Exit from getUserIdByUserName()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in getUserIdByUserName(),[%1$s]", exception.toString());
        }
        return -1;
    }

    public boolean removeUserFromContex(String userName) {
        boolean flag = false;
        try {
            LOGGER.printf(Level.INFO, "Entry in removeUserFromContex()");
            if (userName != null && loginUserMap.containsKey(userName.trim())) {
                loginUserMap.remove(userName.trim());
                return !flag;
            }
            LOGGER.printf(Level.INFO, "Exit from removeUserFromContex()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in removeUserFromContex(),[%1$s]", exception.toString());
        }
        return flag;
    }

    public String getLoginUserName() {
        try {
            String requestHeader = request.getHeader("Authorization");
            if (requestHeader != null && requestHeader.startsWith("Bearer")) {
                String token = requestHeader.substring(7);
                return jwtHelper.getUsernameFromToken(token);
            }
        } catch (Exception exception) {

        }
        return "";
    }

    public int getLoginUserId() {
        int uniqueId = -1;
        try {
            LOGGER.printf(Level.INFO, "Entry in getLoginUserId()");
            final String userName = getLoginUserName();
            uniqueId = getUserIdByUserName(userName);
            LOGGER.printf(Level.INFO, "Exit from getLoginUserId()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in getLoginUserId(),[%1$s]", exception.toString());
        }
        return uniqueId;
    }
}
