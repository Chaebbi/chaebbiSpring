package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.CalcNutrientDtos;
import com.ae.chaebbiSpring.dto.request.*;
import com.ae.chaebbiSpring.utils.CalcNutrients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByKakao(String kakao) {
        List<User> user = em.createQuery("select u from User u where u.kakao = :kakao", User.class)
                .setParameter("kakao", kakao)
                .getResultList();
        return user.stream().findAny();
    }

    public void signup(Long id, SignupRequestDto dto) {
        int age = Integer.parseInt(dto.getAge());
        int gender = Integer.parseInt(dto.getGender());
        String name = dto.getName();
        String weight = dto.getWeight();
        String height = dto.getHeight();
        int activity = Integer.parseInt(dto.getActivity());
        int icon = (int)(Math.random() * 13);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        CalcRequestDto calcRequestDto = new CalcRequestDto(dto.getName(), age, gender, dto.getHeight(), dto.getWeight(), activity);
        CalcNutrientDtos calcNutrientDtos = CalcNutrients.calcNutrientDtos(calcRequestDto);

        em.createQuery("update User u set u.name = :name, u.age = :age, u.gender = :gender, u.height = :height, u.weight = :weight," +
                        "u.date = :date, u.icon = :icon, u.activity = :activity, u.rcal = :calory, u.rcarb = :carb, " +
                        "u.rpro = :pro, u.rfat = :fat " +
                "where u.id = :id")
                .setParameter("name", name)
                .setParameter("age", age)
                .setParameter("gender", gender)
                .setParameter("height", height)
                .setParameter("weight", weight)
                .setParameter("icon", icon)
                .setParameter("activity", activity)
                .setParameter("calory", calcNutrientDtos.getRcal())
                .setParameter("carb", calcNutrientDtos.getRcarb())
                .setParameter("pro", calcNutrientDtos.getRpro())
                .setParameter("fat", calcNutrientDtos.getRfat())
                .setParameter("date", date)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        User u = findOne(id);
        String name = u.getName();
        int gender = u.getGender();

        CalcRequestDto calcRequestDto = new CalcRequestDto(name, Integer.parseInt(dto.getAge()), gender, dto.getHeight(), dto.getWeight(), Integer.parseInt(dto.getActivity()));
        CalcNutrientDtos calcNutrientDtos = CalcNutrients.calcNutrientDtos(calcRequestDto);
        em.createQuery("update User u set u.age = :age, u.height = :height, u.weight = :weight, u.activity = :activity," +
                        " u.rcal = :cal, u.rcarb = :carb, u.rpro = :pro, u.rfat = :fat " +
                "where u.id = :id")
                .setParameter("age", Integer.parseInt(dto.getAge()))
                .setParameter("height", dto.getHeight())
                .setParameter("weight", dto.getWeight())
                .setParameter("activity", Integer.parseInt(dto.getActivity()))
                .setParameter("cal", calcNutrientDtos.getRcal())
                .setParameter("carb", calcNutrientDtos.getRcarb())
                .setParameter("pro", calcNutrientDtos.getRpro())
                .setParameter("fat", calcNutrientDtos.getRfat())
                .setParameter("id", id)
                .executeUpdate();
    }

    public Long checkEmailDuplicate(String checkEmailReq) {
        return (Long) em.createQuery("select count(u.id) from User u where u.email = :email")
                .setParameter("email", checkEmailReq)
                .getSingleResult();


    }
    public Optional<User> findByEmail(String email) {
        List<User> user = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return user.stream().findAny();
    }
}
