package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import logic.Item;

public class ItemDao {
	private NamedParameterJdbcTemplate template;
	//Item 클래스의 프로퍼티와 db 컬럼명을 맵핑할 것
	private RowMapper<Item> mapper = new BeanPropertyRowMapper<Item>(Item.class);
	//dataSource : db 접속 객체(dao) -> spring-db.xml 에서 config 댐
	public void setDataSource(DataSource dataSource) {
		//spring 자체 jdbc 프레임워크
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Item> list(){
		return template.query("select * from item", mapper);
	}
	
	public Item selectOne(Integer id) {
		Map<String, Integer> param = new HashMap<>();
		param.put("id", id);
		return template.queryForObject("select * from item where id=:id", param, mapper);
	}
}
