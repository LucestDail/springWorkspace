package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xml.Article;
import xml.Member;
import xml.UpdateInfo;
import xml.MemberService;
import xml.ReadArticleService;

public class Main2 {
	public static void main(String[] args) {
		String[] config = {"di.xml","aop2.xml"};
		//di 에는 readArticleServiceImpl,
		//aop2 에는 loggingAdvice, cacheAdvice 가 있음
		ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
		//메인 구동하자마자, 우선 aop:config 에 의해서 order 순으로 진행
		ReadArticleService service = ctx.getBean("readArticleService",ReadArticleService.class);
		// di.xml 에 의한 설정을 가져와 ReadArticleService 구현 빈에 주입 실시
		System.out.println("[main] total start");
		try {
			// ArticleCacheAdvice.cache() -> joinPoint.proceed() -> LogginAdvice.before()
			Article a1 = service.getArticleAndReadCnt(1); // getArticleAndReadCnt(1) 파라매터 대입
			// aop aspect 에 의해서 판단, 정상처리이므로, after-returning 실행 -> LoggingAdvice.afterReturning()
			// aop aspect 에 의한 after 처리 -> LoggingAdvice.afterFinally()
			// aop order 2 번 cache 로 수행함
			//왜냐? order2(order3) 순이므로 3번이 마무리 되어서 2번으로 마저 돌아감...
			// ArticleCacheAdvice.cache.put() 수행
			System.out.println("[main]a1=" + a1); // 출력...
			// ArticleCacheAdvice.cache() 에서 cache.get(id) 값인 article 값이 null 이 아니므로 return article
			Article a2 = service.getArticleAndReadCnt(1);
			System.out.println("[main]a1=a2 : "+ (a1==a2));// 결과 a1 에서 생성된 article bean 과 a2 bean 은 복사한 bean 임
			// ArticleCacheAdvice.cache() -> joinPoint.proceed() -> LogginAdvice.before()
			service.getArticleAndReadCnt(0); // getArticleAndReadCnt(0) 파라매터 대입
			// aop aspect 에 의해서 판단, 오류처리이므로, after-throwing 실행 -> LoggingAdvice.afterThrowing()
			// aop aspect 에 의한 after 처리 -> LoggingAdvice.afterFinally()
			// 단 예외 처리이므로 aop 2에서는 즉시 처리 종료... 다시 오더2번으로 안넘어감...
		}catch(Exception e) {
			System.out.println("[main]" + e.getMessage());
			// 상기 에러에 의해서 에러 발생된 메시지 뿌려줌... catch 구문 활성화로 즉시 종료, try 문 종료
		}
		System.out.println("[main] UpdateMemberInfoTraceAspect");
		MemberService ms = ctx.getBean("memberService",MemberService.class);
		ms.regist(new Member());
		ms.update("hong",new UpdateInfo());
		ms.delete("hong2","test");
		System.out.println("[main] total end");
	}
}
