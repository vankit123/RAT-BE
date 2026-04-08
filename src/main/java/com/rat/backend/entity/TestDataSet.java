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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "test_data_sets")
public class TestDataSet extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data_json", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> dataJson;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "expected_json", columnDefinition = "jsonb")
    private Map<String, Object> expectedJson;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "testDataSet")
    private List<TestCaseDataSet> caseDataSets = new ArrayList<>();
}
