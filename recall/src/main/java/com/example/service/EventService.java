package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.models.Event;
import com.example.models.Picture;
import com.example.repository.EventRepository;
import com.example.controller.dto.EventCreateRequest;

@Service
public class EventService {

    final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);

    }

    public void createEvent(EventCreateRequest eventdata, String creatorUsername) {
        if (creatorUsername == null || creatorUsername.isBlank()) {
            throw new RuntimeException("Authentication required to create an event");
        }
        Event newEvent = new Event(
                eventdata.name(),
                eventdata.date(),
                creatorUsername,
                eventdata.startTime(),
                eventdata.endTime(),
                eventdata.location());
        eventRepository.save(newEvent);
    }

    public void addPictureToEvent(String eventName, String pictureUrl, String createdByUsername) {
        Event event = eventRepository.findByName(eventName);
        Picture picture = new Picture(pictureUrl, "", createdByUsername);
        if (event != null) {
            event.getPictures().add(picture);
            eventRepository.save(event);
        }
    }

    public void addCurrentUserToEvent(String eventName, String username) {
        Event event = eventRepository.findByName(eventName);
        if (event != null && !event.getUsernames().contains(username)) {
            event.getUsernames().add(username);
            eventRepository.save(event);
        }
    }

    public void deleteEventByIdForUser(Long eventId, String username) {
        if (username == null || username.isBlank()) {
            throw new RuntimeException("Authentication required to delete an event");
        }
        final var event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            throw new RuntimeException("Event not found");
        }
        if (!username.equals(event.getHostName())) {
            throw new RuntimeException("You are not allowed to delete this event");
        }
        eventRepository.deleteById(eventId);
    }

}
