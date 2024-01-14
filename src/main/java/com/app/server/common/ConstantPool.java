package com.app.server.common;

public class ConstantPool {

    public final static String DESC_NUMBER_PATTERN="[0-9]+";
    public final static String DESC_EMAIL_PATTERN="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public final static String DESC_DATE_FORMAT="yyyy-MM-dd HH:mm:SS";

    //state
    public final static String DESC_ACTIVE_STATE="active";
    public final static String DESC_INACTIVE_STATE="inactive";

    //error description helper
    public final static String DESC_REQUIRED_MESG="{var0} is mandentory";
    public final static String DESC_INVALID_MESG="{var0} is invalid,{var1}";
    public final static String DESC_DUPLICATE_MESG="{var0} is duplicate,{var1}";
    public final static String DESC_FAIL_MESG="Fail to exceute";
    public final static String DESC_UNKNOWN_MESG="Unknown";
    public final static String DESC_SUCCESS_MESG="success";

    //error code in text
    public final static String DESC_SUCCESS="success";
    public final static String DESC_UNKNOWN="unknown";
    public final static String DESC_FAIL="fail";
    public final static String DESC_INVALID="invalid";
    public final static String DESC_REQUIRED="required";
    public final static String DESC_DUPLICATE="duplicate";

    //error code description
    public final static String ERROR_CODE_UNKNOWN="-1";
    public final static String ERROR_CODE_SUCCESS="000";
    public final static String ERROR_CODE_FAIL="001";
    public final static String ERROR_CODE_DUPLICATE="002";
    public final static String ERROR_CODE_INVALID="003";
    public final static String ERROR_CODE_REQUIRED="004";

    //action
    public final static String ACTION_ADD="add";
    public final static String ACTION_UPDATE="update";
    public final static String ACTION_DELETE="delete";

}
