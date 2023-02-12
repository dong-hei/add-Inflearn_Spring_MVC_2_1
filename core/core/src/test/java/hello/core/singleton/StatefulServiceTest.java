package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statusfulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A사용자가 10000원을 주문
        int userAPrice = statefulService1.order("userA",10000);

        //ThreadB : B사용자가 20000원을 주문
        int userBPrice = statefulService2.order("userA",20000);

        //ThreadA: 사용자A가 주문 금액을 조회
//        int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);

//        org.assertj.core.api.Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        //만원이 나와야 되는데 2만원이 나옴 ThreadB가 값을 바꿔치기 해버림
        //싱글톤 패턴이던 스프링같은 싱글톤 컨테이너를 사용하든 싱글톤 객체는 상태를 유지하게 설계하면 안된다.
        //무상태로 설계해야한다.(상태를 유지시키지말고 return을 써서 지역변수로 넘겨 받게끔 해라)
    }


    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}