package com.tri.erp.spring.service.interfaces;


import com.tri.erp.spring.model.Page;
import com.tri.erp.spring.response.PageComponentDto;

import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */
public interface PageService {
    public List<Page> findAllWithComponents();
    public List<Page> findAllAssigned(Integer roleId);
    public List<PageComponentDto> getPageComponents(Integer pageId);
    public List<PageComponentDto> getPageComponents(Integer roleId, Integer pageId);
}
