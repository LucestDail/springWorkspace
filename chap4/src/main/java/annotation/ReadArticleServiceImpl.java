package annotation;

import org.springframework.stereotype.Component;

import xml.Article;
import xml.ReadArticleService;

//생성되는 객체의 이름을 readArticleService 로 설정
@Component("readArticleService")
public class ReadArticleServiceImpl implements ReadArticleService{

	public Article getArticleAndReadCnt(int id) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("[RASImpl]getArticleAndReadCnt(" + id + ") 호출");
		if(id == 0) {
			throw new Exception("id 0은 안됨");
		}
		return new Article(id);
	}
}
