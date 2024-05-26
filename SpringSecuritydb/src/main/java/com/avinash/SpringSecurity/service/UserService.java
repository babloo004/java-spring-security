package com.avinash.SpringSecurity.service;

import com.avinash.SpringSecurity.model.User;
import com.avinash.SpringSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    //Repository object
    @Autowired
    private UserRepo repo;

    //addUser()
    public List<User> addUser(User user){
        repo.save(user);
        return repo.findAll();
    }

    //getUsers()
    public List<User> getUsers(){
        return repo.findAll();
    }
}
