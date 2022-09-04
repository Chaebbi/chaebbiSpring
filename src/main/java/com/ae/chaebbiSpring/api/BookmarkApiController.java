package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Bistro;
import com.ae.chaebbiSpring.domain.Bookmark;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.request.BookmarkRequestDto;
import com.ae.chaebbiSpring.dto.response.CreateBookmarkResponseDto;
import com.ae.chaebbiSpring.dto.response.ResResponse;
import com.ae.chaebbiSpring.dto.response.RestaurantResponseDto;
import com.ae.chaebbiSpring.service.BistroService;
import com.ae.chaebbiSpring.service.BookmarkService;
import com.ae.chaebbiSpring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BistroService bistroService;

    @Tag(name = "즐겨찾기", description = "즐겨찾기 API")
    @Operation(summary = "[POST] 7-1 즐겨찾기 등록 ", description = "즐겨찾기 북마크 등록 API ")
    //7-1
    @PostMapping("api/bookmark")
    public BaseResponse<CreateBookmarkResponseDto> createBookmarkResponse(@AuthenticationPrincipal String userId,
                                                                          @Parameter  @RequestBody @Valid BookmarkRequestDto request) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (request.getBistroId() == null || request.getBistroId().equals("")){
            return new BaseResponse<>(POST_BOOKMARK_NO_BISTRO_ID);
        }
        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        List<Long> restaurantId = restaurant.stream().map(Bistro::getId).collect(Collectors.toList());
        Long count = restaurantId.stream().filter(m-> request.getBistroId().equals(m)).count();
        if(count > 0 ){
            return new BaseResponse<>(POST_BOOKMARK_PRESENT_BISTRO);
        }
        Bistro bistro = bistroService.findOne(request.getBistroId());
        Bookmark bookmark = Bookmark.createBookmark(user, bistro);
        Long id = bookmarkService.create(bookmark);

        return new BaseResponse<>(new CreateBookmarkResponseDto(id.intValue()));
    }

    //7-2
    @Tag(name = "즐겨찾기", description = "즐겨찾기 API")
    @Operation(summary = "[POST] 7-2 즐겨찾기 조회 ", description = "즐겨찾기 북마크 조회 API ")
    @GetMapping("api/bookmarklist")
    public BaseResponse<ResResponse> bookmarkList(@AuthenticationPrincipal String userId) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        List<RestaurantResponseDto> restaurantDtos = new ArrayList<>();

        for(Bistro bistro: restaurant) {
            restaurantDtos.add(new RestaurantResponseDto(bistro.getId(), bistro.getCategory(), bistro.getName(),
                    bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getLa(), bistro.getLo()));
        }
        if(restaurant.size() > 0){
            return new BaseResponse<>(new ResResponse(restaurantDtos.size(), restaurantDtos));

        }else return new BaseResponse<>(POST_BOOKMARK_LIST_EMPTY);

    }

    //7-3
    @Tag(name = "즐겨찾기", description = "즐겨찾기 API")
    @Operation(summary = "[POST] 7-3 즐겨찾기 삭제 ", description = "즐겨찾기 북마크 삭제 API")

    @DeleteMapping("api/del/bookmark")
    public BaseResponse<CreateBookmarkResponseDto> deleteBookmark(@AuthenticationPrincipal String userId,
                              @RequestBody @Valid BookmarkRequestDto request){
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }

        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        Long count =restaurant.stream().map(Bistro::getId).collect(Collectors.toList())
                .stream().filter(
                m-> request.getBistroId().equals(m))
                .count();
        if(count > 0 ){
            Long bistroId = bookmarkService.deleteBookmark(Long.valueOf(userId), request.getBistroId());
            return new BaseResponse<>(new CreateBookmarkResponseDto(bistroId.intValue()));

        } else return new BaseResponse<>(POST_BOOMARK_THERE_NO_BISTRO);



    }



}
