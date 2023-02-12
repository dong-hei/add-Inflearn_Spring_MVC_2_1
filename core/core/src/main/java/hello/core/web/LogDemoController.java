package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    } //No thread-bound request found 오류 java.lang.IllegalStateException
    //스프링컨테이너가 뜨는시점에 HttpRequest 요청이 없음 => 웹스코프의 생존범위가 아니라서 생성도 안된 빈을 요청한것
    //그것은 ObjectProvider<>가 해결해준다.
    //이 정도에서 끝내도 되지만 개발자들의 코드를 줄이려는 욕심은 끝이없다 => @프록시타입

}
