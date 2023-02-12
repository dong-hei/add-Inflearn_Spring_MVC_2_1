package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //annotation 기반으로 작동하는 스프링컨테이너를 생성!
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        //memberSvc의 MemberSvc.class를 꺼내올거야
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        //orderSvc의 OrderSvc.class를 꺼내올거야

/*        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();*/
        //AppConfig 클래스에서
        //MemberServiceImpl을 반환 MemoryMemberRepository 객체를 참조하도록 객체를 넘긴다.
        //OrderServiceImpl을 반환 생성자로 MemoryMemberRep,FixDiscount 객체를 참조하도록 객체를 넘긴다.

//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImp(null,null);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId,"itemA", 20000);

        System.out.println("order = " + order);
        System.out.println("order.calPrice() = " + order.calPrice());

    }
}
