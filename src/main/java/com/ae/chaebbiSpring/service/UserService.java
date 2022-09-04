package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.config.BaseException;
import com.ae.chaebbiSpring.config.security.JwtProvider;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.CalcNutrientDtos;
import com.ae.chaebbiSpring.dto.request.*;
import com.ae.chaebbiSpring.dto.response.CheckEmailRes;
import com.ae.chaebbiSpring.dto.response.GeneralLoginResDto;
import com.ae.chaebbiSpring.dto.response.JoinResponseDto;
import com.ae.chaebbiSpring.repository.UserRepository;
import com.ae.chaebbiSpring.utils.CalcNutrients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.FAILED_TO_LOGIN;
import static com.ae.chaebbiSpring.config.BaseResponseStatus.FAILED_TO_LOGIN_PWD;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByKakaoId(String kakao) {
        return userRepository.findByKakao(kakao);
    }

    public void signup(Long id, SignupRequestDto dto) {
        userRepository.signup(id, dto);
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        userRepository.update(id, dto);
    }

    public JoinResponseDto join(CreateUserRequestDto createUserRequestDto) {
        User u = User.createUser(createUserRequestDto);

        int icon = (int)(Math.random() * 13);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));

        CalcRequestDto calcRequestDto = new CalcRequestDto(createUserRequestDto.getName(), createUserRequestDto.getAge(), createUserRequestDto.getGender(),
                createUserRequestDto.getHeight(), createUserRequestDto.getWeight(), createUserRequestDto.getActivity());
        CalcNutrientDtos calcNutrientDtos = CalcNutrients.calcNutrientDtos(calcRequestDto);

        u.setIcon(icon);
        u.setDate(date);
        u.setRcal(calcNutrientDtos.getRcal());
        u.setRcarb(calcNutrientDtos.getRcarb());
        u.setRpro(calcNutrientDtos.getRpro());
        u.setRfat(calcNutrientDtos.getRfat());

        userRepository.save(u);
        return new JoinResponseDto(u.getId());
    }

    public CheckEmailRes checkEmailDuplicate(String checkEmailReq) {
        Long isPresent = userRepository.checkEmailDuplicate(checkEmailReq);
        if(isPresent > 0){
            return new CheckEmailRes("duplicate email");
        } else {
            return new CheckEmailRes("not duplicate");

        }
    }

    public GeneralLoginResDto generalLogin(GeneralLoginReqDto loginReqDto) throws BaseException {
        Optional<User> user =  userRepository.findByEmail(loginReqDto.getEmail());
        if(!user.isEmpty()){
            String pwd = user.get().getPwd();
            if(loginReqDto.getPwd().equals(pwd)){
                return new GeneralLoginResDto(user.get().getId(), jwtProvider.createToken(user.get()));
            }
            else{
                throw new BaseException(FAILED_TO_LOGIN_PWD);
            }
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
