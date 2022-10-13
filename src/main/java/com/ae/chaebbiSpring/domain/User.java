package com.ae.chaebbiSpring.domain;

import com.ae.chaebbiSpring.dto.request.CreateUserRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String pwd;
    private int gender;
    private int age;
    private String height;
    private String weight;
    private String kakao;
    private int icon;
    private int activity;

    @Column(name = "signup_date")
    private String date;

    @Column(name = "recommend_calory")
    private String rcal;
    @Column(name = "recommend_carb")
    private String rcarb;
    @Column(name = "recommend_pro")
    private String rpro;
    @Column(name = "recommend_fat")
    private String rfat;

    private String nickname;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    public static User createUser(String kakao) {
        User user = new User();
        user.setKakao(kakao);
        return user;
    }

    public static User createUser(CreateUserRequestDto createUserRequestDto) {
        User user = new User();
        user.setEmail(createUserRequestDto.getEmail());
        user.setPwd(createUserRequestDto.getPwd());
        user.setName(createUserRequestDto.getName());
        user.setGender(createUserRequestDto.getGender());
        user.setAge(createUserRequestDto.getAge());
        user.setHeight(createUserRequestDto.getHeight());
        user.setWeight(createUserRequestDto.getWeight());
        user.setActivity(createUserRequestDto.getActivity());
        return user;
    }
}
