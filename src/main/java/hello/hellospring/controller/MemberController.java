package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberservice;

    // 생성자에 Autowired를 작성하면, Controller가 생성될 때-> 스프링 컨테이너에서 해당 스프링 빈(객체)를 넣어준다. (여기서는 memberSerivce객체가 들어가게 된다.)
    // ---> DI
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberservice = memberService;
    }

}
