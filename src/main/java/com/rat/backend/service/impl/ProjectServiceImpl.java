package com.rat.backend.service.impl;

import com.rat.backend.dto.request.ProjectRequest;
import com.rat.backend.dto.response.ProjectResponse;
import com.rat.backend.entity.Project;
import com.rat.backend.exception.ResourceNotFoundException;
import com.rat.backend.mapper.EntityMapper;
import com.rat.backend.repository.ProjectRepository;
import com.rat.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAll() {
        return projectRepository.findAll().stream().map(EntityMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getById(Long id) {
        return EntityMapper.toResponse(findProject(id));
    }

    @Override
    public ProjectResponse create(ProjectRequest request) {
        Project project = new Project();
        apply(project, request);
        return EntityMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = findProject(id);
        apply(project, request);
        return EntityMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public void delete(Long id) {
        projectRepository.delete(findProject(id));
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    private void apply(Project project, ProjectRequest request) {
        project.setCode(request.code());
        project.setName(request.name());
        project.setDescription(request.description());
        project.setBaseUrl(request.baseUrl());
        project.setStatus(request.status());
    }
}
