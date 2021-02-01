package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.User;

public interface UserMapper {
	
	@Select({"<script>",
		" select * from usersecurity ",
		"<trim prefix = 'where' prefixOverrides = 'AND || OR'>",
		"<if test='userid != null'>",
			" and userid=#{userid} ",
		"</if>",
		"<if test='email != null'>",
			" and email=#{email} ",
		"</if>",
		"<if test='phoneno != null'>",
			" and phoneno=#{phoneno} ",
		"</if>",
		"</trim>",
		"</script>"})
	List<User> select(Object object);
	
	@Insert("insert into usersecurity (userid, username, password, birthday, phoneno, postcode, address, email) values (#{userid}, #{username}, #{password}, #{birthday}, #{phoneno}, #{postcode}, #{address}, #{email})")
	void insert(User user);

	@Update({"<script>",
			" update usersecurity ",
			"<trim prefix = 'set' prefixOverrides = ','>",
			"<if test = 'username != null'> ",
				", username = #{username}",
			"</if>",
			"<if test = 'birthday != null'> ",
				", birthday = #{birthday}",
			"</if>",
			"<if test = 'phoneno != null'> ",
				", phoneno = #{phoneno}",
			"</if>",
			"<if test = 'postcode != null'> ",
				", postcode = #{postcode}",
			"</if>",
			"<if test = 'address != null'> ",
				", address = #{address}",
			"</if>",
			"<if test = 'email != null'> ",
				", email = #{email}",
			"</if>",
			"<if test = 'password != null'> ",
				", password = #{password}",
			"</if>",
			"</trim>",
			" where userid=#{userid} ",
			"</script>"})
	void update(User user);
	
	@Update("update usersecurity set password = #{password} where userid = #{userid}")
	void updatePass(User user);

	@Delete("delete from usersecurity where userid=#{userid}")
	void delete(Map<String, String> param);
}
