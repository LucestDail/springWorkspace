package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import xml.MemberService;

@Configuration
@ComponentScan(basePackages = {"annotation","xml"})
@EnableAspectJAutoProxy
public class AppCtx {
	
//	@Bean
//	public MemberService memberService2() {
//		return new MemberService();
//	}

}
