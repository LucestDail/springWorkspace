package spring;

public class WriteImpl {
	private ArticleDao dao;
	public WriteImpl(ArticleDao dao) {
		this.dao = dao;
	}
	public void write() {
		System.out.println("WriteImpl.write 메소드 호출");
		dao.insert();
	}
}
