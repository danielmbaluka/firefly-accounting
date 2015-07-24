package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.Page;
import com.tri.erp.spring.model.PageComponent;
import com.tri.erp.spring.repo.PageComponentRepo;
import com.tri.erp.spring.repo.PageRepo;
import com.tri.erp.spring.response.PageComponentDto;
import com.tri.erp.spring.service.interfaces.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */


@Service
public class PageServiceImpl implements PageService {

    @Autowired
    PageRepo pageRepo;

    @Autowired
    PageComponentRepo pageComponentRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Page> findAllWithComponents() {
        return pageRepo.findAllWithComponents();
    }

    @Override
    public List<Page> findAllAssigned(Integer roleId) {
        return pageRepo.findAllAssigned(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageComponentDto> getPageComponents(Integer pageId) {
        List<PageComponentDto> pageComponentDtoList = new ArrayList<>();

        List<PageComponent> pageComponentList = pageComponentRepo.findAllByPageId(pageId);
        for (PageComponent pageComponent : pageComponentList) {
            PageComponentDto componentDto = new PageComponentDto();
            componentDto.setDescription(pageComponent.getDescription());
            componentDto.setActionRouteId(pageComponent.getActionRoute().getId());
            componentDto.setPageComponentId(pageComponent.getId());
            componentDto.setPageId(pageComponent.getPage().getId());
            componentDto.setViewRouteId(pageComponent.getViewRoute().getId());
            componentDto.setViewRouteId(pageComponent.getViewRoute().getId());
            componentDto.setId(pageComponent.getId());

            pageComponentDtoList.add(componentDto);
        }

        return pageComponentDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageComponentDto> getPageComponents(Integer roleId, Integer pageId) {
        List<PageComponentDto> pageComponentDtoList = new ArrayList<>();

        List<PageComponent> pageComponentList = pageComponentRepo.findAllByRoleIdAndPageId(roleId, pageId);
        for (PageComponent pageComponent : pageComponentList) {
            PageComponentDto componentDto = new PageComponentDto();
            componentDto.setDescription(pageComponent.getDescription());
            componentDto.setActionRouteId(pageComponent.getActionRoute().getId());
            componentDto.setPageComponentId(pageComponent.getId());
            componentDto.setPageId(pageComponent.getPage().getId());
            componentDto.setViewRouteId(pageComponent.getViewRoute().getId());
            componentDto.setViewRouteId(pageComponent.getViewRoute().getId());
            componentDto.setId(pageComponent.getId());

            pageComponentDtoList.add(componentDto);
        }

        return pageComponentDtoList;
    }
}
