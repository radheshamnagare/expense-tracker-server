package com.app.server.dao;

import com.app.server.bean.GraphDetails;
import com.app.server.common.CommonValidator;
import com.app.server.dto.GraphReportDto;
import com.app.server.request.ExpenseTrackerGraphReq;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GraphDaoImpl implements GraphDao{
    private final static Logger LOGGER = LogManager.getLogger(GraphDaoImpl.class);
    @Autowired
    ExpenseTrackerRepo expenseTrackerRepo;

    private List<GraphDetails> graphReportDtoList(List<GraphReportDto> listOfDetails){
        List<GraphDetails> graphDetails =null;
        try{
            graphDetails = listOfDetails.stream().map(bean->{
                GraphDetails details = new GraphDetails();
                details.setLabel(bean.getLabelVal());
                details.setValue(String.valueOf(bean.getAmountVal()));
                return details;
            }).collect(Collectors.toList());
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in graphReportDtoList,[%1$s]",ex.toString());
        }
        return graphDetails;
    }
    @Override
    public List<GraphDetails> graphDetails(ExpenseTrackerGraphReq req) {
       List<GraphDetails> graphDetails =null;
        try{
            LOGGER.printf(Level.INFO,"Entry in graphDetails()");
                if(!CommonValidator.isEmpty(req.getMonth()) && !CommonValidator.isEmpty(req.getYear())){
                    List<GraphReportDto> listOfDetails = expenseTrackerRepo.findGraphDetailsOfMonth(Integer.parseInt(req.getMonth()),Integer.parseInt(req.getYear()));
                    graphDetails = graphReportDtoList(listOfDetails);
                }else if(!CommonValidator.isEmpty(req.getYear())){
                    List<GraphReportDto> listOfDetails =  expenseTrackerRepo.findGraphDetailsOfYear(Integer.parseInt(req.getYear()));
                    graphDetails = graphReportDtoList(listOfDetails);
                }
            LOGGER.printf(Level.INFO,"Exit from graphDetails()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in graphDetails(),[%1$s]");
        }
        return graphDetails;
    }
}
