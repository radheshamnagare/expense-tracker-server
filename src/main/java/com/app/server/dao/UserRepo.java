package com.app.server.dao;

import com.app.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUserNameAndPasswordAndDomain(String userName,String password,String domain);

    User findByUserName(String userName);

    @Query(value = "select user_name from m_users where user_name=:username",nativeQuery = true)
    String findUserName(@Param("username") String userName);
}
