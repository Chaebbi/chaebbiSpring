package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Ingredient;
import com.ae.chaebbiSpring.dto.response.IngredientResponseDto;
import com.ae.chaebbiSpring.dto.response.ResResponse;
import com.ae.chaebbiSpring.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.EMPTY_JWT;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;

    //9-1
    @Tag(name = "음식 재료", description = "음식 재료 API")
    @Operation(summary = "[GET] 9-1 음식 재료 검색", description = "음식 추천에서 재료검색 API  ")
    @GetMapping("/api/ingredient")
    public BaseResponse<ResResponse> ingredients(@AuthenticationPrincipal String userId) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        List<Ingredient> findIngredients = ingredientService.findAllIngredients();
        List<IngredientResponseDto> collect = findIngredients.stream()
                .map(m -> new IngredientResponseDto(m.getId(), m.getName()))
                .collect(toList());
        return new BaseResponse<>(new ResResponse(collect.size(), collect));
    }
}
