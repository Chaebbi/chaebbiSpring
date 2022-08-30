package com.ae.chaebbiSpring.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [POST] /record
    POST_RECORD_NO_IMAGE(false, 2100, "이미지가 없습니다."),
    POST_RECORD_NO_TEXT(false, 2101, "식단 메뉴를 입력해주세요."),
    POST_RECORD_NO_CALORY(false, 2102, "칼로리를 입력해주세요."),
    POST_RECORD_MINUS_CALORY(false, 2103, "칼로리가 0보다 작습니다."),
    POST_RECORD_NO_CARB(false, 2104, "탄수화물을 입력해주세요."),
    POST_RECORD_MINUS_CARB(false, 2105, "탄수화물이 0보다 작습니다."),
    POST_RECORD_NO_PROTEIN(false, 2106, "단백질을 입력해주세요."),
    POST_RECORD_MINUS_PROTEIN(false, 2107, "단백질이 0보다 작습니다."),
    POST_RECORD_NO_FAT(false, 2108, "지방을 입력해주세요."),
    POST_RECORD_MINUS_FAT(false, 2109, "지방이 0보다 작습니다."),
    POST_RECORD_NO_RDATE(false, 2110, "식사 날짜를 입력해주세요."),
    POST_RECORD_INVALID_RDATE(false, 2111, "식사 날짜의 형식이 맞지 않습니다."),
    POST_RECORD_NO_RTIME(false, 2112, "식사 시간을 입력해주세요."),
    POST_RECORD_INVALID_RTIME(false, 2113, "식사 시간의 형식이 맞지 않습니다."),
    POST_RECORD_NO_AMOUNT(false, 2114, "식사 양을 입력해주세요."),
    POST_RECORD_MINUS_AMOUNT(false, 2115, "식사 양이 0보다 작습니다."),
    POST_RECORD_NO_MEAL(false, 2116, "식사 끼니를 입력해주세요."),
    POST_RECORD_INVALID_MEAL(false, 2117, "식사 끼니가 유효하지 않습니다."),
    POST_RECORD_NO_ID(false, 2118, "조회하고 싶은 기록 id를 입력해주세요."),
    POST_RECORD_NO_RECORD_DATA(false, 2119, "해당하는 기록이 없습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_LOGIN_PWD(false,3015,"비밀번호가 틀렸습니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
