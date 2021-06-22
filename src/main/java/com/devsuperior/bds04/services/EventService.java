package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.exceptions.ResourceNotFoundException;
import com.devsuperior.bds04.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        Page<Event> events = repository.findAll(pageable);
        return events.map(EventDTO::new);
    }

    @Transactional
    public EventDTO insert(EventDTO dto) {
        Event entity = new Event(dto);
        entity = repository.save(entity);
        return new EventDTO(entity);
    }

    public EventDTO update(Long id, EventDTO eventDTO) {
        try {
            Event event = repository.getOne(id);

            event.setName(eventDTO.getName());
            event.setDate(eventDTO.getDate());
            event.setUrl(eventDTO.getUrl());
            event.setCity(new City(eventDTO.getCityId(), null));

            event = repository.save(event);

            return new EventDTO(event);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Não encontramos registros desse evento");
        }
    }
}
