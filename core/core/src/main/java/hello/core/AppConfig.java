package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 어노테이션 사용시 설정 정보로 사용한다,빼고 @Bean만 정의된상태에서 실행해도 되기는하는데
//CGLIB를 생성하지 않는다
//Q: 코드가 복잡해진거같은데 스프링 컨테이너 왜씀?
//A: 스프링 컨테이너가 관리해줌으로써 어마어마한 기능을 사용할수있기에 사용한다.
//A: 기능1 국젤화 기능
//A: 기능2 환경변수
//A: 기능3 이벤트를 발행하고 구독하는 모델을 지원
//A: 기능4 파일,클래스패스.외브 등에서 리소스를 편리하게 조회

//@Bean memberSvc -> new MemoryMemberRep()
//@Bean orderSvc -> new MemoryMemberRep() 각자 다른 2개의 MemoryMemberRep()가 생성되면서 싱글톤이 깨지는 것 처럼 보인다
//스프링 컨테이너는 이 문제를 어찌 해결할까?

public class AppConfig {

    @Bean // 스프링컨테이너에 등록시켜 호출한다,(name="")으로 이름을 바꿀수있긴한데 걍 관례상 쓰자
    public MemberService memberService(){
        System.out.println(" call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository()); //객체를 스프링 컨테이너에 등록한다
    }

    @Bean // 스프링컨테이너에서 관리된다. Bean name은 memberRepository로 자동설정
    public MemoryMemberRepository memberRepository() {
        System.out.println(" call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean // 빈의 이름은 중복 No!
    public OrderService orderService(){
        System.out.println(" call AppConfig.orderService");
        return new OrderServiceImp(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        System.out.println(" call AppConfig.discountPolicy");
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
    //실제 동작에 필요한 구현객체를 모두 생성한다.
    //OrderSvcImp MemberSvcImp에 대한 제어권을 모두 AppConfig가 가진다
    //제어의 역전 IoC가 일어난다.
}
