package com.piron.projectmanagertest.repository;

import com.piron.projectmanagertest.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
