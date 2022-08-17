package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.domain.Food;
import com.ae.chaebbiSpring.dto.response.FoodResponseDto;
import com.ae.chaebbiSpring.dto.response.FoodTypeResponseDto;
import com.ae.chaebbiSpring.dto.response.ResResponse;
import com.ae.chaebbiSpring.service.FoodService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class FoodApiController {
    private final FoodService foodService;

    //2-1
    @GetMapping("/api/foodname")
    public ResResponse foods(@AuthenticationPrincipal String userId) {
        List<Food> findFoods = foodService.findAllFoods();
        List<FoodTypeResponseDto> collect = findFoods.stream()
                .map(m -> new FoodTypeResponseDto(m.getId(), m.getName()))
                .collect(toList());
        return new ResResponse(collect.size(), collect);


    }
    //2-2
    @PostMapping("/api/food")
    public ResResponse foodResponse(@AuthenticationPrincipal String userId, @RequestBody @Valid CreateFoodRequest request){
        List<Food> findFood = foodService.findFood(request.id);
        List<FoodResponseDto> collect = findFood.stream()
                .map(m -> new FoodResponseDto(m.getName(), m.getCapacity(), m.getCalory(), m.getCarb(), m.getPro(), m.getFat()))
                .collect(toList());
        return new ResResponse(collect.size(), collect);
    }

    @Data
    private static class CreateFoodRequest {
        @NotNull
        private Long id;
    }

}
