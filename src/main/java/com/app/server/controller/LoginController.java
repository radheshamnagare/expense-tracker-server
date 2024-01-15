package com.app.server.controller;

import com.app.server.modal.JwtHelper;
import com.app.server.modal.ManageLogin;
import com.app.server.request.ExpenseTrackerListReq;
import com.app.server.request.UserLoginRequst;
import com.app.server.request.UserRegisterRequest;
import com.app.server.response.DefaultResponse;
import com.app.server.response.LoginResponse;
import com.app.server.service.LoginUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    final static Logger LOGGER = LogManager.getLogger(LoginController.class);
    @Autowired
    HttpServletRequest request;
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    ConfigurableApplicationContext contex;
    @Autowired
    AuthenticationManager manager;
    @Autowired
    JwtHelper jwtHelper;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginRequst userLoginReq) {
        LoginResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in loginUser");
            doAuthenticate(userLoginReq.getUserId(), userLoginReq.getPassword());
            ManageLogin manageLogin = contex.getBean(ManageLogin.class);
            manageLogin.setLoginUserService(loginUserService);
            manageLogin.setJwtHelper(jwtHelper);
            manageLogin.setRequest(request);
            response = manageLogin.manageLogin(userLoginReq);
            LOGGER.printf(Level.INFO, "Exit from loginUser");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in loginUser,[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultResponse> logOut() {
        DefaultResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in logOut");
            ManageLogin manageLogin = contex.getBean(ManageLogin.class);
            manageLogin.setLoginUserService(loginUserService);
            manageLogin.setJwtHelper(jwtHelper);
            manageLogin.setRequest(request);
            response = manageLogin.manageLogOut();
            LOGGER.printf(Level.INFO, "Exit from logOut");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in logOut,[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        DefaultResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in registerUser");
            ManageLogin manageLogin = contex.getBean(ManageLogin.class);
            manageLogin.setLoginUserService(loginUserService);
            manageLogin.setJwtHelper(jwtHelper);
            manageLogin.setRequest(request);
            response = manageLogin.manageRegisterUser(userRegisterRequest);
            LOGGER.printf(Level.INFO, "Exit from registerUser");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in registerUser,[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @RequestMapping(value = "/sayhello",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public String test(@RequestBody ExpenseTrackerListReq expenseTrackerListReq){

        return "Hello User!";
    }

}
