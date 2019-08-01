package com.guru.ruc.orchestrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.guru.ruc.orchestrator.domain.Advertise;


/**
 * Spring Data  repository for the Person entity.
 */
@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long>, JpaSpecificationExecutor<Advertise> {

}
