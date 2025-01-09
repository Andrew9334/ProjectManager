package com.piron.projectmanagertest;

import com.piron.projectmanagertest.dto.ProjectDTO;
import com.piron.projectmanagertest.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProjectService projectService;

    @Test
    void testGetProjectById() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project A");
        projectDTO.setCode("PA001");

        when(projectService.getProjectById(1L)).thenReturn(projectDTO);

        String url = "http://localhost:" + port + "/api/projects/1";

        var response = this.restTemplate.withBasicAuth("user", "password")
                .getForEntity(url, ProjectDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Project A");
    }
}
