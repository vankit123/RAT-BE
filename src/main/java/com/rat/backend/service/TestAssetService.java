package com.rat.backend.service;

import com.rat.backend.dto.response.TestAssetResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface TestAssetService {

    TestAssetResponse upload(Long projectId, MultipartFile file);

    TestAssetResponse getById(Long id);

    Resource download(Long id);

    Path resolveStoredFilePath(Long id);
}
