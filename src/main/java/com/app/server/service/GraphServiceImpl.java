package com.app.server.service;

import com.app.server.bean.GraphDetails;
import com.app.server.dao.GraphDao;
import com.app.server.request.ExpenseTrackerGraphReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphServiceImpl implements GraphService{
    @Autowired
    GraphDao graphDao;
    @Override
    public List<GraphDetails> graphDetails(ExpenseTrackerGraphReq req){
        return graphDao.graphDetails(req);
    }
}
