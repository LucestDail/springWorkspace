package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Sale;

@Repository
public class SaleDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<Sale> mapper = new BeanPropertyRowMapper<Sale>(Sale.class);
	private Map<String,Object> param = new HashMap<>();
	
	@Autowired
	//Datasource 형태에 대해서 객체 주입해라
	public void setDataSource(DataSource dataSource) { //dataSource: db connection 객체
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public Sale selectOne(Integer id) {
		// TODO Auto-generated method stub
		param.clear();
		param.put("saleid", id);
		return template.queryForObject("select * from sale where saleid=:saleid",param,mapper);
	}
	
	public int maxNumId() {
		param.clear();
		String sql = "select ifnull(max(saleid),0) from sale";
		return template.queryForObject(sql, param, Integer.class);
	}

	public void insert(Sale sale) {
		// TODO Auto-generated method stub
		String sqlForInsert = "insert into sale (saleid, userid, saledate) values (:saleid,:userid,now())";
		SqlParameterSource prop = new BeanPropertySqlParameterSource(sale);
		template.update(sqlForInsert, prop);
	}

	public List<Sale> selectAll(String id) {
		// TODO Auto-generated method stub
		List<Sale> list = null;
		try {
			param.clear();
			param.put("userid", id);
			list = template.query("select * from sale where userid=:userid",param, mapper);
		}catch(DataAccessException e) {
			e.printStackTrace();
			list = new ArrayList<Sale>();
		}
		return list;
	}

}
