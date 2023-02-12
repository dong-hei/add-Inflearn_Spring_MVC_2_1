package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic(); // 생성시점에 주입된 그 프로토타입
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic(); // 생성시점에 주입된 그 프로토타입(1에서 쓰인 그 것)
        Assertions.assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean{ // 싱글톤 빈 ,빈 자동등록
        private final PrototypeBean prototypeBean; // 생성시점에 주입이 된다.

        @Autowired
        private ObjectProvider<PrototypeBean> beanObjectProvider; //지정된 빈을 컨테이너에서 대신 찾아주는 DL(의존관계 Lookup)서비스

    public int logic(){
        PrototypeBean prototypeBean = beanObjectProvider.getObject(); //얘를 통해 항상 새로운 프로토타입 빈이 생성
        prototypeBean.addCount();
        int count = prototypeBean.getCount(); // 생성시점에 주입된 그 프로토타입
        return count;
    }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("Prototype.destroy");
        }
    } //프로토타입 빈을 사용하는 일은 드물다.
}
