package com.oldandsea.pcb.service;


import com.oldandsea.pcb.domain.dto.request.MemberRequestDto;

import com.oldandsea.pcb.domain.dto.response.MemberResponseDto;
import com.oldandsea.pcb.domain.entity.Member;
import com.oldandsea.pcb.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;

    public boolean memberCheck(MemberRequestDto memberDto) {
        Optional<Member> findMember = memberRepository.findByUid(memberDto.getUid());

        if (findMember.isEmpty())
            return true;
        else
            return false;

    }

    @Transactional
    public String createMember(MemberRequestDto memberRequestDto) {

        Member memeber = memberRequestDto.toEntity();
        Optional<Member> findMember = memberRepository.findByUid(memeber.getUid());
        if (findMember.isEmpty()) {
            memberRepository.save(memeber);
            return memeber.getUid();
        } else {
            return null;
        }
    }

    @Transactional
    public MemberResponseDto login(MemberRequestDto memberRequestDto) {
        Optional<Member> byMemberUid = memberRepository.findByUid(memberRequestDto.getUid());
        if (byMemberUid.isPresent()) {
            Member member = byMemberUid.get();

            if (member.getPwd().equals(memberRequestDto.getPwd())) {
                return MemberResponseDto.builder()
                        .memberId(member.getMemberId())
                        .uid(member.getUid())
                        .pwd(member.getPwd())
                        .nickname(member.getNickname())
                        .build();

            } else 
                return null;
            
        }else 
            return null;
        
    }
}



