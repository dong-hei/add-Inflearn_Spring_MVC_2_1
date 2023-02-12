package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    } 수정자 주입, "선택,변경" 가능성이 있는 의존관계에서 사용 // 누군가 실수로 변경할수 있기에 좋은 설계방법은 아님

   @Autowired  //(생략가능) @RequiredArgsConstructor(롬복으로 쓸시)
   //타입 매칭의 결과가 2개이상일 때 필드 명,파라미터 명으로 빈 이름 매칭
    public OrderServiceImp(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
//        //생성자 주입을 함으로써 DI원칙을 지키고있다.
//        //생성자가 딱 1개 있으면 Autowired 생략 가능
//        //의존관계주입의 누락을 막는다.
//        //final 키워드를 넣을수 있게됨으로써 생성자에서 값이 설정되지않는 오류를 컴파일시점에 막아준다.
//        //생성자 호출시점 딱 1번만 호출되는 것이 보장된다. "불변,필수" 의존관계에 사용
   }
    
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 추상에도 의존하고 구현체에도 의존한다. DIP 위반!
/*    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();*/
    //의존관계가 있는이상 소스코드를 변경해야한다. Fix->RateDiscountPolicy로 변경하는순간 OCP 위반!
    //그럼 DIP와 OCP를 지켜야하는데 어떡해야할까?


    //I에만 의존한다.구현에 의존하지않고 I에만 의존한다.
    //구현이 안되있으니 당연히 테스트하면 성공할수가없다.(NullPointerException)
    //그럼 DIP를 어떻게 지켜야할까?
    //관심사 분리를 통해 구현객체를 생성하고 연결하는 책임을 가지는 설정 클래스를 만들자.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    //AppConfig가 연결해줌으로써 기능을 실행해 주기만 하면된다.
}
