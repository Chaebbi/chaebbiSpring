package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseException;
import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.config.security.JwtProvider;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.request.*;
import com.ae.chaebbiSpring.dto.response.*;
import com.ae.chaebbiSpring.service.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final JwtProvider jwtProvider;


    // 4-1
    // 로그인 시에, kakaoprofile로 받은 정보가 db에 있으면 jwt 토큰 발급(status코드는 온보딩 안띄우게). db에 없으면 new user로 저장시키고 jwt 토큰발급(온보딩 띄우게)
    @PostMapping("/api/login")
    public BaseResponse<LoginResponseDto> loginByKakao(
            @RequestBody UserSocialLoginRequestDto socialLoginRequestDto) {
        String token = socialLoginRequestDto.getAccessToken();
        if(token.isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_TOKEN);
        }
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
    @PostMapping("/api/signup")
    public BaseResponse<ResponseEntity<?>> signup(@AuthenticationPrincipal String userId, @RequestBody SignupRequestDto signupRequestDto) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        if(signupRequestDto.getName().isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_NAME);
        }
        if((signupRequestDto.getName().equals(""))) {
            return new BaseResponse<>(POST_USER_NO_NAME);
        }
        if(signupRequestDto.getName().length() > 45) {
            return new BaseResponse<>(POST_USER_LONG_NAME);
        }

        if(signupRequestDto.getAge().isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_AGE);
        }
        if((signupRequestDto.getAge().equals(""))) {
            return new BaseResponse<>(POST_USER_NO_AGE);
        }
        if(Integer.valueOf(signupRequestDto.getAge()) < 1) {
            return new BaseResponse<>(POST_USER_MINUS_AGE);
        }

        if(signupRequestDto.getGender().isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_GENDER);
        }
        if((signupRequestDto.getGender().equals(""))) {
            return new BaseResponse<>(POST_USER_NO_GENDER);
        }
        if(Integer.valueOf(signupRequestDto.getGender()) != 0 || Integer.valueOf(signupRequestDto.getGender()) != 1) {
            return new BaseResponse<>(POST_USER_INVALID_GENDER);
        }

        if(signupRequestDto.getHeight().isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_HEIGHT);
        }
        if(signupRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_HEIGHT);
        }

        if(Integer.valueOf(signupRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_HEIGHT);
        }

        if(signupRequestDto.getWeight().isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_WEIGHT);
        }
        if(signupRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_WEIGHT);
        }

        if(Integer.valueOf(signupRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_WEIGHT);
        }

        if(signupRequestDto.getActivity().isEmpty()) {
            return new BaseResponse<>(POST_USER_NO_ACTIVITY);
        }
        if(signupRequestDto.getActivity().equals("")) {
            return new BaseResponse<>(POST_USER_NO_ACTIVITY);
        }

        if(Integer.valueOf(signupRequestDto.getActivity()) != 25 || Integer.valueOf(signupRequestDto.getActivity()) != 33 || Integer.valueOf(signupRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(POST_USER_INVALID_ACTIVITY);
        }



        userService.signup(Long.valueOf(userId), signupRequestDto);
        return new BaseResponse<>(ResponseEntity.ok().build());
    }

    // 3-1
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
    @PutMapping("/api/userupdate")
    public BaseResponse<ResponseEntity<?>> update(@AuthenticationPrincipal String userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        if(userUpdateRequestDto.getAge().isEmpty()) {
            return new BaseResponse<>(PUT_USER_NO_AGE);
        }
        if(userUpdateRequestDto.getAge().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_AGE);
        }

        if(Integer.valueOf(userUpdateRequestDto.getAge()) < 1) {
            return new BaseResponse<>(PUT_USER_MINUS_AGE);
        }

        if(userUpdateRequestDto.getHeight().isEmpty()) {
            return new BaseResponse<>(PUT_USER_NO_HEIGHT);
        }
        if(userUpdateRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_HEIGHT);
        }

        if(Integer.valueOf(userUpdateRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(PUT_USER_MINUS_HEIGHT);
        }

        if(userUpdateRequestDto.getWeight().isEmpty()) {
            return new BaseResponse<>(PUT_USER_NO_WEIGHT);
        }
        if(userUpdateRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_WEIGHT);
        }

        if(Integer.valueOf(userUpdateRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(PUT_USER_MINUS_WEIGHT);
        }

        if(userUpdateRequestDto.getActivity().isEmpty()) {
            return new BaseResponse<>(PUT_USER_NO_ACTIVITY);
        }
        if(userUpdateRequestDto.getActivity().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_ACTIVITY);
        }

        if(Integer.valueOf(userUpdateRequestDto.getActivity()) != 25 || Integer.valueOf(userUpdateRequestDto.getActivity()) != 33 || Integer.valueOf(userUpdateRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(PUT_USER_INVALID_ACTIVITY);
        }


        userService.update(Long.valueOf(userId), userUpdateRequestDto);
        return new BaseResponse<>(ResponseEntity.ok().build());
    }

    // 11-1 : [POST] 회원가입 api
    @PostMapping("api/create-user")
    public JoinResponseDto createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        JoinResponseDto joinResponseDto = userService.join(createUserRequestDto);
        return joinResponseDto;
    }

    // 11-2 :[POST] 이메일 중복확인 api
    @PostMapping("api/email-check")
    public CheckEmailRes checkEmail(@RequestBody CheckEmailReq checkEmailReq) {
        return userService.checkEmailDuplicate(checkEmailReq);

    }

    // 11-3 : [POST] 로그인 api
    @PostMapping("api/user-login")
    public BaseResponse<GeneralLoginResDto> generalLogin(@RequestBody GeneralLoginReqDto loginReqDto){
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
