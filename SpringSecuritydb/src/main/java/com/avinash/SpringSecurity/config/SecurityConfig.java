package com.avinash.SpringSecurity.config;

import com.avinash.SpringSecurity.controller.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//In Spring, the @Configuration annotation is used to indicate that a class declares one or more @Bean methods
// may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
// Essentially, it is a way of telling Spring that this class contains configuration information for the Spring application context.

//What @EnableWebSecurity Does
//Enables Spring Security's Web Security Support
//Provides Integration with Spring MVC
//Enables Custom Security Configuration

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //making the server stateless
    //to disable the default Signin form provided by Spring, we must follow the below code
    //"SecurityFilterChain" is the main thing which controls the entire Spring Security

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //disabling the csrf
        http.csrf(customizer->customizer.disable());

        //It is used to specify authorization rules for HTTP requests in a Spring Boot application.
        //anyRequest(): This specifies that the rule should apply to any request, regardless of the URL or HTTP method.
        //authenticated(): This method indicates that the request must be authenticated, meaning the user must be logged in to access any URL in the application.
        http.authorizeHttpRequests(request->request.anyRequest().authenticated());

        //httpBasic: This method enables HTTP Basic Authentication.
        // HTTP Basic Authentication is a simple authentication scheme built into the HTTP protocol.
        // It uses a base64-encoded username and password to authenticate the client.

        //Customizer.withDefaults(): This method configures HTTP Basic Authentication with default settings.
        // It is a convenient way to apply default configurations without needing to specify any custom settings.
        http.httpBasic(Customizer.withDefaults());

        //this will make the session Stateless
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //http.build() will return the SecurityFilterChain
        return http.build();
    }

    //setting our own username and password
    //usually when we provide the username and password in the application.properties, then Spring Boot sets up an in-memory user through InMemoryUserDetailsManager
    //to set our custom credentials, we will use UserServiceDetails object
    //this is hard coded method. not useable for connecting database
    //for connecting the database we are having another method it is below this method

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user= User
//                //for password encryption, here we are using no encryption
//                .withDefaultPasswordEncoder()
//                //setting username
//                .username("avinash")
//                //setting password
//                .password("1234")
//                //setting role (optional)
//                .roles("USER")
//                //build() method returns UserDetails Object
//                .build();
//
//        //we can pass multiple Aeguments (users)
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Autowired
    MyUserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        //using DaoAuthenticationProvider
        //DaoAuthenticationProvider implements AuthenticationProvider
        //creating DaoAuthenticationProvider object

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        //setting UserDetailsService by setUserDetailsService() method
        //it accepts UserServiceDetails object
        //lets create the UserServiceDetails object in a "MyUserDetailsService" controller
        provider.setUserDetailsService(userDetailsService);

        //here we must specify our password encoder
        //as we are using Bcrypt, our encoder will be "new BCryptPasswordEncoder(12)"
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

}
