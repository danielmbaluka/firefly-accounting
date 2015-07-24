package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Item;
import com.tri.erp.spring.model.SlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface ItemRepo extends JpaRepository<Item, Integer>{
    @Transactional
    public List<Item> findAllByOrderByDescriptionAsc();
    public Item findOneByDescription(String desc);
}
