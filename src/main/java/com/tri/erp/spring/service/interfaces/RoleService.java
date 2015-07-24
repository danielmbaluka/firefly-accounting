package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Role;
import com.tri.erp.spring.response.PostResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface RoleService {
    public List<Role> findAll();
    public Role findById(Integer id);
    public PostResponse processCreate(Role role, BindingResult bindingResult, MessageSource messageSource);
    public Role findByName(String str);
    public PostResponse processUpdate(Role role, BindingResult bindingResult, MessageSource messageSource);
    public Map<String, String> findPageComponentByRoute(Integer userId, String route);
    public Boolean isRouteAuthorized(Integer userId, String route);
}
