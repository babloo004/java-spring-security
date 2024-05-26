package com.avinash.SpringSecurity.controller;

import com.avinash.SpringSecurity.model.User;
import com.avinash.SpringSecurity.model.UserPrincipal;
import com.avinash.SpringSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

//in this class we are implementing the UserDetailsService Interface
//that interface contains one method known as "loadUserByUsername"
//we must implement this method
//in this method we will connect it to the Repository to fetch the details of user by username

@Service
public class MyUserDetailsService implements UserDetailsService {

    //instances
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        //this will return us the row data with the matching username
        //if it is null we will throw Exception
        if(user==null){
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        //here we cant directly return the user directly
        //it is expecting UserDetails object
        //so we must create the UserDetails object
        //because UserDetails contains extra info like expiry time etc...
        //so lets create the UserDetails object in the model package, named UserPrincipal
        //we must pass the User object into the UserPrincipal


        //we must return UserDetailes object
        //so we must create the UserDetailes object in the model package

        return new UserPrincipal(user);
    }
}
