package com.app.server.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "user-register-request")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterRequest {
    @XmlElement(name="first-name")
    @JsonProperty("first-name")
    String firstName;

    @XmlElement(name="last-name")
    @JsonProperty("last-name")
    String lastName;

    @XmlElement(name="user-name")
    @JsonProperty("user-name")
    String userName;

    @XmlElement(name="email-address")
    @JsonProperty("email-address")
    String emailAddress;

    @XmlElement(name="password")
    @JsonProperty("password")
    String password;

    @XmlElement(name="domain")
    @JsonProperty("domain")
    String domain;
}
