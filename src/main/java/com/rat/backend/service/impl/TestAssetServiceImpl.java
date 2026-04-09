package com.rat.backend.service.impl;

import com.rat.backend.dto.response.TestAssetResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.entity.TestAsset;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.exception.StorageException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.ProjectRepository;
import com.rat.backend.repository.TestAssetRepository;
import com.rat.backend.service.TestAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.YearMonth;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TestAssetServiceImpl implements TestAssetService {

    private final TestAssetRepository testAssetRepository;
    private final ProjectRepository projectRepository;

    @Value("${app.storage.test-assets-root:storage/test-assets}")
    private String testAssetsRoot;

    @Override
    public TestAssetResponse upload(Long projectId, MultipartFile file) {
        if (projectId == null) {
            throw new IllegalArgumentException("projectId is required");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("file is required");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + projectId));

        String originalName = sanitizeOriginalName(file.getOriginalFilename());
        String storedName = buildStoredName(originalName);
        String relativePath = buildRelativePath(projectId, storedName);
        Path targetPath = storageRoot().resolve(relativePath).normalize().toAbsolutePath();

        try {
            Files.createDirectories(targetPath.getParent());
            byte[] content = file.getBytes();
            Files.write(targetPath, content);

            TestAsset asset = new TestAsset();
            asset.setProject(project);
            asset.setOriginalName(originalName);
            asset.setStoredName(storedName);
            asset.setStoredPath(relativePath.replace('\\', '/'));
            asset.setMimeType(resolveMimeType(file));
            asset.setSizeBytes(file.getSize());
            asset.setChecksum(sha256Hex(content));

            return EntityMapper.toResponse(testAssetRepository.save(asset));
        } catch (IOException ex) {
            throw new StorageException("Failed to store test asset: " + originalName, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TestAssetResponse getById(Long id) {
        return EntityMapper.toResponse(findAsset(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Resource download(Long id) {
        Path filePath = resolveStoredFilePath(id);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceNotFoundException("Test asset file not found: " + id);
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new StorageException("Failed to load test asset: " + id, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Path resolveStoredFilePath(Long id) {
        TestAsset asset = findAsset(id);
        Path filePath = storageRoot().resolve(asset.getStoredPath()).normalize().toAbsolutePath();
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("Test asset file not found: " + id);
        }
        return filePath;
    }

    private TestAsset findAsset(Long id) {
        return testAssetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test asset not found: " + id));
    }

    private Path storageRoot() {
        return Paths.get(testAssetsRoot).normalize().toAbsolutePath();
    }

    private String sanitizeOriginalName(String originalFilename) {
        String fileName = StringUtils.hasText(originalFilename) ? originalFilename : "file";
        String cleaned = StringUtils.cleanPath(fileName).replace("\\", "/");
        int lastSlash = cleaned.lastIndexOf('/');
        String onlyName = lastSlash >= 0 ? cleaned.substring(lastSlash + 1) : cleaned;
        return onlyName.replaceAll("[^A-Za-z0-9._-]", "_");
    }

    private String buildStoredName(String originalName) {
        return UUID.randomUUID() + "-" + originalName;
    }

    private String buildRelativePath(Long projectId, String storedName) {
        YearMonth currentMonth = YearMonth.now();
        return Path.of(String.valueOf(projectId),
                        String.valueOf(currentMonth.getYear()),
                        "%02d".formatted(currentMonth.getMonthValue()),
                        storedName)
                .toString();
    }

    private String resolveMimeType(MultipartFile file) {
        return StringUtils.hasText(file.getContentType())
                ? file.getContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    private String sha256Hex(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(content));
        } catch (NoSuchAlgorithmException ex) {
            throw new StorageException("SHA-256 is not available", ex);
        }
    }
}
