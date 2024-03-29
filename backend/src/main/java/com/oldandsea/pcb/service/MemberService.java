package com.oldandsea.pcb.service;

import com.oldandsea.pcb.domain.dto.request.*;
import com.oldandsea.pcb.domain.dto.layer.LoginDTO;
import com.oldandsea.pcb.domain.entity.Member;
import com.oldandsea.pcb.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
   private final MemberRepository memberRepository;
   private final PasswordEncoder passwordEncoder;

    public boolean uidCheck(MemberUidCheckRequestDTO memberUidCheckRequestDto) {
        Optional<Member> member = memberRepository.findByUid(memberUidCheckRequestDto.getUid());
       return member.isPresent();
    }

    public boolean nickNameCheck(MemberNickNameCheckRequestDTO memberNickNameCheckRequestDto) {
        Optional<Member> member = memberRepository.findByNickname(memberNickNameCheckRequestDto.getNickname());
        return member.isPresent();
    }
    @Transactional
    public String createMember(MemberCreateRequestDTO memberCreateRequestDto) {

        Member memeber = memberCreateRequestDto.toEntity();
        memeber.updatePwd(passwordEncoder.encode(memeber.getPwd()));
        Optional<Member> findMember = memberRepository.findByUid(memeber.getUid());
        if (findMember.isPresent()) {
            throw new IllegalArgumentException("회원가입이 이미 완료된 아이디입니다");
        }
        else {
            memberRepository.save(memeber);
            return memeber.getUid();
        }
    }

    public LoginDTO login(MemberLoginRequestDTO memberLoginRequestDto) {
        Member member = memberRepository.findByUid(memberLoginRequestDto.getUid()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 다시 입력해주세요")
        );
            if (passwordEncoder.matches(memberLoginRequestDto.getPwd(),member.getPwd())) {
                return LoginDTO.builder()
                        .memberId(member.getMemberId())
                        .uid(member.getUid())
                        .nickname(member.getNickname())
                        .build();
            } else
                throw new IllegalArgumentException("비밀번호를 다시 입력해주세요");
        }

    @Transactional
    public void delete(Long memberId) {
       Member member = memberRepository.findByMemberIdFetch(memberId).orElseThrow(
               () -> new IllegalArgumentException("memberId에 해당하는 member가 존재하지 않습니다(탈퇴)")
       );
        memberRepository.delete(member);
    }

    @Transactional
    public void updatePwd(Long memberId, MemberPwdUpdateRequestDTO pwdUpdateRequestDto) {
        String pwd  = pwdUpdateRequestDto.getPwd();
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("memberId에 해당하는 member가 존재하지 않습니다(비밀번호 변경)")
        );
        member.updatePwd(pwd);
    }

    @Transactional
    public void updateNickname(Long memberId, MemberNickUpdateRequestDTO nickUpdateRequestDto) {
        String nickname = nickUpdateRequestDto.getNickname();
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("memberId에 해당하는 member가 존재하지 않습니다(닉네임 변경)")
        );
        member.updateNickname(nickname);
    }
}


