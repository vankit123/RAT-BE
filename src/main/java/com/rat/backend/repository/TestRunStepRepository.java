package com.rat.backend.repository;

import com.rat.backend.entity.TestRunStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRunStepRepository extends JpaRepository<TestRunStep, Long> {

    List<TestRunStep> findByTestRunCaseIdOrderByStepOrderAsc(Long testRunCaseId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TestRunStep step set step.testCaseStep = null where step.testCaseStep.id = :testCaseStepId")
    void unlinkTestCaseStep(@Param("testCaseStepId") Long testCaseStepId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TestRunStep step set step.testCaseStep = null where step.testCaseStep.testCase.id = :testCaseId")
    void unlinkTestCaseStepsByTestCaseId(@Param("testCaseId") Long testCaseId);
}
