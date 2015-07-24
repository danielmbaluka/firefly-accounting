package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.DateRange;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 5/22/2015.
 */
public interface DateRangeRepo extends JpaRepository<DateRange, Integer> {
    public List<DateRange> findByOrderByEndDesc(Pageable pageable);
}
