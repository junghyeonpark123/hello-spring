package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberservice;

    // 생성자에 Autowired를 작성하면, Controller가 생성될 때-> 스프링 컨테이너에서 해당 스프링 빈(객체)을 넣어준다. (여기서는 memberSerivce객체가 들어가게 된다.)
    // ---> DI
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberservice = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberservice.join(member);

        return ("redirect:/");
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberservice.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


}
