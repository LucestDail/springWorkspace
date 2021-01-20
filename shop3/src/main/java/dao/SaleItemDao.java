package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Cart;
import logic.SaleItem;

@Repository
public class SaleItemDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<SaleItem> mapper = new BeanPropertyRowMapper<SaleItem>(SaleItem.class);
	private Map<String,Object> param = new HashMap<>();
	
	@Autowired
	//Datasource 형태에 대해서 객체 주입해라
	public void setDataSource(DataSource dataSource) { //dataSource: db connection 객체
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public void insert(SaleItem saleItem) {
		// TODO Auto-generated method stub
		String sqlForInsert = "insert into saleitem (saleid, seq, itemid, quantity) values(:saleid, :seq, :itemid, :quantity)";
		SqlParameterSource prop = new BeanPropertySqlParameterSource(saleItem);
		template.update(sqlForInsert, prop);
	}

	public List<SaleItem> selectAll(int saleid) {
		// TODO Auto-generated method stub
		List<SaleItem> list = null;
		try {
			param.clear();
			param.put("saleid", saleid);
			list = template.query("select * from saleitem where saleid=:saleid", param,mapper);
		}catch(DataAccessException e) {
			e.printStackTrace();
			list = new ArrayList<SaleItem>();
		}
		return list;
	}
}
