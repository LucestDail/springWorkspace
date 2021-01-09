package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

public class DetailController {
	private ShopService shopService;
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	
	//parameter 이름이 아규먼트 이름과 같다면 값을 저장함!!!!!!!!
	@RequestMapping
	public ModelAndView detail(Integer id) {
		Item item = shopService.getItemById(id);
		//view 이름이 없으면 detail.shop 의 경우 detail 로 url 확장자 제외값 파싱하여 가져옴
		ModelAndView mav = new ModelAndView();
		mav.addObject("item",item);
		return mav;
	}
}
