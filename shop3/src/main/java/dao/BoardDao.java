package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Board;
@Repository // @Component + 영속객체(데이터베이스와 직접 연결해서 가져오는 단계, Model 담당 객체)
public class BoardDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<Board> mapper = new BeanPropertyRowMapper<Board>(Board.class);
	private Map<String,Object> param = new HashMap<>();
	private String select = "select num, name, pass, subject, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep from board";
	@Autowired
	//Datasource 형태에 대해서 객체 주입해라
	public void setDataSource(DataSource dataSource) { //dataSource: db connection 객체
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public int count(String searchtype, String searchcontent) {
		String sql = " select count(*) from board ";
		if(searchtype != null && searchcontent != null) {
			sql += " where " + searchtype + " like :searchcontent ";
			param.clear();
			param.put("searchcontent", "%"+searchcontent+"%");
		}
		return template.queryForObject(sql, param, Integer.class);
	}

	public List<Board> list(Integer pageNum, int limit, String searchtype, String searchcontent) {
		String sql = select;
		if(searchtype != null && searchcontent != null) {
			sql += " where " + searchtype + " like :searchcontent ";
			param.clear();
			param.put("searchcontent", "%"+searchcontent+"%");
		}
		param.put("startrow", (pageNum-1) * limit);
		param.put("limit", limit);
		sql += " order by grp desc, grpstep asc limit :startrow, :limit";
		return template.query(sql,param,mapper);
	}

	public Board selectOne(Integer num) {
		param.clear();
		param.put("num", num);
		param.put("select", select);
		String sql = select + " where num=:num";
		return template.queryForObject(sql, param,mapper);
	}

	public void readcntAdd(Integer num) {
		param.clear();
		param.put("num", num);
		String sql = "update board set readcnt=readcnt+1 where num=:num";
		template.query(sql,param,mapper);
	}

	public int insert(Board board) {
		param.clear();
		String sql = "insert into board (num, name, pass, subject, content, file1, regdate, readcnt, grp, grplevel, grpstep) values (:num, :name, :pass, :subject, :content, :fileurl, now(), 0, :grp, :grplevel, :grpstep)";
		SqlParameterSource prop = new BeanPropertySqlParameterSource(board);
		return template.update(sql,prop);
		
	}

	public int maxNum() {
		param.clear();
		String sql = "select ifnull(max(num),0) from board";
		return template.queryForObject(sql, param, Integer.class);
	}

	public int maxGrp() {
		param.clear();
		String sql = "select ifnull(max(grp),0) from board";
		return template.queryForObject(sql, param, Integer.class);
	}


}
