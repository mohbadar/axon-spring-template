package com.unite.axon_spring.iam.commandmodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "environment")
public class Environment extends BaseEntity{
	
	@Column(name = "slug", unique = true)
	@NotNull
	@Size(min=2, message="Slug should have at least 2 characters")
	private String slug;
 
	@Column(name = "name")
	@NotNull
	@Size(min=3, message="Name should have at least 3 characters")
	private String name;
 
	@Column(name = "description")
	private String description;

	@Column(name = "secret_key", unique = true)
	@NotNull
	@Size(min=3, message="SecrectKey should have at least 3 characters")
	private String secretKey;
 
	@Column(name = "active")
	private boolean active;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@ManyToMany(mappedBy = "environments")
    @JsonIgnore
	private Set<User> users;
 
	public Environment(String name, String description) {
		this.name = name;
		this.description = description;
		this.active = false;
	}

	@Override
	public String toString() {
        return "Form [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active + ", envSlug=" + slug + ", created_at=" + createdAt  + ", updated_at=" + updatedAt + "]";
	}
}