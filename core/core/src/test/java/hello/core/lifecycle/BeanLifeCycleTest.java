package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{
        @Bean //(initMethod = "init", destroyMethod = "close") //등록 초기화,소멸 메소드
        //@Bean destroyMethod에는 특별한 기능이 있다. 디폴트가 "(inferred)"(추론), 즉 close,shutdown 메소드를 자동으로 호출해준다.
        //따라서 직접 스프링 빈으로 등록하면 종료메소드는 따로 적어주지 않아도 잘 동작한다.
        //추론이 싫으면 destroyMethod="" 처럼 공백으로 저장한다
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://h2.com");
            return networkClient;
        } // 생성자 호출 url = null  connect : null call: null message = 초기화 연결메세지 가 나온다
        //객체 생성단계에 url이 없고 객체를 생성한 다음에 외부에서 수정자 주입을 통해 setUrl()이 호출되어야 url이 존재한다.
        //스프링 빈의 이벤트 라이프 사이클
        //스프링 컨테이너 생성 -> 스프링 빈 생성 -> 객체 생성 -> 의존관계 주입 -> 초깋화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료(싱글톤)
        //참고! 객체의 생성과 초기화를 분리하면 유지보수에 좋다
    }
}
