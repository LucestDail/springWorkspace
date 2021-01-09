package annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("loggingAspect")
@Aspect
@Order(3)
public class LoggingAspect {
	final String publicMethod = "execution(public * annotation..*(..))";
	@Before(publicMethod)
	public void before() {
		System.out.println("[LA] 메소드 실행 전 실행");
	}
	
	@AfterReturning(pointcut=publicMethod, returning="ret")
	public void afterReturning(Object ret) {
		System.out.println("[LA] 메소드 정상 반환 후 실행. 리턴값 : " + ret);
	}
	
	@AfterThrowing(pointcut=publicMethod, throwing="ex")
	public void afterThrowing(Throwable ex) {
		System.out.println("[LA] 메소드 비정상 반환 후 실행. 에러 : " + ex.getMessage());
	}
	
	@After(publicMethod)
	public void afterFinally() {
		System.out.println("[LA] 메소드 종류 후 실행");
	}

}
