package hello.core.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
    //다형성을 위해서 멤버리포지토리 I를 만들었다
}
