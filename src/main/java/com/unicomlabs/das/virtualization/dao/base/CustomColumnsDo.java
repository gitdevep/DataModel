package com.unicomlabs.das.virtualization.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.unicomlabs.das.virtualization.utils.CustomColumns;
import com.unicomlabs.das.virtualization.utils.CustomDataLog;

@Mapper
public interface CustomColumnsDo {
	/**
	 * 查询用户全部一级自定义列信息列表（即所有用户表编码信息列表）
	 * @return type : List 返回查询操作所有符合条件的记录的VO对象集合，操作失败返回null
	 * @throws Exception
	 */
	List<CustomColumns> selectLevelOneCustomColumnListByUser(String operId);

	/**
	 * 查询组织机构全部一级自定义列信息列表（即组织机构所有表编码信息列表）
	 * @return type : List 返回查询操作所有符合条件的记录的VO对象集合，操作失败返回null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")

	public void insertCustomDataLog(@SuppressWarnings("rawtypes") Map params);	
	public void updateDataDimension(@SuppressWarnings("rawtypes") Map params);	
	public void updateDelete(@SuppressWarnings("rawtypes") Map params);	
	public int selectCustomSqlByName(String name);	
	public List<CustomDataLog> selectSqlContent(int id);	
	public int selectCustomSqlCount(@SuppressWarnings("rawtypes") Map params);
	public List<CustomDataLog> selectCustomSql(@SuppressWarnings("rawtypes") Map params);
	public List<Map<String, Object>> selectCustomSqlDBName(Long userId);
}
