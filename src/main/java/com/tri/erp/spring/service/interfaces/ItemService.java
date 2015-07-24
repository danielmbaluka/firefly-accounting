package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Item;
import com.tri.erp.spring.response.PostResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Integer id);
    public Item findByDescription(String desc);
    public PostResponse processUpdate(Item item, BindingResult bindingResult, MessageSource messageSource);
    public PostResponse processCreate(Item item, BindingResult bindingResult, MessageSource messageSource);
}
