package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.Record;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.DateAnalysisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;

    public void save(Record record) { em.persist(record); }

    public Long update(Long recordId, String text, String date, String calory, String carb, String protein, String fat,
                       String rdate, String rtime, double amount, int meal, User user) {
        em.createQuery("update Record r set r.text = :text, r.server_date = :date, r.cal = :calory, " +
                        "r.carb = :carb, r.protein = :protein, r.fat = :fat, r.date = :rdate, r.time = :rtime, r.amount = :amount, r.meal = :meal " +
                        "where r.id = :recordId and r.user = (select u from User u where u.id = :id)")
                .setParameter("text", text)
                .setParameter("date", date)
                .setParameter("calory", calory)
                .setParameter("carb", carb)
                .setParameter("protein", protein)
                .setParameter("fat", fat)
                .setParameter("rdate", rdate)
                .setParameter("rtime", rtime)
                .setParameter("amount", amount)
                .setParameter("meal", meal)
                .setParameter("recordId", recordId)
                .setParameter("id", user.getId())
                .executeUpdate();
        return recordId;
    }

    public void updateImage(Long recordId, String imgUrl, User user) {
        em.createQuery("update Record r set r.image_url = :imgUrl " +
                "where r.id = :recordId and r.user = (select u from User u where u.id = :id)")
                .setParameter("imgUrl", imgUrl)
                .setParameter("recordId", recordId)
                .setParameter("id", user.getId())
                .executeUpdate();
    }

    public void delete(Long userId, Long recordId) {
        em.createQuery("delete from Record r where r.user = (select u from User u where u.id = :uid) and " +
                        "r.id = :rid")
                .setParameter("uid", userId)
                .setParameter("rid", recordId)
                .executeUpdate();
    }

    public void deleteImage(Long userId, Long recordId) {
        em.createQuery("update Record r set r.image_url = null " +
                "where r.id = :rid and r.user = (select u from User u where u.id = :uid)")
                .setParameter("rid", recordId)
                .setParameter("uid", userId)
                .executeUpdate();
    }

    public List<Record> findDateRecords(Long id, String date) {
        return em.createQuery("select r from Record r join fetch r.user u where u.id = :param and r.date = :date", Record.class)
                .setParameter("param", id)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Record> findDetaileOne(Long id, Long record_id) {
        return em.createQuery("select r from Record r join r.user u where u.id = :param and r.id = :record_id", Record.class)
                .setParameter("param", id)
                .setParameter("record_id", record_id)
                .getResultList();

    }

    //7개의 기록된 날짜의 날짜별 총열량, 총영양소
    public List<DateAnalysisDto> analysisDate(Long id) {
        String sql = "SELECT r.record_date, SUM(r.food_amount), SUM(r.cal), SUM(r.carb), SUM(r.protein), SUM(r.fat)" +
                " FROM record r JOIN user u ON r.user_user_id = u.user_id WHERE r.user_user_id = :user_id" +
                " and r.record_date != :date" +
                " GROUP BY r.record_date ORDER BY r.record_date DESC LIMIT 7";
        //NativeQuery로 직접 날림
        Query nativeQuery = em.createNativeQuery(sql)
                .setParameter("user_id", id)
                .setParameter("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")));
        List<Object[]> resultList = nativeQuery.getResultList();
        List<DateAnalysisDto> dateAnalysisDtos = new ArrayList<>();
        for(Object[] row : resultList){
            String date = (String)row[0];
            Double sumAmount = (Double)row[1];
            Double sumCal = (Double)row[2];
            Double sumCarb = (Double)row[3];
            Double sumPro = (Double)row[4];
            Double sumFat = (Double)row[5];
            dateAnalysisDtos.add(new DateAnalysisDto(date, sumAmount, sumCal, sumCarb, sumPro, sumFat));
        }
        return dateAnalysisDtos;
    }
}
