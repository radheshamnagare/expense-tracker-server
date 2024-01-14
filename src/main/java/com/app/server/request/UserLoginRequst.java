package com.app.server.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="user-login-request")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginRequst {

    @XmlElement(name = "user-id")
    @JsonProperty("user-id")
    String userId;

    @XmlElement(name = "password")
    @JsonProperty("password")
    String password;

    @XmlElement(name = "domain")
    @JsonProperty("domain")
    String domain;
}
