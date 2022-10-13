package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseException;
import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.config.security.JwtProvider;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.request.*;
import com.ae.chaebbiSpring.dto.response.*;
import com.ae.chaebbiSpring.service.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;

@Api(tags = "User API", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final JwtProvider jwtProvider;


    // 4-1
    // 로그인 시에, kakaoprofile로 받은 정보가 db에 있으면 jwt 토큰 발급(status코드는 온보딩 안띄우게). db에 없으면 new user로 저장시키고 jwt 토큰발급(온보딩 띄우게)
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API")
    @PostMapping("/api/login")
    public BaseResponse<LoginResponseDto> loginByKakao(
            @RequestBody UserSocialLoginRequestDto socialLoginRequestDto) {
        String token = socialLoginRequestDto.getAccessToken();

        if((token.equals(""))) {
            return new BaseResponse<>(POST_USER_NO_TOKEN);
        }
        // KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(token);

        /*
        if (kakaoProfile.getKakao_account().getEmail() == null) {
            kakaoService.kakaoUnlink(socialSignupRequestDto.getAccessToken());
            throw new CSocialAgreementException();
        }
         */
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("https://kapi.kakao.com/v2/user/me");
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        MultiValueMap<String, Object> pa = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(pa, headers);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        String id = String.valueOf(responseBody.get("id"));

        Optional<User> user = userService.findByKakaoId(id);
        boolean isEmpty = user.isEmpty();
        System.out.println(isEmpty);
        if(!isEmpty) {
            return new BaseResponse<>(new LoginResponseDto(user.get().getId(), jwtProvider.createToken(user.get()), false));
        } else {
            User u = User.createUser(id);
            userService.create(u);
            return new BaseResponse<>(new LoginResponseDto(u.getId(), jwtProvider.createToken(u), true));
        }
    }

    // 3-3
    @Operation(summary = "카카오 회원 등록", description = "카카오 회원 등록 API")
    @PostMapping("/api/signup")
    public BaseResponse<ResponseEntity<?>> signup(@AuthenticationPrincipal String userId, @RequestBody SignupRequestDto signupRequestDto) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        if(signupRequestDto.getName().isEmpty() || signupRequestDto.getName().equals("")) {
            return new BaseResponse<>(POST_USER_NO_NAME);
        }

        if(signupRequestDto.getName().length() > 45) {
            return new BaseResponse<>(POST_USER_LONG_NAME);
        }

        if(signupRequestDto.getAge().isEmpty() || signupRequestDto.getAge().equals("")) {
            return new BaseResponse<>(POST_USER_NO_AGE);
        }

        if(Integer.valueOf(signupRequestDto.getAge()) < 1) {
            return new BaseResponse<>(POST_USER_MINUS_AGE);
        }

        if(signupRequestDto.getGender().isEmpty() || signupRequestDto.getGender().equals("")) {
            return new BaseResponse<>(POST_USER_NO_GENDER);
        }

        if(Integer.valueOf(signupRequestDto.getGender()) != 0 && Integer.valueOf(signupRequestDto.getGender()) != 1) {
            return new BaseResponse<>(POST_USER_INVALID_GENDER);
        }

        if(signupRequestDto.getHeight().isEmpty() || signupRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_HEIGHT);
        }

        if(Integer.valueOf(signupRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_HEIGHT);
        }

        if(signupRequestDto.getWeight().isEmpty() || signupRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_WEIGHT);
        }

        if(Integer.parseInt(signupRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_WEIGHT);
        }

        if(signupRequestDto.getActivity().isEmpty() || signupRequestDto.getActivity().equals("")) {
            return new BaseResponse<>(POST_USER_NO_ACTIVITY);
        }

        if(Integer.valueOf(signupRequestDto.getActivity()) != 25 && Integer.valueOf(signupRequestDto.getActivity()) != 33 && Integer.valueOf(signupRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(POST_USER_INVALID_ACTIVITY);
        }



        userService.signup(Long.valueOf(userId), signupRequestDto);
        return new BaseResponse<>(ResponseEntity.ok().build());
    }

    // 3-1
    @Operation(summary = "회원 정보 조회", description = "회원 정보 조회 API")
    @GetMapping("/api/userinfo")
    public BaseResponse<UserInfoResponseDto> info(@AuthenticationPrincipal String userId) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        return new BaseResponse<>(new UserInfoResponseDto(user.getName(), user.getGender(), user.getAge(), user.getHeight(), user.getWeight(), user.getIcon(), user.getActivity()));
    }

    // 3-2
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 API")
    @PutMapping("/api/userupdate")
    public BaseResponse<ResponseEntity<?>> update(@AuthenticationPrincipal String userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        if(userUpdateRequestDto.getAge().isEmpty() || userUpdateRequestDto.getAge().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_AGE);
        }


        if(Integer.valueOf(userUpdateRequestDto.getAge()) < 1) {
            return new BaseResponse<>(PUT_USER_MINUS_AGE);
        }

        if(userUpdateRequestDto.getHeight().isEmpty() || userUpdateRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_HEIGHT);
        }

        if(Integer.valueOf(userUpdateRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(PUT_USER_MINUS_HEIGHT);
        }

        if(userUpdateRequestDto.getWeight().isEmpty() || userUpdateRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_WEIGHT);
        }

        if(Integer.valueOf(userUpdateRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(PUT_USER_MINUS_WEIGHT);
        }

        if(userUpdateRequestDto.getActivity().isEmpty() || userUpdateRequestDto.getActivity().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_ACTIVITY);
        }

        if(Integer.valueOf(userUpdateRequestDto.getActivity()) != 25 && Integer.valueOf(userUpdateRequestDto.getActivity()) != 33 && Integer.valueOf(userUpdateRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(PUT_USER_INVALID_ACTIVITY);
        }


        userService.update(Long.valueOf(userId), userUpdateRequestDto);
        return new BaseResponse<>(ResponseEntity.ok().build());
    }

    // 3-4
    @Operation(summary = "[DELETE] 3-4 회원 탈퇴", description = "회원 탈퇴 API")
    @DeleteMapping("/api/userdelete")
    public BaseResponse<String> deleteUser(@AuthenticationPrincipal String userId) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if(user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        userService.delete(Long.valueOf(userId));
        return new BaseResponse<>("회원 탈퇴 되었습니다.");
    }

    // 11-4
    @Operation(summary = "[POST] 11-4 닉네임 중복 확인", description = "닉네임 중복 확인 API")
    @PostMapping("/api/user-nickname")
    public BaseResponse<UserNicknameResponseDto> nicknameCheck(@RequestBody UserNicknameRequestDto request) {
        if(request.getNickname().isEmpty() || request.getNickname().equals("")) {
            return new BaseResponse<>(POST_EMPTY_NICKNAME);
        }

        Long isExist = userService.nicknameCheck(request.getNickname().trim());
        if(isExist > 0) {
            return new BaseResponse<>(new UserNicknameResponseDto(true, "이미 존재하는 닉네임입니다."));
        } else {
            return new BaseResponse<>(new UserNicknameResponseDto(false, "사용해도 되는 닉네임입니다."));
        }
    }

    // 11-1 : [POST] 회원가입 api
    @Operation(summary = "[POST] 11-1 일반 회원가입", description = "일반 회원 회원가입 API")
    @PostMapping("api/create-user")
    public BaseResponse<JoinResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        if(createUserRequestDto.getEmail().isEmpty() || createUserRequestDto.getEmail().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        String isPresent = userService.checkEmailDuplicate(createUserRequestDto.getEmail()).getIsPresent();
        if(isPresent.equals("duplicate email")){
            return new BaseResponse<>(DUPLICATED_EMAIL);
        }
        if(createUserRequestDto.getPwd().length() > 20 || createUserRequestDto.getPwd().length() < 5){
            return new BaseResponse<>(POST_SHORT_PWD);
        }
        if(createUserRequestDto.getGender() != 0 && createUserRequestDto.getGender() != 1 ) {
            return new BaseResponse<>(POST_USER_INVALID_GENDER);
        }
        if(createUserRequestDto.getName().isEmpty() || createUserRequestDto.getName().equals("")) {
            return new BaseResponse<>(POST_USER_NO_NAME);
        }
        if(createUserRequestDto.getName().length() > 45) {
            return new BaseResponse<>(POST_USER_LONG_NAME);
        }

        if(createUserRequestDto.getAge() < 1) {
            return new BaseResponse<>(POST_USER_MINUS_AGE);
        }
        if(createUserRequestDto.getHeight().isEmpty() || createUserRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_HEIGHT);
        }
        if(Integer.valueOf(createUserRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_HEIGHT);
        }
        if(createUserRequestDto.getWeight().isEmpty() || createUserRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_WEIGHT);
        }
        if(Integer.parseInt(createUserRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_WEIGHT);
        }
        if(Integer.valueOf(createUserRequestDto.getActivity()) != 25 && Integer.valueOf(createUserRequestDto.getActivity()) != 33 && Integer.valueOf(createUserRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(POST_USER_INVALID_ACTIVITY);
        }

        JoinResponseDto joinResponseDto = userService.join(createUserRequestDto);
        return new BaseResponse<>(joinResponseDto);
    }

    // 11-2 :[POST] 이메일 중복확인 api
    @Operation(summary = "[POST] 이베일 중복확인", description = "일반 회원 이메일 중복확인API")
    @PostMapping("api/email-check")
    public BaseResponse<CheckEmailRes> checkEmail(@RequestBody CheckEmailReq checkEmailReq) {
       if(checkEmailReq.getEmail().isEmpty() || checkEmailReq.getEmail().equals("")){
           return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
       }
        return new BaseResponse(userService.checkEmailDuplicate(checkEmailReq.getEmail()));

    }

    // 11-3 : [POST] 로그인 api
    @Operation(summary = "[POST] 11-3 일반 로그인", description = "일반 회원 로그인 API")
    @PostMapping("api/user-login")
    public BaseResponse<GeneralLoginResDto> generalLogin(@RequestBody GeneralLoginReqDto loginReqDto){
        if(loginReqDto.getEmail().isEmpty() || loginReqDto.getEmail().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        if(loginReqDto.getPwd().isEmpty() || loginReqDto.getPwd().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PWD);
        }
        try {
            return new BaseResponse<>(userService.generalLogin(loginReqDto));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    /*
    // 액세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급함
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(signService.reissue(tokenRequestDto));
    }
     */




}
