package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleListResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long save(ScheduleRequestDto dto) {
        String sql = "INSERT INTO schedule (title, writer, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dto.getTitle(), dto.getWriter(), dto.getPassword());

        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public List<ScheduleListResponseDto> findAll(String userName, String modifiedAt) {
        StringBuilder sql = new StringBuilder("""
        SELECT s.id, s.title, u.name AS user_name, s.created_at, s.updated_at
        FROM schedule s
        JOIN user u ON s.user_id = u.id
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (userName != null && !userName.isEmpty()) {
            sql.append(" AND u.name = ?");
            params.add(userName);
        }

        if (modifiedAt != null && !modifiedAt.isEmpty()) {
            sql.append(" AND DATE(s.updated_at) = ?");
            params.add(modifiedAt);
        }

        // 정렬 조건 고정: 항상 내림차순
        sql.append(" ORDER BY s.updated_at DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) ->
                new ScheduleListResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("user_name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                )
        );
    }

    public Optional<ScheduleListResponseDto> findById(Long id) {
        String sql = "SELECT id, title, writer, created_at, updated_at FROM schedule WHERE id = ?";

        List<ScheduleListResponseDto> result = jdbcTemplate.query(
                sql,
                new Object[]{id},
                (rs, rowNum) -> new ScheduleListResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("writer"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                )
        );

        return result.stream().findFirst(); // 결과가 없으면 Optional.empty()
    }

    public Optional<String> findPasswordById(Long id) {
        String sql = "SELECT password FROM schedule WHERE id = ?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{id},
                (rs, rowNum) -> rs.getString("password"));
        return result.stream().findFirst();
    }

    public void update(Long id, String title, String writer) {
        String sql = "UPDATE schedule SET title = ?, writer = ? WHERE id = ?";
        jdbcTemplate.update(sql, title, writer, id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
