package com.guru.ruc.orchestrator.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guru.ruc.orchestrator.domain.Advertise;
import com.guru.ruc.orchestrator.repository.AdvertiseRepository;
import com.guru.ruc.orchestrator.service.AdvertiseService;

/**
 * Service Implementation for managing {@link Advertise}.
 */
@Service
@Transactional
public class AdvertiseServiceImpl implements AdvertiseService {

    private final Logger log = LoggerFactory.getLogger(AdvertiseServiceImpl.class);

    private final AdvertiseRepository advertiseRepository;

    public AdvertiseServiceImpl(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    /**
     * Save a person.
     *
     * @param personDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Advertise save(Advertise advertise) {
        log.debug("Request to save Advertise : {}", advertise);
        return  advertiseRepository.save(advertise);
    }

    /**
     * Get all the advertises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Advertise> findAll(Pageable pageable) {
        log.debug("Request to get all advertises");
        return advertiseRepository.findAll(pageable);
    }


    /**
     * Get one person by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Advertise> findOne(Long id) {
        log.debug("Request to get Advertise : {}", id);
        return advertiseRepository.findById(id);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Advertise : {}", id);
        advertiseRepository.deleteById(id);
    }
}
