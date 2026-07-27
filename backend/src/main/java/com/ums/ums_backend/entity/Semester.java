package com.ums.ums_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Semester {

    @Id
    private Long id;

    private String term;

    private Integer year;

    @OneToMany(mappedBy = "semester")
    private List<Enrollment> enrollments;

}
