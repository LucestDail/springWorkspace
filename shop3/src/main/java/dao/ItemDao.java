package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.Item;

@Repository // @Component + 영속객체(데이터베이스와 직접 연결해서 가져오는 단계, Model 담당 객체)
public class ItemDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<Item> mapper = new BeanPropertyRowMapper<Item>(Item.class);
	private Map<String,Object> param = new HashMap<>();
	
	@Autowired
	//Datasource 형태에 대해서 객체 주입해라
	public void setDataSource(DataSource dataSource) { //dataSource: db connection 객체
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Item> list() {
		// TODO Auto-generated method stub
		return template.query("select * from item", mapper);
	}

	public Item selectOne(Integer id) {
		// TODO Auto-generated method stub
		param.clear();
		param.put("id", id);
		return template.queryForObject("select * from item where id=:id",param,mapper);
	}

	public void insert(Item item) {
		// TODO Auto-generated method stub
		param.clear();
		String sqlForId = "select ifnull(max(id),0) from item";
		int id = template.queryForObject(sqlForId, param, Integer.class);
		item.setId(String.valueOf(++id));
		String sqlForInsert = "insert into item (id, name, price, description, pictureUrl) values (:id, :name, :price, :description, :pictureUrl)";
		SqlParameterSource prop = new BeanPropertySqlParameterSource(item);
		template.update(sqlForInsert, prop);
	}
}