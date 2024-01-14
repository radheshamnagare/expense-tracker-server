package com.app.server.modal;

import com.app.server.bean.ExpenseTrackerBean;
import com.app.server.bean.FailRespose;
import com.app.server.bean.SystemError;
import com.app.server.common.*;
import com.app.server.request.ExpenseTrackerDetailsRequest;
import com.app.server.request.ExpenseTrackerListReq;
import com.app.server.response.DefaultResponse;
import com.app.server.response.ExpenseTrackerDetailsResponse;
import com.app.server.service.ExpenseTrackerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ManageExpenseTracker {
    private final static Logger LOGGER = LogManager.getLogger(ManageExpenseTracker.class);
    private HttpServletRequest request;
    private ExpenseTrackerService expenseTrackerService;

    private ExpenseTrackerDetailsResponse getExpenseTrackerDetailsResponse(List<ExpenseTrackerBean> expenseTrackerBeanList, List<FailRespose> fail, SystemError error, String apiKey, String token){
        ExpenseTrackerDetailsResponse expenseTrackerDetailsResponse = new ExpenseTrackerDetailsResponse();
        try{
            LOGGER.printf(Level.INFO,"Entry in getExpenseTrackerDetailsResponse()");
            expenseTrackerDetailsResponse.setExpenseTrackerBeanList(expenseTrackerBeanList);
            expenseTrackerDetailsResponse.setErrorCode(error.getErrorCode());
            expenseTrackerDetailsResponse.setErrorStatus(error.getErrorStatus());
            expenseTrackerDetailsResponse.setErrorDescription(error.getErrorDescription());
            expenseTrackerDetailsResponse.setFail(fail);
            expenseTrackerDetailsResponse.setApiKey(apiKey);
            expenseTrackerDetailsResponse.setToken(token);
            LOGGER.printf(Level.INFO,"Exit from getExpenseTrackerDetailsResponse()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getExpenseTrackerDetailsResponse(),[%1$s]", exception.toString());
        }
        return expenseTrackerDetailsResponse;
    }

    public ExpenseTrackerDetailsResponse manageExpenseTrackerDetails(ExpenseTrackerListReq expenseTrackerListReq){
        ExpenseTrackerDetailsResponse response =null;
        String apiKey = CommonService.getSessionId(request);
        String token = CommonService.getToken();
        SystemError error;
        try{
            LOGGER.printf(Level.INFO,"Entry in manageExpenseTrackerDetails()");
            if(!CommonService.isValidSessionToken(apiKey,token)){
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID, ErrorConstatnt.DESC_SESSION_TOKEN,apiKey);
                response = getExpenseTrackerDetailsResponse(null,null,error,apiKey,token);
            }else{
                int userId = ApplicationCommonContex.getInstance().getLoginUserId();
                expenseTrackerListReq.setUserId(userId);
                List<ExpenseTrackerBean> expenseTrackerDetails =
                        expenseTrackerService.expenseTrackerDetails(expenseTrackerListReq);
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                response = getExpenseTrackerDetailsResponse(expenseTrackerDetails,null,error,apiKey,token);
            }
            LOGGER.printf(Level.INFO,"Exit from manageExpenseTrackerDetails()");
        }catch (Exception exception){
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = getExpenseTrackerDetailsResponse(null,null,error,apiKey,token);
            LOGGER.printf(Level.ERROR,"Exception in manageExpenseTrackerDetails(),[%1$s]", exception.toString());
        }
        return response;
    }

    private boolean isValidActionDelete(String action){
        return ConstantPool.ACTION_DELETE.equals(action);
    }

    private boolean isValidActionUpdateDelete(String action){
        return (ConstantPool.ACTION_DELETE.equals(action) ||  ConstantPool.ACTION_UPDATE.equals(action));
    }

    private boolean isValidId(String id,List<FailRespose> fail){
        try{
             if(expenseTrackerService.isValidExpenceTrackerId(Integer.parseInt(id))>0)
                 return true;
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in isValidId,[%1$s]",exception.toString());
        }
        return false;
    }
    private  FailRespose getFailRespose(String id,String errorCode,String val0,String val1){
        FailRespose failRespose=new FailRespose();
        try{
            failRespose.setId(id);
            failRespose.setReason(CommonService.getSystemError(errorCode,val0,val1).getErrorDescription());
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getFailRespose(),[%1$s]",exception.toString());
        }
        return failRespose;
    }
    private boolean isValidExpenseTrackerRequest(List<ExpenseTrackerBean> req,List<ExpenseTrackerBean> validBean,List<FailRespose> fail,String action){
        try{
            LOGGER.printf(Level.INFO,"Entry in isValidExpenseTrackerRequest()");
            if(CommonValidator.isEmpty(req)){
                FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_BEAN,ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_BEAN,ErrorConstatnt.DESC_NO_DATE_IN_REQUEST);
                fail.add(failRespose);
            }else{
                req.stream().forEach((bean)->{
                    if(isValidActionUpdateDelete(action) && !isValidId(String.valueOf(bean.getId()),fail)) {
                    }else if(CommonValidator.isEmpty(bean.getName())){
                        FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_EXPENSE_NAME,ConstantPool.ERROR_CODE_REQUIRED,"","");
                        fail.add(failRespose);
                    } else if (!isValidActionDelete(action) && CommonValidator.isEmpty(bean.getDescription())) {
                        FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_EXPENSE_DESCRIPTION,ConstantPool.ERROR_CODE_REQUIRED,"","");
                        fail.add(failRespose);
                    }else if (!isValidActionDelete(action) && !CommonValidator.isNumeric(bean.getAmount())){
                        FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_EXPENSE_AMOUNT,ConstantPool.ERROR_CODE_REQUIRED,"","");
                        fail.add(failRespose);
                    }else {
                        validBean.add(bean);
                    }
                });
            }
            LOGGER.printf(Level.INFO,"Exit from isValidExpenseTrackerRequest()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in isValidExpenseTrackerRequest(),[%1$s]",exception.toString());
        }
        return !validBean.isEmpty();
    }

    public DefaultResponse manageExpenseTracker(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest,String action){
     DefaultResponse response =null;
     SystemError error;
     List<FailRespose> fail = new ArrayList<>();
     List<ExpenseTrackerBean> validBean = new ArrayList<>();
     String apiKey = CommonService.getSessionId(request);
     String token = CommonService.getToken();
     int success=0;
        try{
            LOGGER.printf(Level.INFO,"Entry in manageExpenseTracker()");
            if(!CommonService.isValidSessionToken(apiKey,token)){
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_SESSION_TOKEN,apiKey+" "+token);
                response = CommonService.getDefaultResponse(String.valueOf(success),error,fail,apiKey,token);
            }else if(isValidExpenseTrackerRequest(expenseTrackerDetailsRequest.getExpenseTrackerDetails(),validBean,fail,action)){
                int userId= ApplicationCommonContex.getInstance().getLoginUserId();
                expenseTrackerDetailsRequest.setCreateBy(userId);
                switch (action){
                    case ConstantPool.ACTION_ADD :{
                        success = expenseTrackerService.createExpenseTrackerDetails(validBean,userId);
                    }
                    break;
                    case ConstantPool.ACTION_DELETE:{
                       success = expenseTrackerService.deleteExpenseTrackerDetails(validBean,userId);
                    }
                    break;
                    case ConstantPool.ACTION_UPDATE:{
                        success = expenseTrackerService.updateExpenseTrackerDetails(validBean,userId);
                    }
                    break;
                }
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                response = CommonService.getDefaultResponse(String.valueOf(success),error,fail,apiKey,token);
            }else{

            }
            LOGGER.printf(Level.INFO,"Exit from manageExpenseTracker()");
        }catch (Exception exception){
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = CommonService.getDefaultResponse(String.valueOf(success),error,fail,apiKey,token);
            LOGGER.printf(Level.ERROR,"Exception in manageExpenseTracker(),[%1$s]", exception.toString());
        }
        return response;
    }
}
