package com.unite.axon_spring.iam.commandmodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Role")
@Table(name="role")
public class Role extends BaseEntity{

	@Column
	@NotNull
	@Size(min=3, message="Name should have at least 3 characters")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "active", length = 1, nullable = false)
    private boolean active;

	@Column(name = "env_slug")
	@NotNull
	@Size(min=2, message="envSlug should have at least 2 characters")
	private String envSlug;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
    private Collection<Group> groups;

	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "role_permission", 
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Permission> permissions; 
    
    public Role(String id,String name,String desc, Collection<Permission> permissions)
    {
    	this.id=id;
    	this.name=name;
    	this.description=desc;
    	this.permissions=permissions;
    }
    
	public Role(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
        return "Form [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active + ", envSlug=" + envSlug + "]";
	}
}
