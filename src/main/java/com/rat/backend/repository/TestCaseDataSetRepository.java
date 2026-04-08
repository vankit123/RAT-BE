package com.rat.backend.repository;

import com.rat.backend.entity.TestCaseDataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestCaseDataSetRepository extends JpaRepository<TestCaseDataSet, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TestCaseDataSet caseDataSet where caseDataSet.testDataSet.id = :testDataSetId")
    void deleteByTestDataSetId(@Param("testDataSetId") Long testDataSetId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TestCaseDataSet caseDataSet where caseDataSet.testCase.id = :testCaseId")
    void deleteByTestCaseId(@Param("testCaseId") Long testCaseId);
}
