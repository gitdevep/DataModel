package com.unicomlabs.das.virtualization.dao.content;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.unicomlabs.das.virtualization.utils.CustomColumns;
import com.unicomlabs.das.virtualization.utils.CustomSql;

@Mapper
public interface CustomSqlDo {
	/**
	 * 根据自定义SQL查询</br>
	 * @return list 
	 * 			
	 * @throws Exception
	 */
	List<Map<String, Object>> selectByCustomSql(CustomSql params);
	int selectByCustomSqlCount(CustomSql params);
	List<Map<String, Object>> selectItemList(@SuppressWarnings("rawtypes") Map params);
	int selectItemListCount(String sqlStr);
	/**
	 * 根据列编码递归查询子节点字段类型的自定义列信息列表
	 * @return typselectChildrenColumnListByColumnCodee : List 返回查询操作所有符合条件的记录的VO对象集合，操作失败返回null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CustomColumns> selectChildrenColumnListByColumnCode(@SuppressWarnings("rawtypes") Map params);
	public List<Map<String, Object>> selectLevelOneCustomColumnListByOrg(String orgCode);
}
