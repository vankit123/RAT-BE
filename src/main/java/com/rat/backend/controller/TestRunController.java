package com.rat.backend.controller;

import com.rat.backend.dto.request.TestRunRequest;
import com.rat.backend.dto.response.TestRunDetailResponse;
import com.rat.backend.dto.response.TestRunResponse;
import com.rat.backend.service.TestRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test-runs")
@RequiredArgsConstructor
public class TestRunController {

    private final TestRunService testRunService;

    @GetMapping
    public List<TestRunResponse> getAll() {
        return testRunService.getAll();
    }

    @GetMapping("/{id}")
    public TestRunResponse getById(@PathVariable Long id) {
        return testRunService.getById(id);
    }

    @GetMapping("/projects/{projectId}/latest")
    public TestRunResponse getLatestByProjectId(@PathVariable Long projectId) {
        return testRunService.getLatestByProjectId(projectId);
    }

    @GetMapping("/projects/{projectId}/latest/detail")
    public TestRunDetailResponse getLatestDetailByProjectId(@PathVariable Long projectId) {
        return testRunService.getLatestDetailByProjectId(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestRunResponse create(@RequestBody TestRunRequest request) {
        return testRunService.create(request);
    }

    @PutMapping("/{id}")
    public TestRunResponse update(@PathVariable Long id, @RequestBody TestRunRequest request) {
        return testRunService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testRunService.delete(id);
    }
}
