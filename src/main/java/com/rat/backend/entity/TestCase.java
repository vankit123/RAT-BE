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

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "test_cases")
public class TestCase extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "testCase")
    private List<TestCaseStep> steps = new ArrayList<>();

    @OneToMany(mappedBy = "testCase")
    private List<TestFlowCase> flowCases = new ArrayList<>();

    @OneToMany(mappedBy = "testCase")
    private List<TestCaseDataSet> caseDataSets = new ArrayList<>();

    @OneToMany(mappedBy = "testCase")
    private List<TestRun> testRuns = new ArrayList<>();

    @OneToMany(mappedBy = "testCase")
    private List<TestRunCase> testRunCases = new ArrayList<>();
}
