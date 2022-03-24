package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.Members;
import com.capstone.momomeal.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional //JPA 사용할때 저장할때 필요한것이다
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    public String join(Members member){
        // 같은 이름있는 중복 회원은 안됀다
        log.info("member userID = {}",member.getUserid());
        log.info("member = {}",memberRepository.findById(member.getUserid()));
        if(memberRepository.findById(member.getUserid()) != null){
            throw new IllegalStateException("중복 아이디 존재");
        }
        memberRepository.save(member);
        return member.getUsername();
    }

    /**
     * 맴버 검색
     * @return
     */
    public List<Members> findAll(){
        return memberRepository.findAll();
    }

    public Optional<Members> findOne(String userid){
        return memberRepository.findById(userid);
    }

    public Optional<Members> Login(String userid, String pwd){
        return memberRepository.findIdAndPwd(userid, pwd);
    }
}
