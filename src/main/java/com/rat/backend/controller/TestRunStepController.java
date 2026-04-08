package com.rat.backend.controller;

import com.rat.backend.dto.request.TestRunStepRequest;
import com.rat.backend.dto.response.TestRunStepResponse;
import com.rat.backend.service.TestRunStepService;
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
@RequestMapping("/test-run-steps")
@RequiredArgsConstructor
public class TestRunStepController {

    private final TestRunStepService testRunStepService;

    @GetMapping
    public List<TestRunStepResponse> getAll() {
        return testRunStepService.getAll();
    }

    @GetMapping("/{id}")
    public TestRunStepResponse getById(@PathVariable Long id) {
        return testRunStepService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestRunStepResponse create(@RequestBody TestRunStepRequest request) {
        return testRunStepService.create(request);
    }

    @PutMapping("/{id}")
    public TestRunStepResponse update(@PathVariable Long id, @RequestBody TestRunStepRequest request) {
        return testRunStepService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testRunStepService.delete(id);
    }
}
