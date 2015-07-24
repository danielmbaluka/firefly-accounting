package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.StringFormatter;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.RouteRepo;
import com.tri.erp.spring.repo.PageComponentRepo;
import com.tri.erp.spring.repo.RoleRepo;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.PostRoleResponse;
import com.tri.erp.spring.service.interfaces.RoleService;
import com.tri.erp.spring.validator.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.*;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PageComponentRepo pageComponentRepo;

    @Autowired
    RouteRepo routeRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role findById(Integer roleId) {

        Role role = roleRepo.findOne(roleId);

        if (role != null) {
            // get menus
            List<Object[]> rolesObj = roleRepo.findMenusByRoleId(role.getId());
            if (!Checker.collectionIsEmpty(rolesObj)) {

                for (Object[] obj : rolesObj) {

                    Menu menu = new Menu();
                    menu.setId((Integer)obj[0]);
                    menu.setTitle((String)obj[1]);

                    Menu parentMenu = new Menu();
                    parentMenu.setId((Integer)obj[2]);
                    menu.setParentMenu(parentMenu);

                    role.getMenus().add(menu);
                }
            }

            // get assigned page components
            List<PageComponent> pageComponents = pageComponentRepo.findAllByRoleId(role.getId());
            role.getPageComponents().addAll(pageComponents);
        }

        return role;
    }

    @Override
    @Transactional
    public PostResponse processCreate(Role role, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostRoleResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        RoleValidator validator = new RoleValidator();
        validator.setService(this);
        validator.validate(role, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
            response.setSuccess(false);
        } else {
            Role newRole = roleRepo.save(role);

            if (role.getId() != null && role.getId() > 0) { // update mode; reset some stuffs
                roleRepo.removeMenus(role.getId());

                if (!Checker.collectionIsEmpty(role.getPageComponentsToEvict())) {
                    for (PageComponent pageComponent : role.getPageComponentsToEvict()) {
                        roleRepo.removeAssignedPageComponent(role.getId(), pageComponent.getId());
                        roleRepo.removeAssignedRoute(role.getId(), pageComponent.getViewRoute().getId());
                        roleRepo.removeAssignedRoute(role.getId(), pageComponent.getActionRoute().getId());
                    }
                }

                if (!Checker.collectionIsEmpty(role.getMenusToEvict())) {
                    for (Menu menu : role.getMenusToEvict()) {
                        if (menu.getViewRoute() != null) {
                            roleRepo.removeAssignedRoute(role.getId(), menu.getViewRoute().getId());
                        }
                    }
                }
            }

            // insert menus assigned
            if (!Checker.collectionIsEmpty(role.getMenus())) {
                for (Menu menu : role.getMenus()) {
                    roleRepo.saveMenus(role.getId(), menu.getId());
                    if (menu.getViewRoute() != null) {
                        roleRepo.saveAssignedRoute(role.getId(), menu.getViewRoute().getId());
                    }
                }
            }

            // insert page components assigned
            if (!Checker.collectionIsEmpty(role.getPageComponents())) {
                for (PageComponent pageComponent : role.getPageComponents()) {
                    roleRepo.saveAssignedPageComponent(role.getId(), pageComponent.getId());
                }
            }

            // insert assigned route (RoleRoute)
            if (!Checker.collectionIsEmpty(role.getPageComponents())) {
                for (PageComponent pageComponent : role.getPageComponents()) {
                    roleRepo.saveAssignedRoute(role.getId(), pageComponent.getActionRoute().getId());
                    roleRepo.saveAssignedRoute(role.getId(), pageComponent.getViewRoute().getId());
                }
            }

            response.setModelId(newRole.getId());
            response.setSuccessMessage("Role successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public Role findByName(String name) {
        List<Role> roles = roleRepo.findByName(name);
        if (!Checker.collectionIsEmpty(roles)) {
            return roles.get(0);
        } else return null;
    }

    @Override
    @Transactional
    public PostResponse processUpdate(Role role, BindingResult bindingResult, MessageSource messageSource) {
        return this.processCreate(role, bindingResult, messageSource);
    }

    @Override
    public Map<String, String> findPageComponentByRoute(Integer userId, String url) {
        url = StringFormatter.removeBaseFromRoute(url);
        Route route = routeRepo.findOneByUrl(url);

        Map<String, String> componentMap = new HashMap<>();

        if (route != null) {
            List<PageComponent> pageComponents = pageComponentRepo.findAllByUserAndRouteId(userId, route.getId());
            for(PageComponent pageComponent : pageComponents) {
                componentMap.put(pageComponent.getDomId(), pageComponent.getHtml());
            }
        }

        return componentMap;
    }

    @Override
    public Boolean isRouteAuthorized(Integer userId, String url) {
        url = StringFormatter.removeBaseFromRoute(url);

        Route restrictedRoute = routeRepo.findOneByUrlAndRestrictedTrue(url);

        if (restrictedRoute != null) {
            Route route = routeRepo.findAssignedByUserAndRouteId(userId, url);
            return route != null; // no permission for empty result
        }

        return true;
    }
}
