package com.unite.axon_spring.auth.repository;

import com.unite.axon_spring.auth.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    public List<Group> findByEnvSlug(String envSlug);
    public List<Group> findByIdNotIn(List<Long> groupIds);
}
