package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.exceptions.DatabaseException;
import com.devsuperior.bds04.exceptions.ResourceNotFoundException;
import com.devsuperior.bds04.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        return repository.findAll(Sort.by("name"))
                .stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO dto) {
        City entity = new City(dto);
        entity = repository.save(entity);
        return new CityDTO(entity);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Não encontramos essa cidade.");
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Não é possível remover um cidade com eventos associados.");
        }
    }
}
