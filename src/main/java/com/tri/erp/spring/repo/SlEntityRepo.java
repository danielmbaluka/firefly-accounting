package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.SlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface SlEntityRepo extends JpaRepository<SlEntity, Integer>{

    @Transactional
    public List<SlEntity> findAllByOrderByNameAsc();

    @Transactional
    @Query(value = "SELECT e FROM SlEntity e WHERE marker IN :markers ORDER BY name")
    public List<SlEntity> findByMarkers(@Param("markers") List<Integer> markers);
}
