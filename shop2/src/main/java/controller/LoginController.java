package controller;


import javax.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;

public class LoginController {
	private ShopService shopService;
	private Validator validator;
	
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	
	public void setValidator(Validator validator) {// setValidator!!! not setLoginValidator!!!
		this.validator = validator;
	}
	
	@GetMapping // 리턴타입 : String => 뷰의 이름 전달
	public String loginForm(Model model) { // Model 데이터 저장
		model.addAttribute(new User());
		return null; //url : login.shop => view : login.jsp 설정
	}

	@PostMapping
	public ModelAndView login(User user, BindingResult bindResult, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		validator.validate(user, bindResult);
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		try {
			//1. db 에서 userid 고객정보 읽어 User 객체 저장
			User dbuser = shopService.getUserById(user.getUserid());
			if(dbuser == null) {
				bindResult.reject("error.login.id");
				mav.getModel().putAll(bindResult.getModel());
				return mav;
			}else {
				//2. 입력된 비밀번호 db 의 비밀번호 비교하여 일치할 경우
				if(user.getPassword().equals(dbuser.getPassword())) {
					//session.setAttribute("loginUser", dbuser) 실행
					session.setAttribute("loginUser", dbuser);
					mav.setViewName("loginsuccess");
				}else {
					bindResult.reject("error.login.password");
					mav.getModel().putAll(bindResult.getModel());
					return mav;
				}
			}
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			//3. 입력된 비밀번호와 db 의 비밀번호 비교하여 불일치할 경우 유효성 검증으로 비밀번호 확인하세요 메시지 login.jsp 페이지 전송
			bindResult.reject("error.login.user");
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		//4. 로그인이 정상적인 경우 loginsuccess.jsp 페이지로 이동
		mav.setViewName("loginsuccess"); // view 설정
		return mav;
	}
	
	
}
