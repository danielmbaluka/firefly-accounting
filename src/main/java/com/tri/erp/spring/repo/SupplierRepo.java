package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 11/11/2014.
 */
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {
    public List<Supplier> findByName(String name);
    public Supplier findOneByAccountNumber(Integer accountNo);
}
