package com.rat.backend.repository;

import com.rat.backend.entity.TestAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestAssetRepository extends JpaRepository<TestAsset, Long> {
}
