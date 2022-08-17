package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.domain.Ingredient;
import com.ae.chaebbiSpring.dto.response.IngredientResponseDto;
import com.ae.chaebbiSpring.dto.response.ResResponse;
import com.ae.chaebbiSpring.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;

    //9-1
    @GetMapping("/api/ingredient")
    public ResResponse ingredients(@AuthenticationPrincipal String userId) {
        List<Ingredient> findIngredients = ingredientService.findAllIngredients();
        List<IngredientResponseDto> collect = findIngredients.stream()
                .map(m -> new IngredientResponseDto(m.getId(), m.getName()))
                .collect(toList());
        return new ResResponse(collect.size(), collect);
    }
}
