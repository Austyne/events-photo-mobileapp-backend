package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.controller.dto.PictureRequest;
import com.example.models.Event;
import com.example.controller.dto.EventCreateRequest;
import com.example.service.EventService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/events")
public class EventController {
    final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping()
    public void createEvent(@Valid @RequestBody EventCreateRequest entityData, Authentication authentication) {
        final String username = authentication != null ? authentication.getName() : null;
        eventService.createEvent(entityData, username);
    }

    @PostMapping("/{event}/add-picture")
    public void addPictureToEvent(@PathVariable("event") String eventName,
            @RequestBody PictureRequest request,
            Authentication authentication) {
        final String username = authentication != null ? authentication.getName() : null;
        try {
            eventService.addPictureToEvent(eventName, request.getUrl(), username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/{eventName}/join")
    public void addCurrentUserToEvent(@PathVariable String eventName, @RequestBody String username) {
        eventService.addCurrentUserToEvent(eventName, username);
    }

    @DeleteMapping("/{name}")
    public void deleteEvent(@PathVariable("name") String name, Authentication authentication) {
        final String username = authentication != null ? authentication.getName() : null;
        eventService.deleteEventByNameForUser(name, username);
    }

}
