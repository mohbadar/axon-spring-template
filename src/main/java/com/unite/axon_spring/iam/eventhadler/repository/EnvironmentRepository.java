package com.unite.axon_spring.iam.eventhadler.repository;

import com.unite.axon_spring.iam.commandmodel.model.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, String> {

    public Environment findBySlug(String slug);
    public Environment findBySecretKey(String secretKey);
}