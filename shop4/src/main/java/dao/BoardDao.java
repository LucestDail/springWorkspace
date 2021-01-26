package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dao.mapper.BoardMapper;
import logic.Board;
@Repository // @Component + 영속객체(데이터베이스와 직접 연결해서 가져오는 단계, Model 담당 객체)
public class BoardDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private String select = "select num, name, pass, subject, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep from board";
	
	public int count(String searchtype, String searchcontent) {
		param.clear();
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
//		String sql = " select count(*) from board ";
//		if(searchtype != null && searchcontent != null) {
//			sql += " where " + searchtype + " like :searchcontent ";
//			param.clear();
//			param.put("searchcontent", "%"+searchcontent+"%");
//		}
//		return template.queryForObject(sql, param, Integer.class);
		return template.getMapper(BoardMapper.class).count(param);
	}

	public List<Board> list(Integer pageNum, int limit, String searchtype, String searchcontent) {
		String sql = select;
		if(searchtype != null && searchcontent != null) {
//			sql += " where " + searchtype + " like :searchcontent ";
			param.clear();
			param.put("searchtype", searchtype);
			param.put("searchcontent", searchcontent);
		}
		param.put("startrow", (pageNum-1) * limit);
		param.put("limit", limit);
//		sql += " order by grp desc, grpstep asc limit :startrow, :limit";
//		return template.query(sql,param,mapper);
		return template.getMapper(BoardMapper.class).select(param);
	}

	public Board selectOne(Integer num) {
		param.clear();
		param.put("num", num);
//		param.put("select", select);
//		String sql = select + " where num=:num";
//		return template.queryForObject(sql, param,mapper);
		return template.getMapper(BoardMapper.class).select(param).get(0);
	}

	public void readcntAdd(Integer num) {
		param.clear();
		param.put("num", num);
//		String sql = "update board set readcnt=readcnt+1 where num=:num";
//		template.query(sql,param,mapper);
		template.getMapper(BoardMapper.class).updateReadcntAdd(param);
	}

	public int insert(Board board) {
		final int success = 1;
		final int fail = 0;
		param.clear();
//		String sql = "insert into board (num, name, pass, subject, content, file1, regdate, readcnt, grp, grplevel, grpstep) values (:num, :name, :pass, :subject, :content, :fileurl, now(), 0, :grp, :grplevel, :grpstep)";
//		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
//		return template.update(sql,prop);
//		return template.getMapper(BoardMapper.class).insert(board);
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
//		String sql = "select ifnull(max(num),0) from board";
//		return template.queryForObject(sql, param, Integer.class);
		return template.getMapper(BoardMapper.class).maxNum();
	}

	public int maxGrp() {
		param.clear();
//		String sql = "select ifnull(max(grp),0) from board";
//		return template.queryForObject(sql, param, Integer.class);
		return template.getMapper(BoardMapper.class).maxGrp();
	}

	public boolean delete(Board board) {
		param.clear();
		param.put("num", board.getNum());
//		String sql = "delete from board where num=:num";
//		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
//		return template.update(sql, prop)>0;
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
//		String sql = "update board set file1=:fileurl, name=:name, subject=:subject, content=:content where num=:num";
//		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
//		return template.update(sql, prop);
		return template.getMapper(BoardMapper.class).update(board);
	}

	public int maxGrpstep(Board board) {
		param.clear();
//		String sql = "SELECT ifnull(max(grpstep),0) FROM board WHERE grp=:grp AND grplevel=:grplevel";
//		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
//		return template.update(sql, prop);
		return template.getMapper(BoardMapper.class).maxGrpStep(board);
	}


}
