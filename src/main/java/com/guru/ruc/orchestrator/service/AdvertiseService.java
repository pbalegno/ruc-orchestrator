package com.guru.ruc.orchestrator.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guru.ruc.orchestrator.domain.Advertise;

/**
 * Service Interface for managing {@link com.guru.ruc.orchestrator.domain.Advertise}.
 */
public interface AdvertiseService {

    /**
     * Save a advertise.
     *
     * @param Advertise the entity to save.
     * @return the persisted entity.
     */
    Advertise save(Advertise advertise);

    /**
     * Get all the advertises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Advertise> findAll(Pageable pageable);


    /**
     * Get the "id" advertise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Advertise> findOne(Long id);

    /**
     * Delete the "id" advertise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
