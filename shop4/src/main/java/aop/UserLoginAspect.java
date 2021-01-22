package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

@Component
@Aspect
@Order(1)
public class UserLoginAspect {
	@Around("execution(* controller.User*.loginCheck*(..)) && args(..,session)")
	public Object userLoginCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
		User loginUser = (User) session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("[userLogin] 로그인 후 거래하세요","login.shop");
		}
		return joinPoint.proceed();
	}
	
	@Around("execution(* controller.User*.idCheck*(..)) && args(id,session,..)")
	public Object userIdCheck(ProceedingJoinPoint joinPoint, String id, HttpSession session) throws Throwable{
		User loginUser = (User) session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("[userId] 로그인 후 거래하세요","login.shop");
		}else if (!loginUser.getUserid().equals(id) && !loginUser.getUserid().equals("admin")) {
			throw new LoginException("[userId] 내 정보만 확인할 수 있습니다.","mypage.shop?id="+loginUser.getUserid());
		}
		return joinPoint.proceed();
	}
}
