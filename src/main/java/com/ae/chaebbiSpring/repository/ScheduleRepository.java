package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.Schedule;
import com.ae.chaebbiSpring.dto.response.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final EntityManager em;

    public void save(Schedule schedule) {
        em.persist(schedule);
    }

    public List<Schedule> findschedule(Integer userId, String todayDate) {
        return em.createQuery("select s from Schedule s where s.uId = :userId and" +
                        " (s.sdate <= :todayDate and s.edate >= :todayDate)" +
                        " order by s.stime", Schedule.class)
                .setParameter("userId", userId)
                .setParameter("todayDate", todayDate)
                .getResultList();

    }
}
