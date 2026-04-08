package com.rat.backend.controller;

import com.rat.backend.dto.request.TestCaseDataSetRequest;
import com.rat.backend.dto.response.TestCaseDataSetResponse;
import com.rat.backend.service.TestCaseDataSetService;
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
@RequestMapping("/test-case-data-sets")
@RequiredArgsConstructor
public class TestCaseDataSetController {

    private final TestCaseDataSetService testCaseDataSetService;

    @GetMapping
    public List<TestCaseDataSetResponse> getAll() {
        return testCaseDataSetService.getAll();
    }

    @GetMapping("/{id}")
    public TestCaseDataSetResponse getById(@PathVariable Long id) {
        return testCaseDataSetService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestCaseDataSetResponse create(@RequestBody TestCaseDataSetRequest request) {
        return testCaseDataSetService.create(request);
    }

    @PutMapping("/{id}")
    public TestCaseDataSetResponse update(@PathVariable Long id, @RequestBody TestCaseDataSetRequest request) {
        return testCaseDataSetService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testCaseDataSetService.delete(id);
    }
}
