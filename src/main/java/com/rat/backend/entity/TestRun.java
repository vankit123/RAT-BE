package com.rat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "test_runs")
public class TestRun extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_flow_id")
    private TestFlow testFlow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_data_set_id")
    private TestDataSet testDataSet;

    @Column(name = "run_type", nullable = false)
    private String runType;

    @Column(nullable = false)
    private String status;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "video_url")
    private String videoUrl;

    @OneToMany(mappedBy = "testRun")
    private List<TestRunCase> testRunCases = new ArrayList<>();
}
