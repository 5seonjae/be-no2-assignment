package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.exception.ScheduleNotFoundException;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Long id = scheduleRepository.save(dto);
        return new ScheduleResponseDto(id, "일정이 성공적으로 등록되었습니다.");
    }

    public List<ScheduleListResponseDto> getAllSchedules(String userName, String modifiedAt) {
        return scheduleRepository.findAll(userName, modifiedAt);
    }

    public ScheduleListResponseDto getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    public ScheduleListResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto dto) {
        String password = scheduleRepository.findPasswordById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        if (!password.equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.update(id, dto.getTitle(), dto.getWriter());

        return getScheduleById(id); // 수정된 일정 조회 후 반환
    }

    public void deleteSchedule(Long id, ScheduleDeleteRequestDto dto) {
        String password = scheduleRepository.findPasswordById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        if (!password.equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(id);
    }

}
