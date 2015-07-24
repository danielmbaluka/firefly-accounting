package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.User;
import com.tri.erp.spring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by TSI Admin on 10/12/2014.
 */
@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Autowired
    UserRepo userRepo;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getLoggedIn() {
        Authentication authentication = this.getAuthentication();
        String curUsername = authentication.getName();
        User user = userRepo.findOneByUsername(curUsername);
        return user;
    }
}
