package com.app.server.modal;

import com.app.server.bean.FailRespose;
import com.app.server.bean.GraphDetails;
import com.app.server.bean.SystemError;
import com.app.server.common.*;
import com.app.server.request.ExpenseTrackerGraphReq;
import com.app.server.response.ExpenseTrackerGraphResponse;
import com.app.server.service.GraphService;
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
public class ManageGraph {
    private static final Logger LOGGER = LogManager.getLogger(ManageGraph.class);
    HttpServletRequest request;
    GraphService graphService;

    private ExpenseTrackerGraphResponse getExpenseTrackerGraphResponse(List<GraphDetails> graphDetails, List<FailRespose> fail, SystemError error, String apiKey, String token){
        ExpenseTrackerGraphResponse response = new ExpenseTrackerGraphResponse();
        try{
            LOGGER.printf(Level.INFO,"Entry in getExpenseTrackerGraphResponse()");
            response.setGraphDetails(graphDetails);
            response.setFail(fail);
            response.setErrorCode(error.getErrorCode());
            response.setErrorStatus(response.getErrorStatus());
            response.setErrorDescription(response.getErrorDescription());
            response.setApiKey(apiKey);
            response.setToken(token);
            LOGGER.printf(Level.INFO,"Exit from getExpenseTrackerGraphResponse()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getExpenseTrackerGraphResponse(),[%1$s]",exception.toString());
        }
        return response;
    }

    private boolean isValidGraphRequest(ExpenseTrackerGraphReq graphReq,List<FailRespose> fail){
        try{
            FailRespose failRespose;
            LOGGER.printf(Level.INFO,"Entry in isValidGraphRequest()");
            if(CommonValidator.isEmpty(graphReq.getMonth()) && CommonValidator.isEmpty(graphReq.getYear())){
                 failRespose = new FailRespose();
                 failRespose.setId(ErrorConstatnt.DESC_YEAR_MONTH);
                 failRespose.setReason(CommonService.getSystemError(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_YEAR_MONTH,"").getErrorDescription());
                 fail.add(failRespose);
                 return false;
            }else if(!CommonValidator.isNumeric(graphReq.getYear())){
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_YEAR);
                failRespose.setReason(CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_YEAR,graphReq.getYear()).getErrorDescription());
                fail.add(failRespose);
                return false;
            }
            LOGGER.printf(Level.INFO,"Exit from isValidGraphRequest()");
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isValidGraphRequest(),[%1$s]",ex.toString());
        }
        return true;
    }
    public ExpenseTrackerGraphResponse graphDetails(ExpenseTrackerGraphReq expenseTrackerGraphReq){
        ExpenseTrackerGraphResponse response=null;
        String apiKey = CommonService.getSessionId(request);
        String token = CommonService.getToken();
        List<FailRespose> fail = new ArrayList<>();
        SystemError error = null;
        try{
            if(!CommonService.isValidSessionToken(apiKey,token)){
                FailRespose failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_SESSION_TOKEN);
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_SESSION_TOKEN,apiKey+token);
                failRespose.setReason(error.getErrorDescription());
                fail.add(failRespose);
                response = getExpenseTrackerGraphResponse(null,fail,error,apiKey,token);
            }else if(isValidGraphRequest(expenseTrackerGraphReq,fail)){
                int userId = ApplicationCommonContex.getInstance().getLoginUserId();
                expenseTrackerGraphReq.setUserId(userId>0?userId:0);
                List<GraphDetails> graphDetails = graphService.graphDetails(expenseTrackerGraphReq);
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                response = getExpenseTrackerGraphResponse(graphDetails,fail,error,apiKey,token);
            }
        }catch (Exception exception){
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = getExpenseTrackerGraphResponse(null,fail,error,apiKey,token);
            LOGGER.printf(Level.ERROR,"Exception in graphDetails(),[%1$s]",exception.toString());
        }
        return response;
    }
}
