package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Menu;
import com.tri.erp.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */
public interface MenuRepo extends JpaRepository<Menu, Integer> {

    @Query(value = "SELECT " +
            "m.* " +
            "FROM Menu m " +
            "JOIN RoleMenu ON m.id = RoleMenu.FK_menuId " +
            "JOIN UserRole ON RoleMenu.FK_roleId = UserRole.FK_roleId " +
            "WHERE FK_userId = ?1 GROUP BY m.id", nativeQuery = true)
    public List<Menu> findAllByUserId(Integer userId);
}
