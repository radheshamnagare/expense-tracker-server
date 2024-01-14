package com.app.server.response;

import com.app.server.bean.FailRespose;
import com.app.server.bean.ResponseBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "login-response")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends ResponseBase {

    @XmlElement(name="user-name")
    @JsonProperty("user-name")
    private String userName;

    @XmlElement(name="auth-token")
    @JsonProperty("auth-token")
    private String jwtToken;

    @XmlElement(name="success")
    @JsonProperty("success")
    private String success;

    @XmlElement(name="fail")
    @JsonProperty("fail")
    private List<FailRespose> fail;
}
