package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleListResponseDto {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleListResponseDto(Long id, String title, String writer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
