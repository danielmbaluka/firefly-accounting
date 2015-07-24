package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.model.Menu;
import com.tri.erp.spring.model.User;
import com.tri.erp.spring.repo.MenuRepo;
import com.tri.erp.spring.response.MenuDto;
import com.tri.erp.spring.service.interfaces.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    MenuRepo menuRepo;

    @Override
    @Transactional(readOnly = true)
    public List<MenuDto> findAll() {

        List<MenuDto> menuDtoList = new ArrayList<>();
        List<Menu> menus =  menuRepo.findAll();

        for (Menu menu : menus) {
            MenuDto menuDto = new MenuDto();

            menuDto.setId(menu.getId());
            menuDto.setIconClass(menu.getIconClass());
            menuDto.setState(menu.getState());
            menuDto.setTitle(menu.getTitle());
            menuDto.setParentMenu(menu.getParentMenu());
            menuDto.setViewRoute(menu.getViewRoute());

            if (menu.getParentMenu() == null || menu.getParentMenu().getId() == 0) {
                menuDto.setSubMenus(this.getChildren(menus, menu.getId()));
                menuDto.setParentMenu(null);
            }

            menuDtoList.add(menuDto);
        }

        return menuDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDto> findAllByUser() {

        User currentUser = authenticationFacade.getLoggedIn();

        List<MenuDto> menuDtoList = new ArrayList<>();
        List<Menu> menus =  menuRepo.findAllByUserId(currentUser.getId());

        for (Menu menu : menus) {
            MenuDto menuDto = new MenuDto();

            menuDto.setId(menu.getId());
            menuDto.setIconClass(menu.getIconClass());
            menuDto.setState(menu.getState());
            menuDto.setTitle(menu.getTitle());
            menuDto.setParentMenu(menu.getParentMenu());

            if (menu.getParentMenu() == null || menu.getParentMenu().getId() == 0) {
                menuDto.setSubMenus(this.getChildren(menus, menu.getId()));
                menuDto.setParentMenu(null);
            }

            menuDtoList.add(menuDto);
        }

        return menuDtoList;
    }

    private List<MenuDto> getChildren(List<Menu> menus, int parentMenuId) {
        List<MenuDto> menuDtoChildren = new ArrayList<>();

        for (Menu menu : menus) {
            if (menu.getParentMenu() != null) {
                if (menu.getParentMenu().getId() == parentMenuId) {
                    MenuDto menuDto = new MenuDto();
                    menuDto.setId(menu.getId());
                    menuDto.setIconClass(menu.getIconClass());
                    menuDto.setParentMenu(menu.getParentMenu());
                    menuDto.setState(menu.getState());
                    menuDto.setTitle(menu.getTitle());

                    menuDtoChildren.add(menuDto);
                }
            }
        }

        return menuDtoChildren;
    }
}
