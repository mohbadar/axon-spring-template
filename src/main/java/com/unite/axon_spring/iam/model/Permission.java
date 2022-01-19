package com.unite.axon_spring.iam.model;

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
@Entity(name = "Permission")
@Table(name = "permission")
public class Permission extends BaseEntity{

    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Collection<Role> roles;

    public Permission(String name) {
        this.name = name;
    }
    public Permission(String id, String name)
    {
        this.id = id;
        this.name= name;
    }

    public Permission(String id, String name, String description, boolean active) {

    }

    @Override
	public String toString() {
        return "Form [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active + "]";
	}
}