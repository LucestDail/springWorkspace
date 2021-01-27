package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import logic.ShopService;

@RestController // @Controller 호환 가능(하위 클래스) + @ResponseBody 기능 추가
// @Responsebody? View 없이 Controller에서 직접 데이터를 클라이언트에 전송
@RequestMapping("ajax")
public class AjaxController {
	@Autowired
	private ShopService service;
	/**
	 * value="exchange" : 요청 url 설정. /ajax/exchange.shop
	 * produces = "text/html; charset=UTF-8" : 클라이언트에 데이터 전송될때 문서 종류, 인코딩 방식 설정
	 * @return
	 */
	@RequestMapping(value="exchange",produces="text/html; charset=UTF-8")
	public String exchange() {
		Document doc = null;
		List<String> list = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		String url="https://www.koreaexim.go.kr/site/program/financial/exchange?menuid=001001004002001";
		try {
			doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tc");
			Elements e2 = doc.select(".tl2.bdl");
			for(int i = 0; i < e1.size(); i++) {
				if(e1.get(i).html().equals("KRW") ||e1.get(i).html().equals("GBP") ||e1.get(i).html().equals("USD") || e1.get(i).html().equals("JPY(100)") || e1.get(i).html().equals("CNH") || e1.get(i).html().equals("EUR")) {
					list.add(e1.get(i).html());
					for(int j = 0; j <= 6; j++) {
						list.add(e1.get(i+j).html());
					}
				}
			}
			for(Element ele : e2) {
				if(ele.html().contains("한국") ||ele.html().contains("영국") ||ele.html().contains("미국") || ele.html().contains("일본") || ele.html().contains("위안화") || ele.html().contains("유로")) {
					list2.add(ele.html());
				}
			}
			System.out.println("list : " + list);
			System.out.println("list2 : " + list2);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//1, 2, 3,4
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		StringBuilder html = new StringBuilder();
		html.append("<table class='table' style='text-align:center;'>");
		html.append("<caption>수출입은행 : "+today+"</caption>");
		html.append("<tr><th>통화</th><th>받을때</th><th>보낼때</th><th>기준율</th></tr>");
		int temp = list.size()/list2.size();
		for(int i = 0; i < list2.size(); i++) {
			html.append("<tr><td>" + list2.get(i)+ "<br>" + list.get(i*temp+1) + "</td>");
			html.append("<td>" + list.get(i*temp+2) + "</td>");
			html.append("<td>" + list.get(i*temp+3) + "</td>");
			html.append("<td>" +list.get(i*temp+4) +"</td></tr>");
		}
		html.append("</table>");
		return html.toString();
	}
	
}
