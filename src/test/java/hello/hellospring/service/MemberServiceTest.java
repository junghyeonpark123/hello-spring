package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository; // clearStore를 실행하기 위해 생성함.

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); // 각 테스트를 시작하기 전에 객체를 새로 생성한다.
        // memberRepository를 개별로 생성하지 않고, MemberService의 매개변수로 넣어준 이유는, MemberService클래스 내에서 MemberRepository객체를 사용하는 방식으로 테스트 하기 위함이다.
        // (그렇게 하지 않아도 당장 테스트에 문제는 없겠지만, 실제 작동원리와 동일하게 테스트하기 위해...)
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // give - 어떤 상황이 주어졌을 때,
        Member member = new Member();
        member.setName("hello");

        // when - 이것을 실행 했을 때
        Long saveId = memberService.join(member);

        // then - 이러한 결과가 나와야 함.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->  memberService.join(member2));
        // assertThrows를 사용하면, memberService.join(member2)에서 발생하는 예외상황이 IllegatlStateException와 일치해야 한다.

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}