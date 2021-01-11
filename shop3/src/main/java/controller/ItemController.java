package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

@Controller //@Component의 기능을 가지고 있음... 객체화 + Controller
@RequestMapping("item") // item/* 요청시 호출되는 컨트롤러
public class ItemController {
	@Autowired // 자료형 기준 객체 주입
	private ShopService service;
	
	@RequestMapping("list")// item/list.shop 호출시 요청
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();// /WEB-INF/view/item/list.jsp -> 뷰로 설정
		List<Item> itemList = service.getItemList();// itemList : item 테이블 모든 레코드 정보 저장
		mav.addObject("itemList",itemList); // 뷰단 itemList 라는 이름으로 전달
		return mav;
	}
	@RequestMapping({"detail","edit"})
//	@RequestMapping("*")
	public ModelAndView detail(Integer id) {
		ModelAndView mav = new ModelAndView();
		// item : id 에 해당하는 item 테이블의 1개의 레코드를 저장
		Item item = service.getItem(id);
		mav.addObject("item",item);
		return mav;
	}
	
	@RequestMapping("create")
	public String addform(Model model) {
		model.addAttribute(new Item());
		return "item/add";
	}
	
	@RequestMapping("register")
	public ModelAndView add(@Valid Item item, BindingResult bindingResult, HttpServletRequest request) {
		//@Valid : 유효성 검사를 하는 어노테이션, Item class 부분에 유효성 검사를 위한 부분이 들어가야함
		ModelAndView mav = new ModelAndView("item/add");
		if(bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			return mav;
		}
		service.itemCreate(item, request);
		mav.setViewName("redirect:/item/list.shop");
		return mav;
	}
	@PostMapping("update")
	public ModelAndView update(@Valid Item item, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/edit");
		if(bindingResult.hasErrors()) {
			mav.getModel().putAll(bindingResult.getModel());
			return mav;
		}
		return mav;
	}
}
