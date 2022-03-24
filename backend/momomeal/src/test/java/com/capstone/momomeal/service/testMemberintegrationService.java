package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.Members;
import com.capstone.momomeal.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional  //Test에서 실행후 Test가 실행전으로 Rollback해준다
public class testMemberintegrationService {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    //@Commit //Transactional을 무시하고 롤백안한다
    void 회원가입(){ //회원 가입
        //given
        Members member1 = new Members();
        Members member2 = new Members();
        member1.setUserid("springTest3");
        member1.setPwd("springpwd3");
        member1.setUsername("springName3");


        //when
        String name = memberService.join(member1);
        System.out.println(name);

        //then
        //assertThat(one.getUserName()).isEqualTo(memberDTO.getUserName());
    }

    @Test
    void 리스트확인(){
        List<Members> listdata = memberService.findAll();
        for(Members data : listdata){
            System.out.println(data.toString());
        }
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Members member1 = new Members();
        member1.setUserid("test1");
        member1.setPwd("testpwd2");
        member1.setUsername("Kang");

//        memberService.join(member1);
//        memberService.join(member3);
        //assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        try {
            memberService.join(member1);
        }catch(IllegalStateException e){
        }
    }

    @Test
    public void 로그인확인_성공(){
        //given
        Members members1 = new Members();
        members1.setUserid("test1");
        members1.setPwd("testpwd1");
        Optional<Members> result = null;
        try {
            result = memberService.Login("test1","testpwd1");
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        System.out.println(result);
        if(result == null){
            fail("아이디 와 비번이 동시에 같은 데이터값을 찾지 못했습니다");
        }
    }

    @Test
    public void 로그인확인_실패(){
        //given
        Members members1 = new Members();
        members1.setUserid("test1");
        members1.setPwd("testpwd2");
        Optional<Members> result = null;
        try {
            result = memberService.Login("test1","testpwd1");
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }
        System.out.println(result.toString());
        if(result != null){
            fail("아이디 와 비번이 동시에 같은 데이터값을 찾지 못해서 성공했습니다");
        }
    }
}
