package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import dao.mapper.SaleMapper;
import logic.Sale;

@Repository
public class SaleDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();

	public Sale selectOne(Integer id) {
		param.clear();
		param.put("saleid", id);
		return template.getMapper(SaleMapper.class).select(param).get(0);
	}

	public int maxNumId() {
		param.clear();
		return template.getMapper(SaleMapper.class).maxNumId();
	}

	public void insert(Sale sale) {
		param.clear();
		template.getMapper(SaleMapper.class).insert(sale);
	}

	public List<Sale> selectAll(String id) {
		List<Sale> list = null;
		try {
			param.clear();
			param.put("userid", id);
			list = template.getMapper(SaleMapper.class).select(param);
		} catch (DataAccessException e) {
			e.printStackTrace();
			list = new ArrayList<Sale>();
		}
		return list;
	}

}
