package com.example.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleUpdateRequestDto {
    private String title;
    private String writer;
    private String password;
}
