package com.app.server.dao;

import com.app.server.bean.ExpenseTrackerBean;
import com.app.server.request.ExpenseTrackerListReq;

import java.util.List;

public interface ExpenseTrackerDao {

    List<ExpenseTrackerBean> expenseTrackerDetails(ExpenseTrackerListReq expenseTrackerListReq);

    int createExpenseTrackerDetails(List<ExpenseTrackerBean> req,int userId);

    int updateExpenseTrackerDetails(List<ExpenseTrackerBean> req,int userId);

    int deleteExpenseTrackerDetails(List<ExpenseTrackerBean> req,int userId);

    int isValidExpenceTrackerId(int id);
}
