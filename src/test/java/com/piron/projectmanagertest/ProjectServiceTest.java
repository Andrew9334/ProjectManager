package com.piron.projectmanagertest;

import com.piron.projectmanagertest.dto.ProjectDTO;
import com.piron.projectmanagertest.exception.ProjectNotFoundException;
import com.piron.projectmanagertest.model.Project;
import com.piron.projectmanagertest.repository.ProjectRepository;
import com.piron.projectmanagertest.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProjectByIdNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProjectNotFoundException.class, () -> {
            projectService.getProjectById(1L);
        });

        assertEquals("Project with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testCreateProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");
        projectDTO.setCode("NP001");
        projectDTO.setStartDate(LocalDate.now());
        projectDTO.setEndDate(LocalDate.now().plusDays(10));
        projectDTO.setStatus("New");

        Project project = new Project();
        project.setId(1L);
        project.setName(projectDTO.getName());
        project.setCode(projectDTO.getCode());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus(projectDTO.getStatus());

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO createdProject = projectService.createProject(projectDTO);

        assertNotNull(createdProject);
        assertEquals("New Project", createdProject.getName());
        assertEquals("NP001", createdProject.getCode());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProject() {
        Project existingProject = new Project();
        existingProject.setId(1L);
        existingProject.setName("Old Project");
        existingProject.setCode("OP001");
        existingProject.setStartDate(LocalDate.now());
        existingProject.setEndDate(LocalDate.now().plusDays(5));
        existingProject.setStatus("In Progress");

        ProjectDTO updatedDetails = new ProjectDTO();
        updatedDetails.setName("Updated Project");
        updatedDetails.setCode("UP001");
        updatedDetails.setStartDate(LocalDate.now());
        updatedDetails.setEndDate(LocalDate.now().plusDays(10));
        updatedDetails.setStatus("Completed");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectDTO updatedProject = projectService.updateProject(1L, updatedDetails);

        assertNotNull(updatedProject);
        assertEquals("Updated Project", updatedProject.getName());
        assertEquals("UP001", updatedProject.getCode());
        assertEquals("Completed", updatedProject.getStatus());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testDeleteProject() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).existsById(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }
}

