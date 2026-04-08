package com.rat.backend.repository;

import com.rat.backend.entity.TestCaseStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestCaseStepRepository extends JpaRepository<TestCaseStep, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TestCaseStep step where step.testCase.id = :testCaseId")
    void deleteByTestCaseId(@Param("testCaseId") Long testCaseId);
}
