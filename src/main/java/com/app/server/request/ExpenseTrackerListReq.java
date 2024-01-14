package com.app.server.request;

import com.app.server.common.ConstantPool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement(name="expense-tracker-list-request")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseTrackerListReq {

    @XmlElement(name = "from-date")
    @JsonProperty("from-date")
    @DateTimeFormat(pattern = ConstantPool.DESC_DATE_FORMAT)
    Date fromDate;

    @XmlElement(name = "to-date")
    @JsonProperty("to-date")
    @DateTimeFormat(pattern = ConstantPool.DESC_DATE_FORMAT)
    Date toDate;

    @XmlElement(name="user-id")
    @JsonProperty("user-id")
    int userId;
}
