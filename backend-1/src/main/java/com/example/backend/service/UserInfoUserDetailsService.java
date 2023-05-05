package com.example.backend.service;

import com.example.backend.config.UserInfoUserDetails;
import com.example.backend.model.User;
import com.example.backend.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByEmail(email);
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email));

     	    if(userInfo.isPresent())
        	 return  new UserInfoUserDetails(userInfo.get());
     	    else 
        	 throw new UsernameNotFoundException("user not found " + email);
       
    }
}