package com.unite.axon_spring.iam.commandmodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

// @Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
// @ToString(exclude="roles")
// @EqualsAndHashCode
@Entity(name = "Group")
@Table(name="group_tbl")
public class Group extends BaseEntity{
 
	@Column(name = "name")
	@NotNull
	@Size(min=3, message="Name should have at least 3 characters")
	private String name;
	
	@Column
	private String description;
	
	@Column(name = "active", length = 1, nullable = false)
    private boolean active;
	
	@Column(name = "env_slug")
	@NotNull
	@Size(min=2, message="EnvSlug should have at least 2 characters")
	private String envSlug;
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "group_role",
    	joinColumns = @JoinColumn( name = "group_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@JsonIgnore
	private Collection<Role> roles;

	public Group(String id, String name, String description, boolean active, String envSlug) {
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
