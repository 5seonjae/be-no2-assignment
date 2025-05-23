package com.example.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("해당 ID(" + id + ")의 일정이 존재하지 않습니다.");
    }
}
