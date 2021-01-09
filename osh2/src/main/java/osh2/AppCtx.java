package osh2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"osh2"})
public class AppCtx {
	@Bean
	public WriteArticleServiceImpl writeArticleService(){
		return new WriteArticleServiceImpl(articleDao());
	}
	
	@Bean
	public MariadbArticleDao articleDao() {
		return new MariadbArticleDao();
	}
}
