package com.app.server.common;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {

    private final static Logger LOGGER = LogManager.getLogger(CommonService.class);

    public static boolean isEmpty(String var){
        try{
            if(var == null || var.isEmpty())
                return true;
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isEmpty",ex.toString());
        }
        return false;
    }

    public static <T> boolean isEmpty(List<T> list){
        try{
            if(list==null || list.isEmpty())
                return true;
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isEmpty");
        }
        return false;
    }

    public static boolean isNumeric(String var){
        boolean isNumber=false;
        try{
            Pattern pattern = Pattern.compile(ConstantPool.DESC_NUMBER_PATTERN);
            Matcher matcher =  pattern.matcher(var);
            isNumber = matcher.matches();
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isNumeric");
        }
        return isNumber;
    }

    public static boolean isValidEmailId(String emailId){
        boolean isEmailVaild=false;
        try{
           LOGGER.printf(Level.INFO,"Entry in isValidEmailId");
           if(!CommonValidator.isEmpty(emailId)){
               Pattern pattern = Pattern.compile(ConstantPool.DESC_EMAIL_PATTERN);
               Matcher matcher =  pattern.matcher(emailId);
               isEmailVaild = matcher.matches();
           }
           LOGGER.printf(Level.INFO,"Exit from isValidEmailId");
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isValidEmailId,[%1$s]",ex.toString());
        }
        return isEmailVaild;
    }
}
