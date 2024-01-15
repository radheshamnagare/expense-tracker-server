package com.app.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "m_users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column(unique = true)
    String userName;

    @Column
    String password;

    @Column
    String emailId;

    @Column
    boolean isEnable;

    @Column
    boolean isAccountExpire;

    @Column
    boolean isAccountLock;

    @Column
    String domain;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Date insertTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    Date updateTime;

}
