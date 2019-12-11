package com.unicomlabs.das.virtualization.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unicomlabs.das.virtualization.dao.base.CustomColumnsDo;
import com.unicomlabs.das.virtualization.dao.content.CustomSqlDo;
import com.unicomlabs.das.virtualization.utils.CustomColumns;
import com.unicomlabs.das.virtualization.utils.CustomDataLog;
import com.unicomlabs.das.virtualization.utils.CustomSql;
import com.unicomlabs.das.virtualization.service.DataAnalysisService;

import com.unicomlabs.das.virtualization.utils.Page;
/**
 * 数据分析服务实现
 * @author zhaich5
 * @since 2018/1/9
 *
 */
@Service
public class DataAnalysisBo implements DataAnalysisService {

	@Autowired
	private CustomColumnsDo customColumnsDo;
	
	@Autowired
	private CustomSqlDo customSqlDo;
	
	@Override
	public List<CustomColumns> queryUserCustomTableList(String operId) 
			throws Exception {
		return customColumnsDo.selectLevelOneCustomColumnListByUser(operId);
	}

	@Override
	public List<Map<String, Object>> queryOrgCustomTableList(String orgCode) 
			throws Exception {
		return customSqlDo.selectLevelOneCustomColumnListByOrg(orgCode);
	}
	
	@Override
	public List<CustomColumns> queryTableColumnList(@SuppressWarnings("rawtypes") Map params) 
			throws Exception {
		return customSqlDo.selectChildrenColumnListByColumnCode(params);
	}

	@Override
	public List<Map<String, Object>> queryCustomSql(CustomSql sql) 
			throws Exception {
		return customSqlDo.selectByCustomSql(sql);
	}
	@Override
	public int queryCustomSqlCount(CustomSql sql) 
			throws Exception {
		return customSqlDo.selectByCustomSqlCount(sql);
	}
	@Override
	public List<Map<String, Object>> selectItemList(@SuppressWarnings("rawtypes") Map params) 
			throws Exception {
		return customSqlDo.selectItemList(params);
	}
	@Override
	public int selectItemListCount(String sqlStr) 
			throws Exception {
		return customSqlDo.selectItemListCount(sqlStr);
	}
	@Override
	public void insertCustomDataLog(@SuppressWarnings("rawtypes") Map params) throws Exception{
		customColumnsDo.insertCustomDataLog(params);
	}
	@Override
	public void updateDataDimension(@SuppressWarnings("rawtypes") Map params) throws Exception{
		customColumnsDo.updateDataDimension(params);
	}
	@Override
	public void updateDelete(@SuppressWarnings("rawtypes") Map params) throws Exception{
		customColumnsDo.updateDelete(params);
	}
	@Override
	public int selectCustomSqlByName(String name) throws Exception{
		return customColumnsDo.selectCustomSqlByName(name);
	}
	@Override
	public int selectCustomSqlCount(@SuppressWarnings("rawtypes") Map params)throws Exception{
		return customColumnsDo.selectCustomSqlCount(params);
	}
	@Override
	public List<CustomDataLog> selectCustomSql(@SuppressWarnings("rawtypes") Map params)throws Exception{
		return customColumnsDo.selectCustomSql(params);
	}
	@Override
	public List<CustomDataLog> selectSqlContent(int id)throws Exception{
		return customColumnsDo.selectSqlContent(id);
	}
	@Override
	public List<Map<String, Object>> selectCustomSqlDBName(Long userId)throws Exception{
		return customColumnsDo.selectCustomSqlDBName(userId);
	}
}
