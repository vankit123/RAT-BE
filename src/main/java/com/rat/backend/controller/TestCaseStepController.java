package com.rat.backend.controller;

import com.rat.backend.dto.request.TestCaseStepRequest;
import com.rat.backend.dto.response.TestCaseStepResponse;
import com.rat.backend.service.TestCaseStepService;
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
@RequestMapping("/test-case-steps")
@RequiredArgsConstructor
public class TestCaseStepController {

    private final TestCaseStepService testCaseStepService;

    @GetMapping
    public List<TestCaseStepResponse> getAll() {
        return testCaseStepService.getAll();
    }

    @GetMapping("/{id}")
    public TestCaseStepResponse getById(@PathVariable Long id) {
        return testCaseStepService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestCaseStepResponse create(@RequestBody TestCaseStepRequest request) {
        return testCaseStepService.create(request);
    }

    @PutMapping("/{id}")
    public TestCaseStepResponse update(@PathVariable Long id, @RequestBody TestCaseStepRequest request) {
        return testCaseStepService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testCaseStepService.delete(id);
    }
}
