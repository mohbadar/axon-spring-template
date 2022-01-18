package com.unite.axon_spring.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
// @ToString(exclude="roles")
// @EqualsAndHashCode
@Entity(name = "Group")
@Table(name="group_tbl")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_tbl_generator")
    @SequenceGenerator(name="group_tbl_generator", sequenceName = "group_tbl_seq", allocationSize = 1)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column
    private String description;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "env_slug")
    private String envSlug;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "group_role",
            joinColumns = @JoinColumn( name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Role> roles;

    public Group(Long id, String name, String description, boolean active, String envSlug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.envSlug = envSlug;
    }

    @Override
    public String toString() {
        return "Form [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active + ", envSlug=" + envSlug + "]";
    }
}
