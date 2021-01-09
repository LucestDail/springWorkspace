package osh2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WriteArticleServiceImpl implements WriteArticleService{
	@Autowired
	private Article article;
	
	public WriteArticleServiceImpl(ArticleDao articleDao) {
		System.out.println("[WASI2] WriteArticleServiceImpl()");
	}

	@Override
	public void write(Article article) {
		// TODO Auto-generated method stub
		System.out.println("[WASI2] write()");
		
	}

}
