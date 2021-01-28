package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
	
	@RequestMapping(value="exchange2",produces="text/html; charset=utf-8")
	public String exchange2() {
		Map<String, List<String>> map = new HashMap<>();
		StringBuilder html = new StringBuilder();
		String url = "http://fx.kebhana.com/FER1101M.web";
		try {
			String kebhana = Jsoup.connect(url).get().text();
			String strJson = kebhana.substring(kebhana.indexOf("{"));
			JSONParser jsonParser = new JSONParser();
			JSONObject json = (JSONObject) jsonParser.parse(strJson.trim());
			JSONArray array = (JSONArray) json.get("리스트");
			for(int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				if(obj.get("통화명").toString().contains("미국")||
				   obj.get("통화명").toString().contains("일본")||
				   obj.get("통화명").toString().contains("유로")||
				   obj.get("통화명").toString().contains("영국")||
				   obj.get("통화명").toString().contains("중국")) {
					String str = obj.get("통화명").toString();
					String[] sarr = str.split(" ");
					String key = sarr[0];
					String code = sarr[1];
					List<String> list = new ArrayList<String>();
					list.add(code);
					list.add(obj.get("매매기준율").toString());
					list.add(obj.get("현찰파실때").toString());
					list.add(obj.get("현찰사실때").toString());
					map.put(key, list);
				}
			}
			html.append("<table class = 'table' style='text-align:center;'>");
			html.append("<caption>KEB 하나은행 : " + json.get("날짜").toString() + "</caption>");
			html.append("<tr><th rowspan = '2'>코드</th><th rowspan = '2'> 기준율</th><th colspan = '2'>현찰</th></tr>");
			html.append("<tr><th>파실때</th><th>사실때</th></tr>");
			
			for(Map.Entry<String,List<String>> m : map.entrySet()) {
				html.append("<tr>");
				html.append("<td>" +m.getKey() + "<br>" + m.getValue().get(0) + "</td>");
				html.append("<td>" + m.getValue().get(1) + "</td>");
				html.append("<td>" + m.getValue().get(2) + "</td>");
				html.append("<td>" + m.getValue().get(3) + "</td>");
				html.append("</tr>");
			}
			html.append("</table>");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return html.toString();
	}
	
	@RequestMapping(value="graph",produces="text/html; charset=utf-8")
	public String graph() {
		Map<String,Object> map = service.graph1();
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Entry<String, Object> m : map.entrySet()) {
			json.append("{\"name\":");
			json.append("\"" + m.getKey() + "\"");
			json.append(",\"cnt\":");
			json.append("\"" + m.getValue() + "\"}");
			i++;
			if(i < map.size()) {
				json.append(",");
			}
		}
		json.append("]");
		return json.toString();
		
	}
	@RequestMapping(value="graph2",produces="text/html; charset=utf-8")
	public String graph2() {
		Map<String,Object> map = service.graph2();
		System.out.println(map);
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Entry<String, Object> m : map.entrySet()) {
			json.append("{\"regdate\":");
			json.append("\"" + m.getKey() + "\"");
			json.append(",\"cnt\":");
			json.append("\"" + m.getValue() + "\"}");
			i++;
			if(i < map.size()) {
				json.append(",");
			}
		}
		json.append("]");
		return json.toString();
	}
}
