package osh;

import org.springframework.stereotype.Component;

@Component
public class OracleArticleDao implements ArticleDao {

	public void insert(String str) {
		System.out.println(str);
	}

	public void update(String str) {
		System.out.println(str);
	}


	public void delete(String str) {
		System.out.println(str);
	}
	
	public String toString() {
		return "OracleArticleDao ";
	}
}