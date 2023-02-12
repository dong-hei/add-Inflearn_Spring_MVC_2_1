package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    // 자기 자신 인스턴스를 생성해서 안에 넣는다

    public static SingletonService getInstance(){
        return instance;
    }
    //객체 인스턴스가 필요하면 오직 getInstance() 메소드를 통해서만 조회할수 있다
    //항상 같은 인스턴스 반환 가능

    private SingletonService(){
    }
    //private으로 혹시모를 new 키워드로 객체 인스턴스가 생성되는것을 막는다.

    public void logic(){
        System.out.println("객체 로직 호출");
    }
    //싱글톤 패턴의 단점
    // 구현 코드가 많이 들어간다.
    // 의존관계상 클라가 구현클래스에 의존한다 -> DIP 위반
    // 클라가 구현클래스에 의존해 OCP 원칙을 위반할 가능성이 높다.
    // 테스트하기 어렵다.
    // 내부속성을 변경하거나 초기화 하기 어렵다
    // private 생성자로 자식 클래스를 만들기 어렵다
    // 결론적으로 유연성이 떨어지는 안티패턴으로 불리기도 한다.
}
