package com.app.server.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "response-base")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBase {

    @XmlElement(name="error-code")
    @JsonProperty("error-code")
    String errorCode;

    @XmlElement(name="error-status")
    @JsonProperty("error-status")
    String errorStatus;

    @XmlElement(name="error-description")
    @JsonProperty("error-description")
    String errorDescription;

    @XmlElement(name="api-key")
    @JsonProperty("api-key")
    String apiKey;

    @XmlElement(name="token")
    @JsonProperty("token")
    String token;
}
