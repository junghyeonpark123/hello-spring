package repository;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();
    // 앞에 MemoryMemberRepository 가 아닌, MemberRepository 만 쓰면, afterEach 함수 때문에 Error발생.
    // 다운 캐스팅 --> 자식클래스에 존재하는 인스턴스 변수나 메소드에 접근이 불가함(자식클래스의 참조변수가 부모클래스의 객체를 참조할 경우에만, 부모클래스 구성요소에 접근이 가능하다.)

    @AfterEach // 각 테스트가 종료할 때마다 해당 어노테이션에 맵핑된 메소드가 실행된다.
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result); // test code => alt+enter로 Assertions를 추가해야 한다.
    }

    @Test
    public void findByname(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2); // 여기까지 테스트 할 객체 생성

        Member result = repository.findByName("spring2").get();

        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2); // 여기까지 테스트 할 객체 생성

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }

    // 1. 테스트는, 테스트 간에 의존관계가 없어야 한다. 그래서 각 테스트별로 객체를 그때그때 생성해줌.
    // 2. 모든 테스트는, 메소드 간의 별도 순서가 정해져있지 않다,
    // 1,2로 인해서, 여러 개의 메소드를 동시에 실행하면 Error발생. ( 이 코드에서도 마찬가지)
    // 해결법 -> repository(저장소)를 다 지워주는 메소드를 콜백메소드로 작성해준다.
}
