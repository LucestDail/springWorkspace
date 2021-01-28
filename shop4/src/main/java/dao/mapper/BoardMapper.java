package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.Board;

public interface BoardMapper {
	final String select = " select num, name, pass, subject, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep from board ";
	@Select({"<script>",
		"select count(*) from board",
		"<if test='searchtype != null and searchcontent != null'> where ${searchtype} like '%${searchcontent}%' </if>",
		"</script>"})
	int count(Map<String, Object> param);

	@Select({"<script>",
		select,
		"<if test='searchtype != null and searchcontent != null'> where ${searchtype} like '%${searchcontent}%' </if>",
		"<if test='num != null'> where num = #{num} </if>",
		"<if test='limit != null'> order by grp desc, grpstep asc limit ${startrow},#{limit} </if>",
		"</script>"})
	List<Board> select(Map<String, Object> param);
	
	@Update("update board set readcnt = (readcnt+1) where num=#{num}")
	void updateReadcntAdd(Map<String, Object> param);

	@Insert("insert into board (num, name, pass, subject, content, file1, regdate, readcnt, grp, grplevel, grpstep) values (#{num}, #{name}, #{pass}, #{subject}, #{content}, #{fileurl}, now(), 0, #{grp}, #{grplevel}, #{grpstep})")
	void insert(Board board);

	@Select("select ifnull(max(num),0) from board")
	int maxNum();

	@Select("select ifnull(max(grp),0) from board")
	int maxGrp();

	@Delete("delete from board where num = #{num}")
	void delete(Map<String, Object> param);

	@Update("update board set file1=#{fileurl}, name=#{name}, subject=#{subject}, content=#{content} where num=#{num}")
	int update(Board board);

	@Select("SELECT ifnull(max(grpstep),0) FROM board WHERE grp=#{grp} AND grplevel=#{grplevel}")
	int maxGrpStep(Board board);

	@Select("SELECT name, count(name) cnt from board group by NAME ORDER BY cnt DESC LIMIT 0,5")
	List<Map<String, Object>> graph1();

	@Select("SELECT date_format(regdate, '%Y-%m-%d') regdate, count(regdate) cnt from board group by date_format(regdate, '%Y%m%d') ORDER BY cnt DESC")
	List<Map<String, Object>> graph2();
	
}
