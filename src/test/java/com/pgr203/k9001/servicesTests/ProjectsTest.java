package com.pgr203.k9001.servicesTests;

import com.pgr203.k9001.model.Project;
import com.pgr203.k9001.services.Projects;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static com.pgr203.k9001.util.DataSource.dataSourceSetup;
import static com.pgr203.k9001.util.Helpers.PickOne;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsTest {
    Projects projects;

    @Rule
    public PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest");

    @Before
    public void setUp() throws SQLException {
        Connection connection = dataSourceSetup(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        projects = new Projects(connection);
    }

    static Project generateProjectData() {
        Project project = new Project();
        project.setProjectName(PickOne(new String[]{"test1 test_1", "test2 test_2", "test3 test_3", "test4 test_4", "test5 test_5", "test6 test_6"}));
        return project;
    }

    @Test
    public void shouldProjectCreate() throws SQLException {
        Project project = generateProjectData();
        projects.create(project);
        assertThat(project.getId()).isNotZero();
    }

    @Test
    public void shouldReadCreatedProject() throws SQLException {
        Project project = generateProjectData();
        projects.create(project);
        assertThat(project.getId()).isNotZero();
        Optional<Project> createdProject = projects.read(project.getId());
        assertThat(createdProject.orElse(new Project()).getId()).isEqualTo(project.getId());
        assertThat(createdProject.orElse(new Project()).getProjectName()).isEqualTo(project.getProjectName());
    }
}
