package com.rat.backend.repository;

import com.rat.backend.entity.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {

    Optional<TestRun> findTopByProjectIdOrderByCreatedAtDesc(Long projectId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TestRun run set run.testDataSet = null where run.testDataSet.id = :testDataSetId")
    void unlinkTestDataSet(@Param("testDataSetId") Long testDataSetId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TestRun run set run.testCase = null where run.testCase.id = :testCaseId")
    void unlinkTestCase(@Param("testCaseId") Long testCaseId);
}
