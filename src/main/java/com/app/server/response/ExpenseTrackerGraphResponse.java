package com.app.server.response;

import com.app.server.bean.FailRespose;
import com.app.server.bean.GraphDetails;
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
@XmlRootElement(name = "expense-tracker-graph-response")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseTrackerGraphResponse extends ResponseBase {

    @XmlElement(name="graph-details")
    @JsonProperty("graph-details")
    List<GraphDetails> graphDetails;

    @XmlElement(name="fail")
    @JsonProperty("fail")
    List<FailRespose> fail;
}
