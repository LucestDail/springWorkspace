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
import logic.Sale;
import logic.ShopService;
import logic.User;

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
	public ModelAndView checkout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@RequestMapping("end")
	/**
	 * 주문 확정 : 로그인 상태, 장바구니 상품 존재해야 접근 가능 => aop 설정(촏ㅊ
	 * 1. 장바구니 상품(CART)을 saleitem 테이블에 저장
	 * 2. 로그인 정보(loginUser)로 주문 정보(sale) 테이블에 저장
	 * 3. 장바구니 상품 제거
	 * @param session
	 * @return
	 */
	public ModelAndView checkend(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Cart cart = (Cart)session.getAttribute("CART");
		User loginUser = (User)session.getAttribute("loginUser");
		Sale sale = service.checkend(loginUser,cart);
		long total = cart.getTotal();
		session.removeAttribute("CART");
		mav.addObject("sale",sale);
		mav.addObject("total",total);
		return mav;
	}
}
