package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.BoardMapper;
import logic.Board;
@Repository // @Component + 영속객체(데이터베이스와 직접 연결해서 가져오는 단계, Model 담당 객체)
public class BoardDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();

	public int count(String searchtype, String searchcontent) {
		param.clear();
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		return template.getMapper(BoardMapper.class).count(param);
	}

	public List<Board> list(Integer pageNum, int limit, String searchtype, String searchcontent) {
		if(searchtype != null && searchcontent != null) {
			param.clear();
			param.put("searchtype", searchtype);
			param.put("searchcontent", searchcontent);
		}
		param.put("startrow", (pageNum-1) * limit);
		param.put("limit", limit);
		return template.getMapper(BoardMapper.class).select(param);
	}

	public Board selectOne(Integer num) {
		param.clear();
		param.put("num", num);
		return template.getMapper(BoardMapper.class).select(param).get(0);
	}

	public void readcntAdd(Integer num) {
		param.clear();
		param.put("num", num);
		template.getMapper(BoardMapper.class).updateReadcntAdd(param);
	}

	public int insert(Board board) {
		final int success = 1;
		final int fail = 0;
		param.clear();
		try{
			template.getMapper(BoardMapper.class).insert(board);
		}catch(Exception e) {
			System.out.println("error occured while delete -> check information : " + board);
			e.printStackTrace();
			return fail;
		}
		return success;
	}

	public int maxNum() {
		param.clear();
		return template.getMapper(BoardMapper.class).maxNum();
	}

	public int maxGrp() {
		param.clear();
		return template.getMapper(BoardMapper.class).maxGrp();
	}

	public boolean delete(Board board) {
		param.clear();
		param.put("num", board.getNum());
		try {
			template.getMapper(BoardMapper.class).delete(param);
		}catch(IndexOutOfBoundsException e) {
			System.out.println("error occured while delete -> check information : " + board.getNum());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int update(Board board) {
		return template.getMapper(BoardMapper.class).update(board);
	}

	public int maxGrpstep(Board board) {
		param.clear();
		return template.getMapper(BoardMapper.class).maxGrpStep(board);
	}

	public List<Map<String,Object>> graph1() {
		return template.getMapper(BoardMapper.class).graph1();
	}

	public List<Map<String,Object>> graph2() {
		return template.getMapper(BoardMapper.class).graph2();
	}


}
