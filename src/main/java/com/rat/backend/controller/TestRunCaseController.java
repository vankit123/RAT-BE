package com.rat.backend.controller;

import com.rat.backend.dto.request.TestRunCaseRequest;
import com.rat.backend.dto.response.TestRunCaseResponse;
import com.rat.backend.service.TestRunCaseService;
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
@RequestMapping("/test-run-cases")
@RequiredArgsConstructor
public class TestRunCaseController {

    private final TestRunCaseService testRunCaseService;

    @GetMapping
    public List<TestRunCaseResponse> getAll() {
        return testRunCaseService.getAll();
    }

    @GetMapping("/{id}")
    public TestRunCaseResponse getById(@PathVariable Long id) {
        return testRunCaseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestRunCaseResponse create(@RequestBody TestRunCaseRequest request) {
        return testRunCaseService.create(request);
    }

    @PutMapping("/{id}")
    public TestRunCaseResponse update(@PathVariable Long id, @RequestBody TestRunCaseRequest request) {
        return testRunCaseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testRunCaseService.delete(id);
    }
}
