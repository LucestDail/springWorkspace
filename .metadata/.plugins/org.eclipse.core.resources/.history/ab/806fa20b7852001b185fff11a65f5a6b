package osh;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		System.out.println("[MAIN] start");
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		WriteArticleService articleService = ctx.getBean("writeArticleService", WriteArticleService.class);
		articleService.write(new Article());
		System.out.println("[MAIN] end");
		
	}
}
