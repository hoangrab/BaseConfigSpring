package com.hocvui.services.impl;

import com.hocvui.entities.User;
import com.hocvui.model.CustomUserDetail;
import com.hocvui.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Username not found : "+ username));
        return new CustomUserDetail(u);
    }
}
