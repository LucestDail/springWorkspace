package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import exception.CartEmptyException;
import logic.Cart;
import logic.Item;
import logic.ItemSet;
import logic.ShopService;

@Controller @RequestMapping("cart")
public class CartController {
	
	@Autowired
	private ShopService service;
	
	@RequestMapping("cartAdd")
	public ModelAndView add(Integer id, Integer quantity, HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		System.out.println(id +" : "+quantity);
		Item item = service.getItem(id);
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart == null) {
			cart = new Cart();
			session.setAttribute("CART", cart);
		}
		cart.push(new ItemSet(item,quantity));
		mav.addObject("message",item.getName() + " : " + quantity + " 개 장바구니 추가");
		mav.addObject("cart",cart);
		return mav;
	}
	
	@RequestMapping("cartDelete")
	public ModelAndView delete(HttpSession session, int index) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		//cart.delete(index);
		cart.getItemSetList().remove(index);
		mav.addObject("cart",cart);
		return mav;
	}
	
	@RequestMapping("cartView")
	public ModelAndView cartView(HttpSession session) {
		ModelAndView mav = new ModelAndView("cart/cart");
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart == null || cart.getItemSetList().size() == 0) {
			throw new CartEmptyException("장바구니에 상품이 없습니다...","../item/list.shop");
		}
		mav.addObject("cart",cart);
		return mav;
	}
	
//	@RequestMapping("cartView")
//	public ModelAndView cartView(HttpSession session) {
//		return new ModelAndView("cart/cart").addObject("cart",(Cart)session.getAttribute("CART"));
//	}
	
	@GetMapping("checkout")
	public ModelAndView loginCheckcheckout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
}
