package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.User;
import org.springframework.security.core.Authentication;

/**
 * Created by TSI Admin on 10/12/2014.
 */
public interface AuthenticationFacade {
    Authentication getAuthentication();
    User getLoggedIn();
}
