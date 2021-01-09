package controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;
//index.shop 요청시 호출되는 객체
public class IndexController {
	private ShopService shopService;
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	//index.shop 요청시 메소드 호출됨...
	@RequestMapping
	public ModelAndView itemList() {
		//ModelAndView : Data/view 저장 -> DispatcherServlet 객체에 전달
		List<Item> itemList = shopService.getItemList();
		// view name : index -> spring-mvc.xml 에 의해서 /WEB-INF/view/index.jsp 로 설정
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("itemList",itemList);//data -> view 전송
		return mav;
	}

}
