package controller;


import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import exception.RedirectException;
import logic.Item;
import logic.Sale;
import logic.SaleItem;
import logic.ShopService;
import logic.User;
import util.CipherUtil;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private ShopService service;
	@Autowired
	private CipherUtil cipher;

	// http://localhost:8080/shop3/user/userEntry.shop
	@GetMapping("userEntry")
	public ModelAndView userEntryForm() {
		ModelAndView mav = new ModelAndView();
		User u = new User();
		mav.addObject(u);
		return mav;
	}

	@PostMapping("userEntry")
	public ModelAndView create(@Valid User user, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			bindingResult.reject("error.input.user");
			return mav;
		}
		try {
			user.setPassword(cipher.makehash(user.getPassword()));
			service.insertUser(user);
			mav.addObject("user", user);
		} catch (DataIntegrityViolationException e) {
			mav.getModel().putAll(bindingResult.getModel());
			bindingResult.reject("error.duplicate.user");
			return mav;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		mav.setViewName("user/userEntrySuccess");
		mav.setViewName("redirect:login.shop");
		return mav;
	}

	@GetMapping("login")
	public ModelAndView userLoginForm() {
		ModelAndView mav = new ModelAndView();
		User u = new User();
		mav.addObject(u);
		return mav;
	}

	@PostMapping("login")
	public ModelAndView login(@Valid User user, BindingResult bindingResult, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasFieldErrors("userid") || bindingResult.hasFieldErrors("password")) {
			mav.getModel().putAll(bindingResult.getModel());
			bindingResult.reject("error.input.user");
			return mav;
		}
		try {
			User dbuser = service.getUserById(user.getUserid());
			if (dbuser == null) {
				bindingResult.reject("error.login.id");
				mav.getModel().putAll(bindingResult.getModel());
				return mav;
			} else {
				String hashPass = dbuser.getPassword();
				String curPass = cipher.makehash(user.getPassword());
				if (hashPass.equals(curPass)) {
					session.setAttribute("loginUser", dbuser);
//					mav.setViewName("user/loginsuccess");
				} else {
					bindingResult.reject("error.login.password");
					mav.getModel().putAll(bindingResult.getModel());
					return mav;
				}
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("error.login.user");
			mav.getModel().putAll(bindingResult.getModel());
			mav.setViewName("redirect:login.shop");
			return mav;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.setViewName("redirect:main.shop");
		return mav;
	}

//	@GetMapping("main")
//	public ModelAndView main() {
//		ModelAndView mav = new ModelAndView();
//		User u = new User();
//		mav.addObject(u);
//		return mav;
//	}

	@RequestMapping("main")
	public String loginCheckMain(HttpSession session) {
		return null;
	}

	@RequestMapping("logout")
	public ModelAndView loginChecklogout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		session.invalidate();
		mav.setViewName("redirect:login.shop");
		return mav;
	}

	@GetMapping("mypage")
	public ModelAndView idCheckMypage(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = null;
		user = service.getUserById(id);
		List<Sale> salelist = service.salelist(id);
		for (Sale sale : salelist) {
			List<SaleItem> saleitemlist = service.saleItemList(sale.getSaleid());
			for (SaleItem saleitem : saleitemlist) {
				Item item = service.getItem(Integer.parseInt(saleitem.getItemid()));
				saleitem.setItem(item);
			}
			sale.setItemList(saleitemlist);
		}
		mav.addObject("user", user);
		mav.addObject("salelist", salelist);
		return mav;
	}

	@GetMapping("update")
	public ModelAndView idCheckUpdate(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = null;
		try {
			user = service.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("user", user);
		return mav;
	}

	@PostMapping("update")
	public ModelAndView passCheckUpdate(@Valid User user, BindingResult bindingResult, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			bindingResult.reject("error.input.user");
			return mav;
		}
		try {
			user.setPassword(cipher.makehash(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		User dbuser = null;
		User newUserSession = null;
		if (((User) session.getAttribute("loginUser")).getUserid().equals("admin")) {//관리자가 시도
			dbuser = service.getUserById("admin");
			if (!dbuser.getPassword().equals(user.getPassword())) {//관리자인데 비밀번호 틀렸다.
				bindingResult.reject("error.login.password");
				mav.getModel().putAll(bindingResult.getModel());
				return mav;
			}
		} else {//관리자가 아닐 경우 시도
			dbuser = service.getUserById(user.getUserid());
			if (!dbuser.getPassword().equals(user.getPassword())) {//관리자가 아닌데 비밀번호 틀렸다.
				bindingResult.reject("error.login.password");
				mav.getModel().putAll(bindingResult.getModel());
				return mav;
			}
		}
		//정상적으로 비밀번호 절차 실시 후
		service.updateUser(user);
		if(((User) session.getAttribute("loginUser")).getUserid().equals("admin") && user.getUserid().equals("admin")) {
			//세션 정보도 관리자, 현재 파라매터 정보도 관리자 => 지금 바꾼 정보가 관리자 본인 정보다...
			newUserSession = service.getUserById("admin");
			session.setAttribute("loginUser", newUserSession);
		}
		if(((User) session.getAttribute("loginUser")).getUserid().equals(user.getUserid())) {
			//세션 정보가 파라매터 정보와 같다 => 지금 바꾼 정보가 본인 정보다...
			newUserSession = service.getUserById(user.getUserid());
			session.setAttribute("loginUser", newUserSession);
		}
		mav.addObject("user", newUserSession);
		mav.setViewName("redirect:mypage.shop?id=" + user.getUserid());
		return mav;
	}
	
	@GetMapping("delete")
	public ModelAndView idCheckdelete(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = null;
		try {
			user = service.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("user", user);
		return mav;
	}
	/**
	 * 본인 탈퇴 : 본인 비밀번호 검증 -> session정보 제거(로그아웃)-> 로그인페이지로 이동
	 * 관리자 탈퇴 : 관리자 비밀번호 검증 -> ../admin/list.shop으로 이동
	 * 단, 관리자 본인은 탈퇴 불가능
	 * @param user
	 * @param bindingResult
	 * @param session
	 * @return
	 */
	//goodee recommand
	@PostMapping("delete")
	public ModelAndView idCheckdelete(String userid, HttpSession session,String password) {
		ModelAndView mav = new ModelAndView();
		User loginUser = (User)session.getAttribute("loginUser");
		if(userid.equals("admin")) {
			throw new LoginException("관리자 탈퇴 불가","main.shop");
		}
		String hashPass = null;
		try {
			hashPass = cipher.makehash(password);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		if(!hashPass.equals(loginUser.getPassword())) {
			throw new LoginException("탈퇴시 비밀번호가 틀립니다.","delete.shop?id="+userid);
		}
		try {
			service.deleteUser(userid);
		}catch(Exception e) {
			e.printStackTrace();
			throw new LoginException("탈퇴시 오류가 발생했습니다.","delete.shop?id="+userid);
		}
		// 탈퇴 이후
		if(loginUser.getUserid().equals("admin")) {
			mav.setViewName("redirect:/admin/list.shop");
		}else {
//			mav.setViewName("redirect:logout.shop");
			session.invalidate();
			throw new LoginException(userid + "회원님의 탈퇴 처리가 완료되었습니다.","login.shop");
		}
		return mav;
	}
	
	
	@PostMapping("userdelete")
	public ModelAndView userdelete(/* @Valid */ User user, /* BindingResult bindingResult, */ HttpSession session) {
		System.out.println("current user : " + user.getUserid() + "," + user.getPassword());
		ModelAndView mav = new ModelAndView();
//		if (bindingResult.hasFieldErrors("password")) {
//			mav.getModel().putAll(bindingResult.getModel());
//			bindingResult.reject("error.input.user");
//			return mav;
//		}
		User dbuser = null;
		if (((User) session.getAttribute("loginUser")).getUserid().equals("admin")) {
			//시도 대상 : 관리자
			dbuser = service.getUserById("admin");
			if (!dbuser.getPassword().equals(user.getPassword())) {
				//관리자 비밀번호 오류
//				bindingResult.reject("error.login.password");
//				mav.getModel().putAll(bindingResult.getModel());
				mav.setViewName("redirect:delete.shop?id="+user.getUserid());
				return mav;
			}else {
				//관리자 비밀번호 정답
				if(user.getUserid().equals("admin")) {
					//지우려는 대상이 관리자인 경우
//					bindingResult.reject("error.admin.delete");
//					mav.getModel().putAll(bindingResult.getModel());
					mav.setViewName("redirect:../admin/list.shop");
				}else {
					//관리자 -> 일반유저 삭제
					service.deleteUser(user.getUserid());
					mav.setViewName("redirect:../admin/list.shop");
				}
			}
		} else {
			//시도 대상 : 일반 유저
			dbuser = service.getUserById(user.getUserid());
			if (!dbuser.getPassword().equals(user.getPassword())) {
				//일반 유저 비밀번호 오류
//				bindingResult.reject("error.login.password");
//				mav.getModel().putAll(bindingResult.getModel());
				mav.setViewName("redirect:delete.shop?id="+user.getUserid());
				return mav;
			}else {
				//일반유저 비밀번호 정답
				service.deleteUser(user.getUserid());
				session.invalidate();
				mav.setViewName("redirect:login.shop");
			}
		}
		return mav;
	}
	
	@GetMapping("idform")
	public ModelAndView idform() {
		ModelAndView mav = new ModelAndView();
		User u = new User();
		mav.addObject(u);
		return mav;
	}
	
	@GetMapping({"pwsearch","idsearch"})
	public ModelAndView formview() {
		ModelAndView mav = new ModelAndView();
		User u = new User();
		mav.addObject(u);
		return mav;
	}
	@PostMapping("{url}search")
	public ModelAndView search(User user, BindingResult bindingResult, @PathVariable String url) {
		ModelAndView mav = new ModelAndView();
		System.out.println(user.getUserid() + "," + user.getEmail() + "," + user.getPhoneno());
		String code = "error.userid.search";
		String title = "아이디";
		if(url.equals("pw")) { //url pw 포함시 pwsearch 이므로 id 확인, 없을 경우 bindingresult reject value
			code = "error.password.search";
			title = "비밀번호";
			if(user.getUserid() == null || user.getUserid().equals("")) {
				bindingResult.rejectValue("userid", "error.required");
			}
		}
		if(user.getEmail() == null || user.getEmail().equals("")) {
			bindingResult.rejectValue("email", "error.required");
		}
		if(user.getPhoneno() == null || user.getPhoneno().equals("")) {
			bindingResult.rejectValue("phoneno", "error.required");
		}
		if(bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			return mav;
		}
//		return mav;
		if(user.getUserid() != null && user.getUserid().equals("")) {
			user.setUserid(null);
		}
		String result = null;
		try {
			result = service.getSearch(user);
		}catch(EmptyResultDataAccessException e) {
			bindingResult.reject(code);
			e.printStackTrace();
			return mav;
		}
		mav.addObject("result",result);
		mav.addObject("title",title);
		if(url.equals("pw")) {
			mav.addObject("pass",result.substring(0,2) + "**");
			mav.setViewName("user/pw");
			return mav;
		}
		mav.addObject("id",result);
		mav.setViewName("user/id");
		return mav;
		
	}
	
	
	@PostMapping("id")
	public ModelAndView idsearch(String email, String tel) {
		ModelAndView mav = new ModelAndView();
		User user = service.getUserByEmailTel(email,tel);
		if(user == null) {
			throw new RedirectException("해당되는 정보가 없습니다","window.close()");
		}
		mav.addObject("id",user.getUserid());
		return mav;
	}
	
	@GetMapping("pwform")
	public ModelAndView pwform() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@PostMapping("pw")
	public ModelAndView pwsearch(String id, String email, String tel) {
		ModelAndView mav = new ModelAndView();
		User user = service.getUserByIdEmailTel(id,email,tel);
		if(user == null) {
			throw new RedirectException("해당되는 정보가 없습니다","window.close()");
		}
		String returnPassValue = user.getPassword().substring(0,2) + "**";
		mav.addObject("pass",returnPassValue);
		return mav;
	}
	
	@GetMapping("passwordform")
	public ModelAndView passwordform() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
//	@PostMapping("passwordform")
//	public ModelAndView passwordchg(String pass, String chgpass, String chgpass2, HttpSession session) {
//		ModelAndView mav = new ModelAndView();
//		String sessionUserid = ((User)session.getAttribute("loginUser")).getUserid();
//		if(!pass.equals(service.getUserById(sessionUserid).getPassword())) {
//			throw new RedirectException("현재 비밀번호가 기존 비밀번호와 다릅니다.","window.close()");
//		}else if(!chgpass.equals(chgpass2)) {
//			throw new RedirectException("변경 비밀번호와 변경 비밀번호 재입력이 다릅니다.","window.close()");
//		}else if(pass.equals(chgpass)){
//			throw new RedirectException("현재 비밀번호와 변경 비밀번호가 같습니다.","window.close()");
//		}else {
//			service.chgpassUser(sessionUserid,chgpass);
//			User newuser = service.getUserById(sessionUserid);
//			session.setAttribute("loginUser", newuser);
//			throw new RedirectException("비밀번호가 변경되었습니다!","window.close()");
//		}
//	}
	
	@PostMapping("passwordform")
	//Param 이 여러개일 경우 Map 으로 받아올 수 있습니다! (requestParam : value) 순으로 key-value 형식 맵핑
	public ModelAndView passwordchg(@RequestParam Map<String, String> param, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String sessionUserid = ((User)session.getAttribute("loginUser")).getUserid();
		try {
			param.put("pass", cipher.makehash(param.get("pass")));
			param.put("chgpass", cipher.makehash(param.get("chgpass")));
			param.put("chgpass2", cipher.makehash(param.get("chgpass2")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!param.get("pass").equals(service.getUserById(sessionUserid).getPassword())) {
			throw new RedirectException("현재 비밀번호가 기존 비밀번호와 다릅니다.","window.close()");
		}else if(!param.get("chgpass").equals(param.get("chgpass2"))) {
			throw new RedirectException("변경 비밀번호와 변경 비밀번호 재입력이 다릅니다.","window.close()");
		}else if(param.get("pass").equals(param.get("chgpass"))){
			throw new RedirectException("현재 비밀번호와 변경 비밀번호가 같습니다.","window.close()");
		}else {
			service.chgpassUser(sessionUserid,param.get("chgpass"));
			User newuser = service.getUserById(sessionUserid);
			session.setAttribute("loginUser", newuser);
			throw new RedirectException("비밀번호가 변경되었습니다!","window.close()");
		}
	}
}
