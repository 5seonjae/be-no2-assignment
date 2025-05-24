package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
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

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        ScheduleResponseDto response = scheduleService.createSchedule(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleListResponseDto>> getSchedules(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String modifiedAt) {

        List<ScheduleListResponseDto> schedules = scheduleService.getAllSchedules(userName, modifiedAt);
        return ResponseEntity.ok(schedules);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleListResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleListResponseDto response = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(response);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleListResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto dto) {
        ScheduleListResponseDto response = scheduleService.updateSchedule(id, dto);
        return ResponseEntity.ok(response);
    }

    // 일정 삭제
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
