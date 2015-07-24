package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.model.BusinessSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface BusinessSegmentRepo extends JpaRepository<BusinessSegment, Integer> {
}
