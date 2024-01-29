package com.example.yetiproject.repository;

import com.example.yetiproject.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TicketJdbcBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Long> batchUpdate(List<Ticket> tickets) {
        String sql = "INSERT INTO tickets (ticket_info_id, seat, user_id) VALUES (?, ?, ?)";
        return Arrays.stream(
                jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Ticket ticket = tickets.get(i);
                        log.info("ticket : " + ticket);
                        ps.setLong(1, ticket.getTicketInfoId());
                        ps.setString(2, ticket.getSeat());
                        ps.setLong(3, ticket.getUserId());
                    }

                    @Override
                    public int getBatchSize() {
                        return tickets.size();
                    }
                })
        ).boxed().map(Integer::longValue).toList();
    }
}
