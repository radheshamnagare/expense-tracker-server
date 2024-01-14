package com.app.server.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@XmlRootElement(name = "expense-tracker-bean")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseTrackerBean {

    @XmlElement(name="id")
    @JsonProperty("id")
    int id;

    @XmlElement(name="name")
    @JsonProperty("name")
    String name;

    @XmlElement(name="amount")
    @JsonProperty("amount")
    String amount;

    @XmlElement(name="description")
    @JsonProperty("description")
    String description;

    @XmlElement(name="insert-time")
    @JsonProperty("insert-time")
    Date insertTime;

    @XmlElement(name="update-time")
    @JsonProperty("update-time")
    Date updateTime;

    @XmlElement(name="create-by")
    @JsonProperty("create-by")
    int createBy;
}
