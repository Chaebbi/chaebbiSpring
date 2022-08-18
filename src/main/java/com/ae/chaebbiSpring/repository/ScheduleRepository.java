package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final EntityManager em;

    public void save(Schedule schedule) {
        em.persist(schedule);
    }
}
