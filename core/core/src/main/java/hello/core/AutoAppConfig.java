package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(//@Component 애노테이션이 붙은 클래스를 스캔해서 스프링빈으로 등록시킨다.
//        basePackages = "hello.core.member", //여기 경로에서부터 좀 찾아줄래?
//        basePackageClasses = AutoAppConfig.class, // 클래스를 지정해줄래?
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //AppConfig,TestConfig가 있기때문에 필터링으로 수동 Configuration을 제외시킨다.(예제 코드를 남기기위한것)
        //경로를 지정하지않으면 hello.core 부터시작해서 하위 클래스를 다 뒤진다.
        //추천 설정정보 클래스 최상단에 두고 패키지위치에 지정하지 않는것! com.hello같은 최상단에 메인 설정정보를 두고
        // @ComponentScan 어노테이션을 붙이고 basePackage는 생략한다
)
public class AutoAppConfig {

//    @Bean(name= "memoryMemberRepository")
//    MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        //자동 빈 등록 vs 수동 빈 등록이면 수동빈이 자동빈을 오버라이드한다 수동빈 우선!
//        // 하지만 CoreApp으로 실행할때는
//        //The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class], could not be registered.
//        //로 팅기기게 만든다.(수동 vs 자동 빈으로 만들면 버그가 발생할수도있으니까)
//        //코드를 조금 더 쓰더라도 항상 명확한것을 사용해라!
//    }
}
