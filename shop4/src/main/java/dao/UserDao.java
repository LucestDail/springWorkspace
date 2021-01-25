package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import dao.mapper.UserMapper;
import logic.User;
@Repository
public class UserDao {
	@Autowired
	private SqlSessionTemplate template;
	Map<String, String> param = new HashMap<>();
	
	
	public List<User> list(){
		param.clear();
		return template.getMapper(UserMapper.class).select(null);
	}
	
	public User selectOne(String userid) {
		param.clear();
		param.put("userid", userid);
		User user = null;
		try {
			user = template.getMapper(UserMapper.class).select(param).get(0);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while selecrt -> check information : " + userid);
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public void insert(User user) {
		param.clear();
		template.getMapper(UserMapper.class).insert(user);
	}

	public void update(User user) {
		param.clear();
		template.getMapper(UserMapper.class).update(user);
	}

	public void delete(String userid) {
		param.clear();
		param.put("userid", userid);
		try {
			template.getMapper(UserMapper.class).delete(param);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + userid);
			e.printStackTrace();
		}
	}

	public User selectByEmailTel(String email, String tel) {
		param.put("email",email);
		param.put("phoneno", tel);
		User user = null;
		try {
			user = template.getMapper(UserMapper.class).select(param).get(0);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + param);
			e.printStackTrace();
		}
		return user;
	}

	public User selectByIdEmailTel(String id, String email, String tel) {
		param.put("email",email);
		param.put("phoneno", tel);
		param.put("userid", id);
		User user = null;
		try {
			user = template.getMapper(UserMapper.class).select(param).get(0);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + param);
			e.printStackTrace();
		}
		return user;
	}

	public void chgPass(String sessionUserid, String chgpass) {
		param.put("userid",sessionUserid);
		param.put("password", chgpass);
		User user = new User();
		user.setPassword(chgpass);
		user.setUserid(sessionUserid);
		try {
			template.getMapper(UserMapper.class).update(user);
		}catch(EmptyResultDataAccessException e) {
			System.out.println("error occured while delete -> check information : " + sessionUserid);
			e.printStackTrace();
		}
	}
}
