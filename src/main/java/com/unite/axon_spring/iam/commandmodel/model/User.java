package com.unite.axon_spring.iam.commandmodel.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "User")
@Table(name = "user_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User extends BaseEntity{

    @Column
    @NotNull
    @Size(min=3, message="Name should have at least 3 characters")
    private String name;

    @Column
    private String address;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(unique = true)
    @NotNull
    @Size(min=3, message="PhoneNo should have at least 3 characters")
    private String phoneNo;

    @Column(unique = true)
    @NotNull
    @Size(min=3, message="Username should have at least 3 characters")
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min=8, message="Password should have at least 8 characters")
    private String password;

    @Column()
    @Email
    private String email;
    @Column
	private Boolean deleted;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@Column(name = "deleted_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	private LocalDateTime deletedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // @Column(name = "token_expired", length = 1, nullable = false)
    // private boolean tokenExpired;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Group> groups = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "environment_user", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "environment_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Environment> environments = new HashSet<>();

    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public User(String id, String name, String address, String username, boolean active, String email
            , LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.username = username;
        this.active = active;
        this.email = email;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    @ManyToOne
    @JoinColumn(name="profession_id", nullable = true)
    @JsonIgnore
    private Profession profession;

}
