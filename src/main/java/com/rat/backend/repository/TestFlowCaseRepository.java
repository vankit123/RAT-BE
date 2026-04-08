package com.rat.backend.repository;

import com.rat.backend.entity.TestFlowCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestFlowCaseRepository extends JpaRepository<TestFlowCase, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TestFlowCase flowCase where flowCase.testCase.id = :testCaseId")
    void deleteByTestCaseId(@Param("testCaseId") Long testCaseId);
}
