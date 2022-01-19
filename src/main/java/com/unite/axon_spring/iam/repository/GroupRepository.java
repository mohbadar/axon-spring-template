package com.unite.axon_spring.iam.repository;

import com.unite.axon_spring.iam.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    public List<Group> findByEnvSlug(String envSlug);
    public List<Group> findByIdNotIn(List<String> groupIds);
}
