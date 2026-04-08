package com.rat.backend.service;

import com.rat.backend.dto.request.TestRunRequest;
import com.rat.backend.dto.response.TestRunDetailResponse;
import com.rat.backend.dto.response.TestRunResponse;

public interface TestRunService extends CrudService<TestRunRequest, TestRunResponse> {

    TestRunResponse getLatestByProjectId(Long projectId);

    TestRunDetailResponse getLatestDetailByProjectId(Long projectId);
}
