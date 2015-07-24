package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin.
 */
public interface AssetRepo extends JpaRepository<Asset, Integer> {
    List<Asset> findByRefNo(String refNo);
}
