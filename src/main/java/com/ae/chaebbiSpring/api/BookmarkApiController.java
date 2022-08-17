package com.ae.chaebbiSpring.api;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BistroService bistroService;

    //7-1
    @PostMapping("api/bookmark")
    public CreateBookmarkResponseDto createBookmarkResponse(@AuthenticationPrincipal String userId,
                                                         @RequestBody @Valid BookmarkRequestDto request) {
        User user = userService.findOne(Long.valueOf(userId));
        Bistro bistro = bistroService.findOne(request.getBistroId());
        Bookmark bookmark = Bookmark.createBookmark(user, bistro);
        Long id = bookmarkService.create(bookmark);

        return new CreateBookmarkResponseDto(id.intValue());
    }

    //7-2
    @GetMapping("api/bookmarklist")
    public ResResponse bookmarkList(@AuthenticationPrincipal String userId) {
        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        List<RestaurantResponseDto> restaurantDtos = new ArrayList<>();

        for(Bistro bistro: restaurant) {
            restaurantDtos.add(new RestaurantResponseDto(bistro.getCategory(), bistro.getName(),
                    bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getLa(), bistro.getLo()));
        }
        return new ResResponse(restaurantDtos.size(), restaurantDtos);

    }

    //7-3
    @DeleteMapping("api/del/bookmark")
    public CreateBookmarkResponseDto deleteBookmark(@AuthenticationPrincipal String userId,
                              @RequestBody @Valid BookmarkRequestDto request){
        Long bistroId = bookmarkService.deleteBookmark(Long.valueOf(userId), request.getBistroId());
        return new CreateBookmarkResponseDto(bistroId.intValue());

    }



}
