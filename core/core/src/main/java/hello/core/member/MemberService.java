package hello.core.member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
    //다형성을 위해서 멤버서비스 I를 만들었다
}
