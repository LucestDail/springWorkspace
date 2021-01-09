package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import logic.User;

public class UserDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<User> list(){
		return template.query("select * from useraccount", mapper);
	}
	
	public User selectOne(String userid) {
		Map<String, String> param = new HashMap<>();
		param.put("userid", userid);
		String sql = "select * from useraccount where userid =:userid";
		User user = null;
		try {
			user = template.queryForObject(sql, param, mapper);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		return user;
	}

	public void insert(User user) {
		// TODO Auto-generated method stub
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into useraccount (userid, username, password, birthday, phoneno, postcode, address, email) values (:userid, :username, :password, :birthday, :phoneno, :postcode, :address, :email)";
		template.update(sql, param);
	}
}
