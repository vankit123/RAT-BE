package com.rat.backend.controller;

import com.rat.backend.dto.request.TestFlowCaseRequest;
import com.rat.backend.dto.response.TestFlowCaseResponse;
import com.rat.backend.service.TestFlowCaseService;
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
@RequestMapping("/test-flow-cases")
@RequiredArgsConstructor
public class TestFlowCaseController {

    private final TestFlowCaseService testFlowCaseService;

    @GetMapping
    public List<TestFlowCaseResponse> getAll() {
        return testFlowCaseService.getAll();
    }

    @GetMapping("/{id}")
    public TestFlowCaseResponse getById(@PathVariable Long id) {
        return testFlowCaseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestFlowCaseResponse create(@RequestBody TestFlowCaseRequest request) {
        return testFlowCaseService.create(request);
    }

    @PutMapping("/{id}")
    public TestFlowCaseResponse update(@PathVariable Long id, @RequestBody TestFlowCaseRequest request) {
        return testFlowCaseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testFlowCaseService.delete(id);
    }
}
