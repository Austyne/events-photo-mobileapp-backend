package com.example.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventCreateRequest(
        @NotBlank(message = "Event name cannot be blank") String name,
        @FutureOrPresent(message = "Event date cannot be in the past") @NotNull(message = "Event date cannot be null") LocalDate date,
        @NotNull(message = "Event start time cannot be null") LocalTime startTime,
        @NotNull(message = "Event end time cannot be null") LocalTime endTime,
        String location) {
}
