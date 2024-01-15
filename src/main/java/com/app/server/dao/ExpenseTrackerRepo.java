package com.app.server.dao;

import com.app.server.bean.GraphDetails;
import com.app.server.dto.GraphReportDto;
import com.app.server.entity.ExpenseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExpenseTrackerRepo extends JpaRepository<ExpenseDetails, Integer> {

    void deleteByIdAndName(int id, String name);

    List<ExpenseDetails> findByInsertTimeGreaterThanEqualAndInsertTimeLessThanEqual(Date star, Date end);

    @Query(nativeQuery = true,value = "select DATE_FORMAT(now(),'%d %M %Y')  labelVal,sum(amount) as amountVal from m_expense_details where MONTH(insert_time)=:month and YEAR(insert_time)=:year group by labelVal,YEAR(insert_time)")
    List<GraphReportDto> findGraphDetailsOfMonth(@Param("month") int monthValue,@Param("year") int yearValue);

    @Query(nativeQuery = true,value="select MONTHNAME(insert_time) labelVal,sum(amount) as amountVal from m_expense_details where YEAR(insert_time)=:year group by labelVal,YEAR(insert_time);")
    List<GraphReportDto> findGraphDetailsOfYear(@Param("year") int year);
}
