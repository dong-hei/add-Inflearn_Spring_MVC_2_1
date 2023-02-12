package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    //Null Bean을 출력하고싶을때
    static class TestBean{

        @Autowired(required = false) // true로 하면 SpringContainer에 없기때문에 오류뜸 
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        } //아예호출이 안됨

        @Autowired
        public void setNoBean2(@Nullable Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        } // 호출은되는데 Null
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        } // Optional.empty

    }
}
