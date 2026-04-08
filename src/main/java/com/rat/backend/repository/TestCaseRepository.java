package com.rat.backend.repository;

import com.rat.backend.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
