package com.avinash.SpringSecurity.repository;

import com.avinash.SpringSecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

    //findByUsername
    public User findByUsername(String username);
}
