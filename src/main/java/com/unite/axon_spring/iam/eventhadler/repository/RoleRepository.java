package com.unite.axon_spring.iam.eventhadler.repository;

import com.unite.axon_spring.iam.commandmodel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Role findByName(String name);
	public List<Role> findByEnvSlug(String envSlug);
}
