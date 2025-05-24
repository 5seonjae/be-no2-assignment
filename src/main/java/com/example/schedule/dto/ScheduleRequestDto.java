package com.example.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class ScheduleRequestDto {

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "작성자는 필수 입력 항목입니다.")
    private String writer;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;
}
