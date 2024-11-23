package com.ssafy.living_spot.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberSignUpRequest(
        @NotBlank(message = "이름 입력은 필수입니다.")
        @Size(min = 2, max = 10, message = "이름은 2~10자리여야 합니다.")
        String name,

        @NotBlank(message = "이메일 입력은 필수입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email,
        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다.")
        String password,

        String profileImageUrl
) {
}
