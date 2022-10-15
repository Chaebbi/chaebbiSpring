package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Bistro;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.request.BistroMainRequestDto;
import com.ae.chaebbiSpring.dto.request.BistroMiddleRequestDto;
import com.ae.chaebbiSpring.dto.request.CategoryRequestDto;
import com.ae.chaebbiSpring.dto.request.MiddleRequestDto;
import com.ae.chaebbiSpring.dto.response.*;
import com.ae.chaebbiSpring.service.BistroService;
import com.ae.chaebbiSpring.service.BookmarkService;
import com.ae.chaebbiSpring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jboss.jandex.Main;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;

@Api(tags = "Bistro API", description = "음식점 관련 API")
@RestController
@RequiredArgsConstructor
public class BistroApiController {
    private final BistroService bistroService;
    private final BookmarkService bookmarkService;
    private final UserService userService;

    //6-1
    @Operation(summary = "[POST] 6-1 음식점 중분류 조회", description = "음식점 중분류 조회 API ")
    @PostMapping("/api/bistromiddle")
    public BaseResponse<ResultResponse> middle(@AuthenticationPrincipal String userId, @RequestBody @Valid MiddleRequestDto request) {
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

        if(request.getWide().isEmpty() || request.getWide().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_WIDE);
        }

        List<Bistro> bistros = bistroService.getMiddle(request.getWide());
        List<String> middles = new ArrayList<>();

        for(Bistro bistro : bistros) {
            middles.add(bistro.getMiddle());
        }
        return new BaseResponse<>(new ResultResponse(middles));
    }

    //6-2
    @Operation(summary = "[POST] 6-2 대,중분류 별 음식점 조회", description = "음식점 대분류, 중분류별 조회 API ")
    @PostMapping("/api/categories")
    public BaseResponse<CategoryListResponseDto> categories(@AuthenticationPrincipal String userId, @RequestBody @Valid CategoryRequestDto request) {
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

        if(request.getWide().isEmpty() || request.getWide().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_WIDE);
        }

        if(request.getMiddle().isEmpty() || request.getMiddle().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_MIDDLE);
        }

        List<BistroDto> categoryList = bistroService.getCategoryList(request.getWide(), request.getMiddle());
        List<String> categories = bistroService.getCategories(request.getWide(), request.getMiddle());
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));

        List<CategoryListDto> listDtos = new ArrayList<>();

        for(BistroDto bistro : categoryList) {
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            listDtos.add(new CategoryListDto(bistro.getId().intValue(), isBookmark, bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(), bistro.getTel(), bistro.getLa(), bistro.getLo(), bistro.getUrl()));
        }


        return new BaseResponse<>(new CategoryListResponseDto(categories, listDtos.size(), listDtos));
    }

    //6-3
    @Operation(summary = "[POST] 6-3 지도 음식점 전체 조회", description = "지도 음식점 전체 조회 API ")
    @GetMapping("/api/allbistro")
    public ResultResponse allBistro(@AuthenticationPrincipal String userId) {
        List<BistroDto> allBistro = bistroService.getBistro();
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));
        List<BistroResponseDto> bistroDtos = new ArrayList<>();
        for (BistroDto bistro : allBistro){
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            bistroDtos.add(new BistroResponseDto(isBookmark, bistro.getId(), bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getMenu(), Double.parseDouble(bistro.getLa()), Double.parseDouble(bistro.getLo()), bistro.getUrl()));
        }
        return new ResultResponse(bistroDtos);
    }

    //6-4
    @Operation(summary = "[GET] 6-4 음식점 대분류 조회", description = "음식점 대분류 조회 API ")

    @GetMapping("api/bistrowide")
    public BaseResponse<ResultResponse> middle(@AuthenticationPrincipal String userId) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        List<Bistro> bistros = bistroService.getWide();
        List<String> wides = new ArrayList<>();

        for(Bistro bistro : bistros) {
            wides.add(bistro.getWide());
        }
        return new BaseResponse<>(new ResultResponse(wides));
    }

    /*
    @GetMapping("/api/bistrourl/{bistroId}")
    public BaseResponse<String> getUrl(@AuthenticationPrincipal String userId, @PathVariable("bistroId") int bistroId) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        return new BaseResponse<>(bistroService.getUrl(bistroId));
    }
     */

    // 6-6
    @PostMapping("/api/bistro-category-main")
    public BaseResponse<List<MainListDto>> getBistroMain(@AuthenticationPrincipal String userId, @RequestBody BistroMainRequestDto request) {
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

        if(request.getSiteWide().isEmpty() || request.getSiteWide().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_WIDE);
        }

        if(request.getSiteMiddle().isEmpty() || request.getSiteMiddle().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_MIDDLE);
        }

        if(request.getCategoryMain().isEmpty() || request.getCategoryMain().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_MAIN);
        }

        List<Bistro> bistroMainList = bistroService.getBistroMain(request.getSiteWide(), request.getSiteMiddle(), request.getCategoryMain());
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));

        List<MainListDto> listDtos = new ArrayList<>();

        for (Bistro bistro : bistroMainList){
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }

            listDtos.add(new MainListDto(bistro.getId().intValue(), isBookmark, bistro.getName(), bistro.getRAddr(), bistro.getLAddr(), bistro.getTel(), bistro.getMenu(), bistro.getLa(), bistro.getLo(), bistro.getUrl()));
        }

        return new BaseResponse<>(listDtos);
    }

    // 6-7
    @PostMapping("/api/bistro-category-middle")
    public BaseResponse<List<MainListDto>> getBistroMiddle(@AuthenticationPrincipal String userId, @RequestBody BistroMiddleRequestDto request) {
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

        if(request.getSiteWide().isEmpty() || request.getSiteWide().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_WIDE);
        }

        if(request.getSiteMiddle().isEmpty() || request.getSiteMiddle().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_MIDDLE);
        }

        if(request.getCategoryMain().isEmpty() || request.getCategoryMain().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_MAIN);
        }

        if(request.getCategoryMiddle().isEmpty() || request.getCategoryMiddle().equals("")) {
            return new BaseResponse<>(POST_BISTRO_CATEGORY_NO_MIDDLE);
        }

        List<Bistro> bistroMainList = bistroService.getBistroMiddle(request.getSiteWide(), request.getSiteMiddle(),
                request.getCategoryMain(), request.getCategoryMiddle());
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));

        List<MainListDto> listDtos = new ArrayList<>();

        for (Bistro bistro : bistroMainList){
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }

            listDtos.add(new MainListDto(bistro.getId().intValue(), isBookmark, bistro.getName(), bistro.getRAddr(), bistro.getLAddr(), bistro.getTel(), bistro.getMenu(), bistro.getLa(), bistro.getLo(), bistro.getUrl()));
        }

        return new BaseResponse<>(listDtos);
    }
}
