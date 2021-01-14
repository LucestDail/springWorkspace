package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.Sale;
import logic.SaleItem;
import logic.ShopService;
import logic.User;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private ShopService service;

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
			service.insertUser(user);
			mav.addObject("user", user);
		} catch (DataIntegrityViolationException e) {
			mav.getModel().putAll(bindingResult.getModel());
			bindingResult.reject("error.duplicate.user");
			return mav;
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
				System.out.println("is not null now");
				if (user.getPassword().equals(dbuser.getPassword())) {
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

		User dbuser = null;
		if (((User) session.getAttribute("loginUser")).getUserid().equals("admin")) {

			dbuser = service.getUserById("admin");
			if (!dbuser.getPassword().equals(user.getPassword())) {

				bindingResult.reject("error.login.password");
				mav.getModel().putAll(bindingResult.getModel());
				return mav;
			}
		} else {

			dbuser = service.getUserById(user.getUserid());
			if (!dbuser.getPassword().equals(user.getPassword())) {

				bindingResult.reject("error.login.password");
				mav.getModel().putAll(bindingResult.getModel());
				return mav;
			}
		}

		service.updateUser(user);
		mav.addObject("user", user);
		mav.setViewName("redirect:mypage.shop?id=" + user.getUserid());
		return mav;
	}

}
