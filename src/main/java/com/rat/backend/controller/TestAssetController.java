package com.rat.backend.controller;

import com.rat.backend.dto.response.TestAssetResponse;
import com.rat.backend.service.TestAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/test-assets")
@RequiredArgsConstructor
public class TestAssetController {

    private final TestAssetService testAssetService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TestAssetResponse upload(@RequestParam Long projectId, @RequestParam MultipartFile file) {
        return testAssetService.upload(projectId, file);
    }

    @GetMapping("/{id}")
    public TestAssetResponse getById(@PathVariable Long id) {
        return testAssetService.getById(id);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        TestAssetResponse asset = testAssetService.getById(id);
        Resource resource = testAssetService.download(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(asset.originalName(), StandardCharsets.UTF_8)
                        .build()
                        .toString())
                .contentType(MediaType.parseMediaType(asset.mimeType()))
                .contentLength(asset.sizeBytes())
                .body(resource);
    }
}
