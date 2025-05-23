package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        ScheduleResponseDto response = scheduleService.createSchedule(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleListResponseDto>> getSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String modifiedAt,
            @RequestParam(required = false, defaultValue = "desc") String sort) {

        List<ScheduleListResponseDto> schedules = scheduleService.getAllSchedules(writer, modifiedAt, sort);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleListResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleListResponseDto response = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleListResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto dto) {
        ScheduleListResponseDto response = scheduleService.updateSchedule(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDeleteRequestDto dto) {

        scheduleService.deleteSchedule(id, dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "삭제 완료");
        return ResponseEntity.ok(response);
    }

}
