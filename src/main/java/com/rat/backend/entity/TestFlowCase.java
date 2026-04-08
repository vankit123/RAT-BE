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
@Table(name = "test_flow_cases")
public class TestFlowCase extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_flow_id", nullable = false)
    private TestFlow testFlow;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Column(name = "run_order", nullable = false)
    private Integer runOrder;

    @Column(name = "is_required", nullable = false)
    private Boolean required;

    @Column(name = "stop_on_failure", nullable = false)
    private Boolean stopOnFailure;
}
