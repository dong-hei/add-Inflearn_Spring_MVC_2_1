package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //빈을 자동으로 등록시키는 어노테이션, memberServiceImpl 이런식으로 앞에 대문자를 소문자로 바꿔주고 자동등록된다.
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired // ac.getBean(MemberRepository.class) 기능을 수행한다.(그러나 Autowired가 기능은 더 많다.)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //멤버리포지토리가 뭐가들어갈지 생성자를 통해서 건드린다.

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
    //MemberService의 구현체를 만들어봤다.

    //테스트 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
