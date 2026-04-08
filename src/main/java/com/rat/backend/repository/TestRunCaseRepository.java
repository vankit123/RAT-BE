package com.rat.backend.repository;

import com.rat.backend.entity.TestRunCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRunCaseRepository extends JpaRepository<TestRunCase, Long> {

    List<TestRunCase> findByTestRunIdOrderByIdAsc(Long testRunId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TestRunCase runCase set runCase.testDataSet = null where runCase.testDataSet.id = :testDataSetId")
    void unlinkTestDataSet(@Param("testDataSetId") Long testDataSetId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TestRunCase runCase set runCase.testCase = null where runCase.testCase.id = :testCaseId")
    void unlinkTestCase(@Param("testCaseId") Long testCaseId);
}
