package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 11/11/2014.
 */
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    public Employee findOneByAccountNumber(Integer accountNo);
}
