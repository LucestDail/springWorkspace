package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xml.Article;
import xml.Member;
import xml.UpdateInfo;
import annotation.MemberService;
import xml.ReadArticleService;

public class Main3 {
	public static void main(String[] args) {
		String[] config = {"annotation.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
		//service : annotation.ReadArticleServiceImple 객체이기 때문에 xml 
		ReadArticleService service = ctx.getBean("readArticleService",ReadArticleService.class);
		System.out.println("[main] total start");
		try {
			Article a1 = service.getArticleAndReadCnt(1);
			Article a2 = service.getArticleAndReadCnt(1);
			System.out.println("[main]a1=a2 : "+ (a1==a2));
			service.getArticleAndReadCnt(0);
		}catch(Exception e) {
			System.out.println("[main]" + e.getMessage());
		}
		System.out.println("[main] UpdateMemberInfoTraceAspect");
		MemberService ms = ctx.getBean("memberService",MemberService.class);
		ms.regist(new Member());
		ms.update("hong",new UpdateInfo());
		ms.delete("hong2","test");
		System.out.println("[main] total end");
	}
}
