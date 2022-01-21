package com.unite.axon_spring.iam.commandmodel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Profession")
@Table(name = "profession")
public class Profession extends BaseEntity{

    @Column(name = "title")
    @NotNull
    @Size(min=3, message="Title should have at least 3 characters")
    private String title;

    @Column(name = "remark")
    private String remark;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "env_slug")
    @NotNull
    @Size(min=2, message="EnvSlug should have at least 2 characters")
    private String envSlug;
    
//    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
//    @JoinColumn(name = "job_id")
//    private Collection<User> users;
}