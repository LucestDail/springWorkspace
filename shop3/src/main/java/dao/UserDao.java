package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.User;
@Repository
public class UserDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
	@Autowired
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

	public void update(User user) {
		// TODO Auto-generated method stub
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "update useraccount set username=:username, birthday=:birthday, phoneno=:phoneno, postcode=:postcode, address=:address, email=:email where userid=:userid";
		template.update(sql, param);
	}

	public void delete(String userid) {
		// TODO Auto-generated method stub
		Map<String, String> param = new HashMap<>();
		param.put("userid", userid);
		String sql = "delete from useraccount where userid =:userid";
		try {
			template.query(sql, param, mapper);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + userid);
			e.printStackTrace();
		}
	}

	public User selectByEmailTel(String email, String tel) {
		Map<String, String> param = new HashMap<>();
		param.put("email",email);
		param.put("phoneno", tel);
		String sql = "select * from useraccount where email=:email and phoneno=:phoneno";
		User user = null;
		try {
			user = template.queryForObject(sql, param, mapper);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + param);
//			e.printStackTrace();
		}
		return user;
	}

	public User selectByIdEmailTel(String id, String email, String tel) {
		Map<String, String> param = new HashMap<>();
		param.put("email",email);
		param.put("phoneno", tel);
		param.put("userid", id);
		String sql = "select * from useraccount where email=:email and phoneno=:phoneno and userid=:userid";
		User user = null;
		try {
			user = template.queryForObject(sql, param, mapper);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + param);
//			e.printStackTrace();
		}
		return user;
	}

	public void chgPass(String sessionUserid, String chgpass) {
		// TODO Auto-generated method stub
		Map<String, String> param = new HashMap<>();
		param.put("userid",sessionUserid);
		param.put("password", chgpass);
		String sql = "update useraccount set password=:password where userid=:userid";
		try {
			template.query(sql, param, mapper);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + sessionUserid);
//			e.printStackTrace();
		}
	}
}
