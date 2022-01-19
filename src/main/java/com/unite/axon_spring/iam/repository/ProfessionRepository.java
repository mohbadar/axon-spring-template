/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unite.axon_spring.iam.repository;

import com.unite.axon_spring.iam.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author hp 2018
 */
@Repository
public interface ProfessionRepository extends JpaRepository<Profession, String> {
    // @Query("SELECT * FROM public.job j where j.user.id = ?1")
    public java.util.Optional<Profession> findById(String id);
    
    // @Query("SELECT DISTINCT j.title FROM public.job j where j.envSlug = ?1")
    // List<String> findDistinctJobs(String envSlug);

    // @Query("SELECT * FROM public.job j where j.envSlug = ?1")
    public List<Profession> findByEnvSlug(String envSlug);
}
