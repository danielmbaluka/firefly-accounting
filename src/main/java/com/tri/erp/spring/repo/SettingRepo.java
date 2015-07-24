package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TSI Admin on 6/25/2015.
 */
public interface SettingRepo extends JpaRepository<Setting, Integer> {
    public Setting findOneByCode(String code);
}
