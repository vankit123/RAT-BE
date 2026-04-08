package com.rat.backend.controller;

import com.rat.backend.dto.request.TestFlowRequest;
import com.rat.backend.dto.response.TestFlowResponse;
import com.rat.backend.service.TestFlowService;
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
@RequestMapping("/test-flows")
@RequiredArgsConstructor
public class TestFlowController {

    private final TestFlowService testFlowService;

    @GetMapping
    public List<TestFlowResponse> getAll() {
        return testFlowService.getAll();
    }

    @GetMapping("/{id}")
    public TestFlowResponse getById(@PathVariable Long id) {
        return testFlowService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestFlowResponse create(@RequestBody TestFlowRequest request) {
        return testFlowService.create(request);
    }

    @PutMapping("/{id}")
    public TestFlowResponse update(@PathVariable Long id, @RequestBody TestFlowRequest request) {
        return testFlowService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testFlowService.delete(id);
    }
}
