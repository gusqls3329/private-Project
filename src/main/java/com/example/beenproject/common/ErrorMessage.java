package com.example.beenproject.common;

public interface ErrorMessage {
    String ILLEGAL_EX_MESSAGE = "잘못된 요청입니다.";

    String CAN_NOT_BLANK_EX_MESSAGE = "빈 값이 있습니다.";
    String ILLEGAL_PROMISE_EX_MESSAGE = "정해진 규칙과 다릅니다.";
    String NO_SUCH_USER_EX_MESSAGE = "해당하는 유저가 없습니다.";
    String BAD_EMAIL_EX_MESSAGE = "이미 존재하는 이메일입니다..";
    String ILLEGAL_UID_MESSAGE = "존재하지 않는 아이디입니다.";
    String ILLEGAL_UPW_MESSAGE = "비밀번호가 틀렸습니다.";

    String SERVER_ERR_MESSAGE = "알 수 없는 오류로 실패했습니다.";

}
