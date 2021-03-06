package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;
import util.UserValidator;

public class UserEntryController {
	private ShopService shopService;
	private UserValidator userValidator;
	
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	public void setUserValidator(UserValidator userValidator) {
		this.userValidator = userValidator;
	}
	
	//http://localhost:8080/shop2/userEntry.shop
	@GetMapping //Get 방식으로 요청이 들어온 경우 호출되는 메소드
	public ModelAndView userEntryForm() {
		ModelAndView mav = new ModelAndView();
		User u = new User();
		mav.addObject(u);
		return mav;
	}
	
	//User 객체 프로퍼티와 파라매터 이름 비교하여 값을 user 객체에 파라매터 값을 저장
	// user.setUserid(request.getParameter("userid")); 같은...
	@PostMapping //post 방식으로 요청이 들어온 경우 호출되는 메소드
	public ModelAndView userEntry(User user, BindingResult bindResult) {
		ModelAndView mav = new ModelAndView();
		userValidator.validate(user,bindResult);
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		//입력값 검증이 완료 상태. 정상 입력
		try {
			shopService.insertUser(user);
			mav.addObject("user",user);
			//중복키 오류 발생할 경우 스프링 jdbc 프레임워크에서 발생
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindResult.reject("error.duplicate.user"); // 글로벌 오류
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		mav.setViewName("userEntrySuccess"); // view 설정
		return mav; // /WEB-INF/view/userEntrySuccess.jsp 페이지로 이동
	}
	
	@InitBinder // 파라매터값을 프로퍼티의 자료형에 맞도록 형변환 해줌
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//Date.class : 프로퍼티 자료형 지정
		// CustomDateEditor : 형식 지정
		//false : 빈칸 불가능, true 할 경우에는 빈칸은 허용함
		binder.registerCustomEditor(Date.class,  new CustomDateEditor(format,false));
	}
}
