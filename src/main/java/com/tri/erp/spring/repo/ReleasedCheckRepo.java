package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.ReleasedCheque;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TSI Admin on 6/19/2015.
 */
public interface ReleasedCheckRepo extends JpaRepository<ReleasedCheque, Integer> {
}
