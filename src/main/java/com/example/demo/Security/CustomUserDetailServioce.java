package com.example.demo.Security;

import com.example.demo.entities.User;
import com.example.demo.execeptions.ResourceNotFoundExeception;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServioce  implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundExeception("user","email"+username,0));
//        loading user from database by username
        return user;
    }
}
