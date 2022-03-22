package com.capstone.momomeal.controller;

import com.capstone.momomeal.domain.Members;
import com.capstone.momomeal.domain.UserVO;
import com.capstone.momomeal.repository.MemoryUserRepository;
import com.capstone.momomeal.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Controller
public class HomeController {

    @Autowired MemberService memberService;
    @Autowired MemoryUserRepository memoryUserService;

    //메인 화면
    @RequestMapping(value = "/home")
    public String homeLogout(Model model){
        log.info("checkLogin = "+ memoryUserService.checkLogin());
        model.addAttribute("logcheck",memoryUserService.checkLogin());
        return "home";
    }

    //로그인화면 이동, 또는 프로필 이동
    @RequestMapping(value = "/home/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("id");
        String pwd = request.getParameter("pwd");

        Optional<Members> result = memberService.Login(userid, pwd);
        log.info("result id = {} pwd = {}",result.get().getUserid(),result.get().getPwd());
        if(result != null){ //로그인 성공
            memoryUserService.loginSuccess(result.get().getUserid(), result.get().getPwd(), result.get().getNickname());

            UserVO user = memoryUserService.getUser();
            log.info("homeController userVO = {}", user.toString());

            return "profile/homeprofile";
        }
        return "profile/failprofile";
    }

}
