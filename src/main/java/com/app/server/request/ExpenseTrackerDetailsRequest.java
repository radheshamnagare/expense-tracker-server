package com.app.server.request;

import com.app.server.bean.ExpenseTrackerBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name="expense-tracker-details-request")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseTrackerDetailsRequest {

    @XmlElement(name = "expense-tracker-details")
    @JsonProperty("expense-tracker-details")
    List<ExpenseTrackerBean> expenseTrackerDetails;

    @XmlElement(name = "create-by")
    @JsonProperty("create-by")
    int createBy;
}
