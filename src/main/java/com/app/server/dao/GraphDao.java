package com.app.server.dao;

import com.app.server.bean.GraphDetails;
import com.app.server.request.ExpenseTrackerGraphReq;

import java.util.List;

public interface GraphDao {

    List<GraphDetails> graphDetails(ExpenseTrackerGraphReq req);
}
