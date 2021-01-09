package osh2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopMain {
	public static void main(String[] args) {
		System.out.println("[MAIN2] start");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		WriteArticleService articleService = ctx.getBean("writeArticleService", WriteArticleService.class);
		articleService.write(new Article());
		System.out.println("[MAIN2] end");
	}
}