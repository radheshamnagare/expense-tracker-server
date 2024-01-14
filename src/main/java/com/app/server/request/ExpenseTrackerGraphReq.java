package com.app.server.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="expense-tracker-graph-request")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseTrackerGraphReq {

    @XmlElement(name="month")
    @JsonProperty("month")
    String month;

    @XmlElement(name="year")
    @JsonProperty("year")
    String year;

    @XmlElement(name="user-id")
    @JsonProperty("user-id")
    int userId;
}
