package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Supplier;
import com.tri.erp.spring.model.UnitMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 11/11/2014.
 */
public interface UnitMeasureRepo extends JpaRepository<UnitMeasure, Integer> {
    public UnitMeasure findOneByCode(String str);
    public UnitMeasure findOneByDescription(String str);
}
