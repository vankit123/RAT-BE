package com.rat.backend.controller;

import com.rat.backend.dto.request.TestDataSetRequest;
import com.rat.backend.dto.response.TestDataSetResponse;
import com.rat.backend.service.TestDataSetService;
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
@RequestMapping("/test-data-sets")
@RequiredArgsConstructor
public class TestDataSetController {

    private final TestDataSetService testDataSetService;

    @GetMapping
    public List<TestDataSetResponse> getAll() {
        return testDataSetService.getAll();
    }

    @GetMapping("/{id}")
    public TestDataSetResponse getById(@PathVariable Long id) {
        return testDataSetService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestDataSetResponse create(@RequestBody TestDataSetRequest request) {
        return testDataSetService.create(request);
    }

    @PutMapping("/{id}")
    public TestDataSetResponse update(@PathVariable Long id, @RequestBody TestDataSetRequest request) {
        return testDataSetService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testDataSetService.delete(id);
    }
}
