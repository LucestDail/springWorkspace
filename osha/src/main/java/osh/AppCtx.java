package osh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"osh"})
public class AppCtx {
	
	@Bean
	public Project project() {
		Project p = new Project();
		p.setDir("c://osh");
		p.setArticleDao(OracleArticleDao());
		return p;
	}
	
	@Bean
	public ArticleDao OracleArticleDao() {
		return new OracleArticleDao();
	}
}
