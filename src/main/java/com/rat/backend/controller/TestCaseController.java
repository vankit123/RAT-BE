package com.rat.backend.controller;

import com.rat.backend.dto.request.TestCaseRequest;
import com.rat.backend.dto.response.TestCaseResponse;
import com.rat.backend.service.TestCaseService;
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
@RequestMapping("/test-cases")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @GetMapping
    public List<TestCaseResponse> getAll() {
        return testCaseService.getAll();
    }

    @GetMapping("/{id}")
    public TestCaseResponse getById(@PathVariable Long id) {
        return testCaseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestCaseResponse create(@RequestBody TestCaseRequest request) {
        return testCaseService.create(request);
    }

    @PutMapping("/{id}")
    public TestCaseResponse update(@PathVariable Long id, @RequestBody TestCaseRequest request) {
        return testCaseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testCaseService.delete(id);
    }
}
