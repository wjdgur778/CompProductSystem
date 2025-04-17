package com.example.CompProductSystem.api.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> getMembers(){
        List<Member> membersDto = memberRepository.findAll();
        return membersDto;
    }


}
