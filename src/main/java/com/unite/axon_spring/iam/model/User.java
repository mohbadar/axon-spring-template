package com.unite.axon_spring.iam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "User")
@Table(name = "user_tbl", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User extends BaseEntity{

    @Column
    private String name;

    @Column
    private String address;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column
    private String phoneNo;

    @Column(unique = true)
    private String username;

    // @Pattern( regexp = "(?=.*[0-9]).+" ),
    // @Pattern( regexp = "(?=.*[a-z]).+" ),
    // @Pattern( regexp = "(?=.*[A-Z]).+"),
    // @Pattern( regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+"),
    // @Pattern( regexp = "(?=\\S+$).+" )
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String odkPassword;

    @Transient
    private boolean hasOdkPassword;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @Column
    private String email;

    @Column
    private String preferences;

    @Column
    private String avatar;

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

    // public User(String name, String email, String address) {
    // this.name = name;
    // this.email = email;
    // this.address = address;
    // }
    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public User(String id, String name, String address, String username, boolean active, String odkPassword, String email,
            String avatar, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.username = username;
        this.active = active;
        this.email = email;

        this.hasOdkPassword = false;
        if (odkPassword != null && odkPassword.length() > 0) {
            this.hasOdkPassword = true;
        }
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @JsonIgnore
    public Boolean isMatchingPasswords() {
        return this.password.equals(this.confirmPassword);
    }

    @ManyToOne
    @JoinColumn(name="profession_id", nullable = true)
    @JsonIgnore
    private Profession profession;


    @Override
    public String toString() {
        return "User [name=" + name + ", active=" + active + ", email=" + email + ", created_at=" + createdAt
                + ", updated_at=" + updatedAt + "]";
    }
}