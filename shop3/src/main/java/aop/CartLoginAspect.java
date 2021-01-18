package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.Cart;
import logic.User;

@Component
@Aspect
@Order(1)
public class CartLoginAspect {
	@Around("execution(* controller.Cart*.loginCheck*(..)) && args(session)")
	public Object cartLoginCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
		User loginUser = (User) session.getAttribute("loginUser");
		Cart cart = (Cart) session.getAttribute("CART");
		System.out.println("cartLogin checked");
		if(loginUser == null) {
			throw new LoginException("[cartLogin] 로그인 후 거래하세요","../user/login.shop");
		}else {
			System.out.println(loginUser);
		}
		if(cart == null) {
			throw new LoginException("[cartLogin]카트가 비어있습니다.","../item/list.shop");
		}else {
			System.out.println(cart);
		}
		return joinPoint.proceed();
	}
}
