package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.Food;
import com.ae.chaebbiSpring.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> findFood(Long id) { return  foodRepository.findFood(id); }


}
