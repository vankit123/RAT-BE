package com.rat.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "project")
    private List<TestCase> testCases = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<TestFlow> testFlows = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<TestDataSet> testDataSets = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<TestAsset> testAssets = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<TestRun> testRuns = new ArrayList<>();
}
