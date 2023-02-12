package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        //조회: 호출할때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //조회 2
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberSvc1과 memberSvc2는 다르다
        assertThat(memberService1).isNotSameAs(memberService2);
    }
    //memberService1 = hello.core.member.MemberServiceImpl@2357d90a
    //memberService2 = hello.core.member.MemberServiceImpl@6328d34a
    //생성할때마다 다른 객체가 생성된다.
    //웹 App특징은 고객 요청이 많은데 감당 불가능(메모리 낭비가 심하다)
    //해결방안은 해당 객체가 딱 1개만 생성되고 공유하도록 설계 - 싱글톤
    // 2개가 생성하지 못하도록 막는것 -> private 생성자를 사용해 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야함

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonSvcTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        assertThat(singletonService1).isSameAs(singletonService2);
        // same == (같나?)
        // equal java equals
    } // 객체 인스턴스를 생성하는것을 그대로 가져다쓰는것을 확인

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContaniner(){
//       싱글톤 패턴 AppConfig appConfig = new AppConfig();
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        
        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberSvc1과 memberSvc2는 다르다
        assertThat(memberService1).isSameAs(memberService2);
    }

}
