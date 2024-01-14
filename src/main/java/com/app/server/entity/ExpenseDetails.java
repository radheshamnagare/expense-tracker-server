package com.app.server.entity;

import com.app.server.common.ConstantPool;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Table(name = "m_expense_details")
@Entity
public class ExpenseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String name;

    @Column
    int amount;

    @Column
    String description;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date insertTime;

    @Column(insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date updateTime;

    @Column
    int createBy;


    @PrePersist
    public void setTime() {
        SimpleDateFormat sdf= new SimpleDateFormat(ConstantPool.DESC_DATE_FORMAT);
        try{
            String _time = sdf.format(new Date());
            this.setUpdateTime(sdf.parse(_time));
            this.setInsertTime(sdf.parse(_time));
        }catch (Exception ex){

        }
    }
}
