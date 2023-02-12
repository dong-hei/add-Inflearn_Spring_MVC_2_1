package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();

    }
/*    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImp();*/
    //I와 I의 구현체들을 가져온다.

    @Test
    void createOrder(){
        Long memberId = 1L;
        Member member = new Member(1L, "memberA", Grade.VIP);
        // long은 null을 넣을수없기때문에 Long을 사용한다.
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
        //Junit을 통해서 테스트하는 방법이 좋은방법이다.
    }
}
