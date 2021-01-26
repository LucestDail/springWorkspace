package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.ItemMapper;
import logic.Item;

@Repository // @Component + 영속객체(데이터베이스와 직접 연결해서 가져오는 단계, Model 담당 객체)
public class ItemDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();

	public List<Item> list() {
		return template.getMapper(ItemMapper.class).select(null);
	}

	public Item selectOne(Integer id) {
		param.clear();
		param.put("id", id);
		return template.getMapper(ItemMapper.class).select(param).get(0);
	}

	public void insert(Item item) {
		param.clear();
		int id = template.getMapper(ItemMapper.class).maxid();
		item.setId(String.valueOf(++id));
		template.getMapper(ItemMapper.class).insert(item);
	}

	public void update(Item item) {
		param.clear();
		template.getMapper(ItemMapper.class).update(item);
	}

	public void delete(Integer id) {
		param.clear();
		param.put("id", id);
		template.getMapper(ItemMapper.class).delete(param);
	}
}