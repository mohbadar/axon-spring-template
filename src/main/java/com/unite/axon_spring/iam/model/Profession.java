package com.unite.axon_spring.iam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Profession")
@Table(name = "profession")
public class Profession extends BaseEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "remark")
    private String remark;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "env_slug")
    private String envSlug;
    
//    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
//    @JoinColumn(name = "job_id")
//    private Collection<User> users;
}