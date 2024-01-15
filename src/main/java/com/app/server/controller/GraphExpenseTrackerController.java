package com.app.server.controller;

import com.app.server.modal.ManageGraph;
import com.app.server.request.ExpenseTrackerGraphReq;
import com.app.server.response.ExpenseTrackerGraphResponse;
import com.app.server.service.GraphService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class GraphExpenseTrackerController {
    private final static Logger LOGGER = LogManager.getLogger(GraphExpenseTrackerController.class);
    @Autowired
    GraphService graphService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ConfigurableApplicationContext appContext;

    @RequestMapping(value = "/lookup" ,method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ExpenseTrackerGraphResponse> graphDetails(@RequestBody ExpenseTrackerGraphReq expenseTrackerGraphReq){
        ExpenseTrackerGraphResponse reponse=null;
        try{
            LOGGER.printf(Level.INFO,"Entry in graphDetails()");
            ManageGraph manageGraph = appContext.getBean(ManageGraph.class);
            manageGraph.setGraphService(graphService);
            manageGraph.setRequest(request);
            reponse = manageGraph.graphDetails(expenseTrackerGraphReq);
            LOGGER.printf(Level.INFO,"Exit from graphDetails()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in graphDetails(),[%1$s]",exception.toString());
        }
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
}
