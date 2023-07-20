package com.oldandsea.pcb.domain.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPwdUpdateRequestDTO {
    private String pwd;
    @Builder
    public MemberPwdUpdateRequestDTO(String nickname, String pwd) {
        this.pwd = pwd;
    }
}