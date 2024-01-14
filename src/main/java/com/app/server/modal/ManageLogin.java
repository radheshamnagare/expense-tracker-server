package com.app.server.modal;

import com.app.server.bean.FailRespose;
import com.app.server.bean.SystemError;
import com.app.server.common.*;
import com.app.server.request.UserLoginRequst;
import com.app.server.request.UserRegisterRequest;
import com.app.server.response.DefaultResponse;
import com.app.server.response.LoginResponse;
import com.app.server.service.LoginUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ManageLogin {
    private final static Logger LOGGER = LogManager.getLogger(ManageLogin.class);
    private LoginUserService loginUserService;
    private JwtHelper jwtHelper;
    HttpServletRequest request;

    private String authToken;

    void resetToekn(){
        this.authToken = "";
    }


    private boolean isValidLoginRequest(UserLoginRequst userLoginRequst, List<FailRespose> fail){
        FailRespose failRespose;
        try{
            if(CommonValidator.isEmpty(userLoginRequst.getUserId())){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_USER_ID,""));
                fail.add(failRespose);
                return false;
            }else if(CommonValidator.isEmpty(userLoginRequst.getPassword())){
                failRespose  = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_PASSWORD);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_PASSWORD,""));
                fail.add(failRespose);
                return false;
            } else if (CommonValidator.isEmpty(userLoginRequst.getDomain())) {
                failRespose  = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_DOMAIN);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_DOMAIN,""));
                fail.add(failRespose);
                return false;
            } else  {
                UserDetails userDetails = loginUserService.isValidLoginCredential(userLoginRequst,request);
                if(userDetails==null){
                    failRespose  = new FailRespose();
                    failRespose.setId(ErrorConstatnt.DESC_CREDENTIAL);
                    failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_CREDENTIAL,userLoginRequst.getUserId()));
                    fail.add(failRespose);
                    return false;
                }else{
                    authToken = jwtHelper.generateToken(userDetails);
                    if(CommonValidator.isEmpty(authToken)){
                        failRespose  = new FailRespose();
                        failRespose.setId(ErrorConstatnt.DESC_CREDENTIAL);
                        failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_CREDENTIAL,userLoginRequst.getUserId()));
                        fail.add(failRespose);
                        return false;
                    }
                }
            }
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isValidLoginRequest",ex.toString());
        }
        return true;
    }

    public  LoginResponse getLoginResponse(String success, SystemError error, List<FailRespose> fail, String apiKey, String token,String authToken){
        LoginResponse response = new LoginResponse();
        try{
            LOGGER.printf(Level.INFO,"Entry in getLoginResponse()");
            response.setSuccess(success);
            response.setErrorCode(error.getErrorCode());
            response.setErrorStatus(error.getErrorStatus());
            response.setErrorDescription(error.getErrorDescription());
            response.setFail(fail);
            response.setApiKey(apiKey);
            response.setToken(token);
            response.setJwtToken(authToken);
            response.setUserName(jwtHelper.getUsernameFromToken(authToken));
            LOGGER.printf(Level.INFO,"Exit from getLoginResponse()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getLoginResponse(),[%1$s]", exception.toString());
        }
        return response;
    }
    public LoginResponse manageLogin(UserLoginRequst userLoginRequest){
        LoginResponse response;
        String apiKey = CommonService.getSessionId(request);
        String token =  CommonService.getToken();
        List<FailRespose> fail  = new ArrayList<>();
        try{
            LOGGER.printf(Level.INFO,"Entry in manageLogin");
            resetToekn();
            if(!CommonService.isValidSessionToken(apiKey,token)){
                SystemError error =  CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_SESSION_TOKEN,"");
                response = getLoginResponse(String.valueOf(1),error,fail,apiKey,token,authToken);
            } else if(isValidLoginRequest(userLoginRequest,fail)){
                SystemError  error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                response = getLoginResponse(String.valueOf(1),error,fail,apiKey,token,authToken);
            }else{
               SystemError error =  CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL,"","");
               response = getLoginResponse(String.valueOf(0),error,fail,apiKey,token,authToken);
            }
            LOGGER.printf(Level.INFO,"Exit from manageLogin");
        }catch (Exception ex){
            SystemError error =  CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = getLoginResponse(String.valueOf(0),error,fail,apiKey,token,authToken);
            LOGGER.printf(Level.ERROR,"Exception in manageLogin",ex.toString());
        }
        return response;
    }

    private boolean doExpiredSession(){
       String userName =  ApplicationCommonContex.getInstance().getLoginUserName();
       return ApplicationCommonContex.getInstance().removeUserFromContex(userName);
    }
    public DefaultResponse manageLogOut(){
        DefaultResponse response;
        try{
            LOGGER.printf(Level.INFO,"Entry in manageLogOut");
            if(doExpiredSession()){
                SystemError  error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                response = CommonService.getDefaultResponse(String.valueOf(1),error,null,"","");
            }else{
                SystemError error =  CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL,"","");
                response = CommonService.getDefaultResponse(String.valueOf(0),error,null,"","");
            }
            LOGGER.printf(Level.INFO,"Exit from manageLogOut");
        }catch (Exception ex){
            SystemError error =  CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = CommonService.getDefaultResponse(String.valueOf(1),error,null,"","");
            LOGGER.printf(Level.ERROR,"Exception in manageLogOut,[%1$s]",ex.toString());
        }
        return response;
    }

    private boolean isValidUserInfo(String firstName,String lastName,String emaiId,List<FailRespose> fail){
       FailRespose failRespose;
        try{
            LOGGER.printf(Level.INFO,"Entry in checkUserInfo");
            if(CommonValidator.isEmpty(firstName)){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_FIRST_NAME);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_FIRST_NAME,""));
                fail.add(failRespose);
                return false;
            }else if(CommonValidator.isEmpty(lastName)){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_LASTNAME);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_LASTNAME,""));
                fail.add(failRespose);
                return false;
            } else if (!CommonValidator.isValidEmailId(emaiId)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_EMAIL_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_EMAIL_ID,emaiId));
                fail.add(failRespose);
                return false;
            }
            LOGGER.printf(Level.INFO,"Exit from checkUserInfo");
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception checkUserInfo,[%1$s]",ex.toString());
        }
        return true;
    }

    private boolean isValidUserName(String userName,List<FailRespose> fail){
        FailRespose failRespose;
        try{
            LOGGER.printf(Level.INFO,"Entry in isValidUserName");
            if(CommonValidator.isEmpty(userName)){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_USER_ID,""));
                fail.add(failRespose);
                return false;
            }else if(userName.length()<3){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_USER_ID,userName));
                return false;
            }else if(!loginUserService.isUserNameAlreadyExist(userName)){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_DUPLICATE,ErrorConstatnt.DESC_USER_ID,userName));
                return false;
            }
            LOGGER.printf(Level.INFO,"Exit in isValidUserName");
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isValidUserName");
        }
        return true;
    }
    private boolean isValidPassword(String password,List<FailRespose> fail){
       FailRespose failRespose;
        try{
            if(CommonValidator.isEmpty(password)){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_PASSWORD);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_PASSWORD,""));
                fail.add(failRespose);
                return false;
            }else if(password.length()<8){
               failRespose = new FailRespose();
               failRespose.setId(ErrorConstatnt.DESC_PASSWORD);
               failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_PASSWORD,password));
               fail.add(failRespose);
               return false;
            }
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Entry in isValidPassword,[%1$s]",ex.toString());
        }
        return true;
    }

    private boolean isValidDomain(String domain,List<FailRespose> fail){
        FailRespose failRespose;
        try{
            LOGGER.printf(Level.INFO,"Entry in isValidDomain");
            if(CommonValidator.isEmpty(domain)){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_DOMAIN);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_DOMAIN,""));
                fail.add(failRespose);
                return false;
            }
            LOGGER.printf(Level.INFO,"Exit in isValidDomain");
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Entry in isValidDomain,[%1$s]",ex.toString());
        }
        return true;
    }
    private boolean isValidRequestForRegister(UserRegisterRequest userRegisterRequest,List<FailRespose> fail){
        try{
            if(!isValidUserInfo(userRegisterRequest.getFirstName(), userRegisterRequest.getLastName(), userRegisterRequest.getEmailAddress(), fail)){
                return false;
            }else if(!isValidUserName(userRegisterRequest.getUserName(),fail)){
                return false;
            }else if(!isValidPassword(userRegisterRequest.getPassword(), fail)){
                return false;
            }else if(!isValidDomain(userRegisterRequest.getDomain() ,fail)){
                return false;
            }
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isValidRequestForRegister,[%1$s]",ex.toString());
        }
        return true;
    }
    public DefaultResponse manageRegisterUser(UserRegisterRequest userRegisterRequest){
        DefaultResponse response;
        List<FailRespose> fail = new ArrayList<>();
        try{
            if(isValidRequestForRegister(userRegisterRequest,fail)){
               boolean registrationComplate =  loginUserService.saveUser(userRegisterRequest);
               if(registrationComplate){
                   SystemError  error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                   response = CommonService.getDefaultResponse(String.valueOf(1),error,fail,"","");
               }else{
                   SystemError  error = CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL,"","");
                   response = CommonService.getDefaultResponse(String.valueOf(0),error,fail,"","");
               }
            }else{
                SystemError  error = CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL,"","");
                error.setErrorDescription(fail.get(0).getReason());
                response = CommonService.getDefaultResponse(String.valueOf(0),error,fail,"","");
            }
        }catch (Exception ex){
            SystemError  error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = CommonService.getDefaultResponse(String.valueOf(0),error,fail,"","");
            LOGGER.printf(Level.ERROR,"Exception in manageRegisterUser,[%1$s]",ex.toString());
        }
        return response;
    }
}
