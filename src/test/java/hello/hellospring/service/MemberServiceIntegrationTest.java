package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 가급적이면, 단위별로 나뉜 테스트를 수행하는 것을 권장한다. (스프링컨테이너가 미포함된...)

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행함.
@Transactional  // RollBack을 실행하여 DB에 데이터가 들어가지 않게끔 지워버림. -> 데이터를 지우는 코드를 따로 넣지 않아도 된다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository; // clearStore를 실행하기 위해 생성함.

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

}