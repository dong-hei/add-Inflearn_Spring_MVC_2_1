package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.directory.InitialDirContext;

//InitializingBean, DisposableBean 단점
//해당 코드가 스프링 전용 인터페이스에 의존한다, 이름 변경 불가, 코드를 고칠수 없는 외부 라이브러리에 적용할수없다.
//지금은 더 나은 방법이 있기에 거의 사용하지않는다.
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출,url" + url);
        connect();
        call("초기화 연결 메세지");
    }

    public void setUrl(String url){
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect" + url);
    }

    public void call(String message){
        System.out.println("call" + url + "message = " + message);
    }

    public void disconnect(){
        System.out.println("close" + url);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//
//        connect();
//        call("초기화 연결 메세지");
//    }//InitializingBean 인터페이스 : 의존관계가 끝나면 주입할게!
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destory");
//        disconnect();
//    } //DisposableBean  인터페이스 : 종료될때 안전하게 종료할게!

    @PostConstruct // 초기화 외부 라이브러리에 사용하지 못하는게 유일한 단점
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy // 종료 외부 라이브러리에 사용하지 못하는게 유일한 단점
    public void close(){
        System.out.println("NetworkClient.close");
        connect();
        disconnect();
    }
}
