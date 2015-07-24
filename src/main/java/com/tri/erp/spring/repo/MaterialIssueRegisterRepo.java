package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.MaterialIssueRegister;
import com.tri.erp.spring.model.MaterialIssueRegisterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
public interface MaterialIssueRegisterRepo extends JpaRepository<MaterialIssueRegister, Integer> {
    @Query(value = "SELECT e.code FROM MaterialIssueRegister e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestMaterialIssueRegisterCodeByYear(@Param("year") Integer year);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "mir.code, " +
            "mir.particulars," +
            "created.fullName AS creator, " +
            "checked.fullName AS checker, " +
            "approved.fullName AS approvingOfficer, " +
            "docStat.status, " +
            "mir.voucherDate, " +
            "mir.id  FROM MaterialIssueRegister mir " +
            "JOIN User AS created ON created.id = mir.FK_createdByUserId " +
            "JOIN User AS checked ON checked.id = mir.FK_checkedByUserId " +
            "JOIN User AS approved ON approved.id = mir.FK_approvedByUserId " +
            "JOIN DocumentStatus as docStat ON docStat.id = mir.FK_documentStatusId", nativeQuery = true)
    public List<Objects[]> getAllMaterialIssueRegister();
}
