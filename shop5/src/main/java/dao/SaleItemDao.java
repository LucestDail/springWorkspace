package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import dao.mapper.SaleItemMapper;
import logic.SaleItem;

@Repository
public class SaleItemDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();

	public void insert(SaleItem saleItem) {
		param.clear();
		template.getMapper(SaleItemMapper.class).insert(saleItem);
	}

	public List<SaleItem> selectAll(int saleid) {
		List<SaleItem> list = null;
		try {
			param.clear();
			param.put("saleid", saleid);
			list = template.getMapper(SaleItemMapper.class).select(param);
		} catch (DataAccessException e) {
			e.printStackTrace();
			list = new ArrayList<SaleItem>();
		}
		return list;
	}
}
