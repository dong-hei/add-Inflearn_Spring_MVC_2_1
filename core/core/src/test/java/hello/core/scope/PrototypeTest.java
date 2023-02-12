package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
    } // 조회할때 생성되는것을 확인 할수있다 , 서로 다른 빈 참조값이 나온다.(새로운 빈을 생성한다.)
    // 생성과 의존관계 주입 초기화 까지만 관여하고 종료메소드는 실행되지 않는다.(종료메소드는 클라가 직접해야한다.)

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }
    //싱글톤 빈과 함께 의존관계 주입받을때 쓰이는 빈을 계속 유지되는것이 문제다
    //프로토타입 빈을 주입시점에만 새로 생성하는것이 아니라 사용할때마다 새로 생성해서 사용하는 것을 원한다.
    //이 문제를 해결할수있는 것 또한 스프링에 있다 ObjectFactory, ObjectProvider

    //참고! 사용할때마다 생성되는것이 아니라 주입시점에만 새로 생성하는것이다.
}
