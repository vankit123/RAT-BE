package com.rat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "test_case_steps")
public class TestCaseStep extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    private String target;

    @Column(columnDefinition = "text")
    private String value;

    @Column(name = "expected_value", columnDefinition = "text")
    private String expectedValue;

    @Column(columnDefinition = "text")
    private String description;
}
