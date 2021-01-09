package chap1;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main1 {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		Greeter g = ctx.getBean("greeter", Greeter.class);
		System.out.println(g.greet("스프링"));
		Message m = ctx.getBean("message",Message.class);
		m.sayHello("홍길동");
		Greeter g2 = ctx.getBean("greeter", Greeter.class);
		if(g == g2) {
			System.out.println("g object == g2 object");
		}
	}
}
