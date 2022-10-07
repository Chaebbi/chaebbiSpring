package com.ae.chaebbiSpring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bistro_category")
@Getter
@Setter
@NoArgsConstructor
public class BistroCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int main_id;
    private int middle_id;
    private int bistro_id;
}
