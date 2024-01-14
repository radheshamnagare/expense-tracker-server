package com.app.server.service;

import com.app.server.bean.GraphDetails;
import com.app.server.request.ExpenseTrackerGraphReq;

import java.util.List;

public interface GraphService {

    public List<GraphDetails> graphDetails(ExpenseTrackerGraphReq req);
}
