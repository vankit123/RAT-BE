package com.rat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "test_run_steps")
public class TestRunStep extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_run_case_id", nullable = false)
    private TestRunCase testRunCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_step_id")
    private TestCaseStep testCaseStep;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    private String target;

    @Column(columnDefinition = "text")
    private String value;

    @Column(name = "expected_value", columnDefinition = "text")
    private String expectedValue;

    @Column(name = "actual_value", columnDefinition = "text")
    private String actualValue;

    @Column(nullable = false)
    private String status;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "error_message", columnDefinition = "text")
    private String errorMessage;
}
