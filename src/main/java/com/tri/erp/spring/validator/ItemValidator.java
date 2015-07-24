package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Item;
import com.tri.erp.spring.model.Role;
import com.tri.erp.spring.service.implementations.ItemServiceImpl;
import com.tri.erp.spring.service.implementations.RoleServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class ItemValidator implements Validator {

    private ItemServiceImpl service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Item item = (Item) o;

        Item i = this.service.findByDescription(item.getDescription());
        // insert mode
        if (item.getId() == null || item.getId() == 0) {
            if (i != null) {
                errors.rejectValue("description", "item.description.taken");
            }
        } else {
            // update role
            if (i != null && i.getId() != item.getId()) { // diff item
                errors.rejectValue("description", "item.description.taken");
            }
        }
    }

    public void setService(ItemServiceImpl service) {
        this.service = service;
    }
}
