package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Food;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.response.FoodResponseDto;
import com.ae.chaebbiSpring.dto.response.FoodTypeResponseDto;
import com.ae.chaebbiSpring.dto.response.ResResponse;
import com.ae.chaebbiSpring.service.FoodService;
import com.ae.chaebbiSpring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;
import static com.ae.chaebbiSpring.config.BaseResponseStatus.POST_RECORD_NO_ID;
import static java.util.stream.Collectors.toList;

@Api(tags = "Food API", description = "음식 관련 API")
@RestController
@RequiredArgsConstructor
public class FoodApiController {
    private final FoodService foodService;
    private final UserService userService;

    //2-1
    @Operation(summary = "식단 검색", description = "모든 음식 API")
    @GetMapping("/api/foodname")
    public BaseResponse<ResResponse> foods(@AuthenticationPrincipal String userId) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        List<Food> findFoods = foodService.findAllFoods();
        List<FoodTypeResponseDto> collect = findFoods.stream()
                .map(m -> new FoodTypeResponseDto(m.getId(), m.getName()))
                .collect(toList());
        return new BaseResponse<>(new ResResponse(collect.size(), collect));


    }
    //2-2
    @Operation(summary = "음식 1개 검색", description = "음식 인덱스 API")
    @PostMapping("/api/food")
    public BaseResponse<ResResponse> foodResponse(@AuthenticationPrincipal String userId, @RequestBody @Valid CreateFoodRequest request){
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        List<Food> findFood = foodService.findFood(request.id);
        List<FoodResponseDto> collect = findFood.stream()
                .map(m -> new FoodResponseDto(m.getName(), m.getCapacity(), m.getCalory(), m.getCarb(), m.getPro(), m.getFat()))
                .collect(toList());
        return new BaseResponse<>(new ResResponse(collect.size(), collect));
    }

    @Schema(description = "음식 인덱스 조회 request")
    @Data
    private static class CreateFoodRequest {
        @Schema(name = "id", description = "영양조회 하고싶은 음식 ", nullable = false, example = "4")
        @NotNull
        private Long id;
    }

}
