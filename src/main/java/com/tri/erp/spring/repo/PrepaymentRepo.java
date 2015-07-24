package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Prepayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Personal on 6/3/2015.
 */
public interface PrepaymentRepo extends JpaRepository<Prepayment, Integer> {
    public List<Prepayment> findByDescription(String description);
}
