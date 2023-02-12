package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request" , proxyMode = ScopedProxyMode.TARGET_CLASS)
//proxyMode = CGLIB이라는 가짜 프록시를 만들어 주입시켜준다 myLogger.logic()을 호출한다
//클라입장에서는 원본인지 아닌지 모른다(다형성)
//이런 특별한 scope는 꼭 필요한 곳에서만 최소화해서 사용하자,무분별하게 사용하면 유지보수가 어렵다.
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL){
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope been created:" + this);

    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope been close:" + this);
    }
}
