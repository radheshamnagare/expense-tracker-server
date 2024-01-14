package com.app.server.dao;

import com.app.server.bean.ExpenseTrackerBean;
import com.app.server.common.CommonValidator;
import com.app.server.entity.ExpenseDetails;
import com.app.server.request.ExpenseTrackerListReq;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ExpenseTrackerDaoImpl implements ExpenseTrackerDao {
    private static final Logger LOGGER = LogManager.getLogger(ExpenseTrackerDaoImpl.class);
    @Autowired
    ExpenseTrackerRepo expenseTrackerRepo;

    private ExpenseDetails getExpenseDetails(ExpenseTrackerBean expenseTrackerBean, int userId) {
        ExpenseDetails expenseDetails = new ExpenseDetails();
        try {
            LOGGER.printf(Level.INFO, "Exntry in getExpenseDetails()");
            if(expenseTrackerBean.getId()>0)
                expenseDetails.setId(expenseTrackerBean.getId());
            expenseDetails.setName(expenseTrackerBean.getName());
            expenseDetails.setDescription(expenseTrackerBean.getDescription());
            expenseDetails.setAmount(Integer.parseInt(expenseTrackerBean.getAmount()));
            expenseDetails.setCreateBy(userId);
            LOGGER.printf(Level.INFO, "Exit from getExpenseDetails()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception from getExpenseDetails(),[%1$s]", exception.toString());
        }
        return expenseDetails;
    }

    private List<ExpenseTrackerBean> getExpensTrackerBean(List<ExpenseDetails> listExpensDetails){
        List<ExpenseTrackerBean> listOfExpenseTrackerDetails = new ArrayList<>();
        try{
            LOGGER.printf(Level.INFO,"Entry in getExpensTrackerBean()");
            listExpensDetails.stream().forEach(bean->{
                ExpenseTrackerBean detail = new ExpenseTrackerBean();
                detail.setId(bean.getId());
                detail.setName(bean.getName().toUpperCase());
                detail.setDescription(bean.getDescription());
                detail.setAmount(String.valueOf(bean.getAmount()));
                detail.setInsertTime(bean.getInsertTime());
                detail.setUpdateTime(bean.getUpdateTime());
                detail.setCreateBy(bean.getCreateBy());
                listOfExpenseTrackerDetails.add(detail);
            });
            LOGGER.printf(Level.INFO,"Exit from getExpensTrackerBean()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getExpensTrackerBean(),[%1$s]",exception.toString());
        }
        return listOfExpenseTrackerDetails;
    }
    @Override
    public List<ExpenseTrackerBean> expenseTrackerDetails(ExpenseTrackerListReq expenseTrackerListReq) {
        List<ExpenseTrackerBean> expenseTrackerBeanList=null;
        List<ExpenseDetails> expenseDetails=null;
        try{
            LOGGER.printf(Level.INFO,"Entry in expenseTrackerDetails()");
            if(expenseTrackerListReq.getFromDate()!=null && expenseTrackerListReq.getToDate()!=null){
                expenseDetails = expenseTrackerRepo.findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqual(expenseTrackerListReq.getFromDate(),expenseTrackerListReq.getToDate());
            }else{
                expenseDetails= expenseTrackerRepo.findAll();
            }
            if(!CommonValidator.isEmpty(expenseDetails)){
                expenseTrackerBeanList = getExpensTrackerBean(expenseDetails);
            }
            LOGGER.printf(Level.INFO,"Exit from expenseTrackerDetails()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in expenseTrackerDetails(),[%1$s]",exception.toString());
        }
        return expenseTrackerBeanList;
    }
    @Override
    public int createExpenseTrackerDetails(List<ExpenseTrackerBean> req, int userId) {
        int success = 0;
        try {
            LOGGER.printf(Level.INFO, "Entry in createExpenseTrackerDetails()");
            List<ExpenseDetails> validBean = new ArrayList<>();
            req.stream().forEach(bean -> {
                ExpenseDetails expenseDetails = getExpenseDetails(bean, userId);
                if (expenseDetails != null)
                    validBean.add(expenseDetails);
            });
            if (!validBean.isEmpty())
                success = expenseTrackerRepo.saveAll(validBean).size();
            LOGGER.printf(Level.INFO, "Exit from createExpenseTrackerDetails()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in createExpenseTrackerDetails()");
        }
        return success;
    }
    @Override
    public int updateExpenseTrackerDetails(List<ExpenseTrackerBean> req, int userId) {
        int success = 0;
        try {
            LOGGER.printf(Level.INFO, "Entry in updateExpenseTrackerDetails()");
            List<ExpenseDetails> validBean = new ArrayList<>();
            req.stream().forEach(bean -> {
                ExpenseDetails expenseDetails = getExpenseDetails(bean, userId);
                if (expenseDetails != null)
                    validBean.add(expenseDetails);
            });
            if (!validBean.isEmpty())
                success = expenseTrackerRepo.saveAll(validBean).size();
            LOGGER.printf(Level.INFO, "Exit from updateExpenseTrackerDetails()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in updateExpenseTrackerDetails(),[%1$s]", exception.toString());
        }
        return success;
    }
    @Override
    public int deleteExpenseTrackerDetails(List<ExpenseTrackerBean> req, int userId) {
        AtomicInteger success = new AtomicInteger(0);
        try {
            LOGGER.printf(Level.INFO, "Entry in deleteExpenseTrackerDetails()");
            req.stream().forEach(bean -> {
                 expenseTrackerRepo.deleteById(bean.getId());
                success.getAndAdd(1);
            });
            LOGGER.printf(Level.INFO, "Exit from deleteExpenseTrackerDetails()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in deleteExpenseTrackerDetails(),[%1$s]", exception.toString());
        }
        return success.get();
    }

    @Override
    public int isValidExpenceTrackerId(int id) {
        return expenseTrackerRepo.findById(id).get().getId();
    }
}
