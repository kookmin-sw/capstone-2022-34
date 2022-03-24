package com.capstone.momomeal.repository;

import com.capstone.momomeal.domain.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryUserRepository {
    private final UserVO user = new UserVO();

    //로그인시 메모리에 저장
    public void loginSuccess(String userId, String pwd,String userName){
        user.setUserId(userId);
        user.setPwd(pwd);
        user.setUserName(userName);
        user.setLogin(true);
    }
    //user Data 꺼내오기
    public UserVO getUser(){
        return user;
    }

    //로그인 확인
    public boolean checkLogin(){
        return user.isLogin();
    }
}
