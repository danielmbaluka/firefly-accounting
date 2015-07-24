package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Canvass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Personal on 5/12/2015.
 */
public interface CanvassRepo extends JpaRepository<Canvass, Integer> {
    public List<Canvass> findByCode(String code);
    @Query(value = "SELECT e.code FROM Canvass e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestCanvassCodeByYear(@Param("year") Integer year);
}
