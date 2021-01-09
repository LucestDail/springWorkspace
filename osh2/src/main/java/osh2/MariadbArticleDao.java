package osh2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MariadbArticleDao implements ArticleDao{
	
	@Autowired
	private Article article;
	
	public MariadbArticleDao() {
		System.out.println("[MAD] MariadbArticleDao()");
		insert();
	}
	
	@Override
	public void insert() {
		// TODO Auto-generated method stub
		System.out.println("[MAD] insert()");
		System.out.println("mariadb에 저장합니다");
	}
}
