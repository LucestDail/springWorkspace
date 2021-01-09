package xml;

public class ReadArticleServiceImpl implements ReadArticleService {

	public Article getArticleAndReadCnt(int id) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("[GAAR]getArticleAndReadCnt(" + id + ") 호출");
		if(id == 0) {
			throw new Exception("0은 안됨");
		}
		return new Article(id);
	}

}
