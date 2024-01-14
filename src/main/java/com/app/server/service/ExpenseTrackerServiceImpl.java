package com.app.server.service;

import com.app.server.bean.ExpenseTrackerBean;
import com.app.server.dao.ExpenseTrackerDao;
import com.app.server.request.ExpenseTrackerListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseTrackerServiceImpl implements ExpenseTrackerService{

    @Autowired
    ExpenseTrackerDao expenseTrackerDao;

    @Override
    public List<ExpenseTrackerBean> expenseTrackerDetails(ExpenseTrackerListReq expenseTrackerListReq) {
        return expenseTrackerDao.expenseTrackerDetails(expenseTrackerListReq);
    }

    @Override
    public int createExpenseTrackerDetails(List<ExpenseTrackerBean> req, int userId) {
        return expenseTrackerDao.createExpenseTrackerDetails(req,userId);
    }

    @Override
    public int updateExpenseTrackerDetails(List<ExpenseTrackerBean> req, int userId) {
        return expenseTrackerDao.updateExpenseTrackerDetails(req,userId);
    }

    @Override
    public int deleteExpenseTrackerDetails(List<ExpenseTrackerBean> req, int userId) {
        return expenseTrackerDao.deleteExpenseTrackerDetails(req,userId);
    }

    @Override
    public int isValidExpenceTrackerId(int id) {
        return expenseTrackerDao.isValidExpenceTrackerId(id);
    }
}
