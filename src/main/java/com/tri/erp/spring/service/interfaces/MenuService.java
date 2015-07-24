package com.tri.erp.spring.service.interfaces;


import com.tri.erp.spring.response.MenuDto;

import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */
public interface MenuService {
    public List<MenuDto> findAll();
    public List<MenuDto> findAllByUser();
}
