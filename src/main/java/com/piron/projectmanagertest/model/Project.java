package com.piron.projectmanagertest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Code is mandatory")
    @Size(max = 50, message = "Code must be less than 50 characters")
    private String code;

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

    @NotBlank(message = "Status is mandatory")
    @Pattern(regexp = "New|In Progress|Deleted", message = "Status must be one of: New, In Progress, Deleted")
    private String status;

    @Version
    @Column(name = "version")
    private Integer version;

    @ElementCollection
    @CollectionTable(name = "project_sections", schema = "project", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "section")
    private List<String> sections;
}
