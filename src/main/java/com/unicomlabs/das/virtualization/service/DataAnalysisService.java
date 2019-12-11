package com.unicomlabs.das.virtualization.service;

import java.util.List;
import java.util.Map;

import com.unicomlabs.das.virtualization.utils.Page;
import com.unicomlabs.das.virtualization.utils.CustomColumns;
import com.unicomlabs.das.virtualization.utils.CustomDataLog;
import com.unicomlabs.das.virtualization.utils.CustomSql;


/**
 * 数据分析服务接口
 * 
 * @author zhaich5
 * @since 2018/6/4
 * @version 1.0
 * 
 */
public interface DataAnalysisService {
	
	/**
	 * 查询用户全部自定义表信息列表
	 * @param operId 用户ID
     * @return type : List 返回查询操作所有符合条件的记录的VO对象集合，操作失败返回null
     */
    public List<CustomColumns> queryUserCustomTableList(String operId) 
    		throws Exception;
    
	/**
	 * 查询组织机构全部自定义表信息列表
	 * @param orgCode 组织机构编码
     * @return type : List 返回查询操作所有符合条件的记录的VO对象集合，操作失败返回null
     */
    public List<Map<String, Object>> queryOrgCustomTableList(String orgCode) 
    		throws Exception;
    
	/**
	 * 查询指定表字段信息列表
     * @return type : List 返回查询操作所有符合条件的记录的VO对象集合，操作失败返回null
     */
    public List<CustomColumns> queryTableColumnList(@SuppressWarnings("rawtypes") Map params) 
    		throws Exception;
    
    /**
     * 查询自定义SQL
     * @param sql
     * 			自定义sql对象
     * @return list
     * 			
     * @throws Exception
     */
    public List<Map<String, Object>> queryCustomSql(CustomSql sql) throws Exception;
    public int queryCustomSqlCount(CustomSql sql) throws Exception;
    public List<Map<String, Object>> selectItemList(@SuppressWarnings("rawtypes") Map params) throws Exception;
    public int selectItemListCount(String sqlStr) throws Exception;
    public List<Map<String, Object>> selectCustomSqlDBName(Long userId)throws Exception;
    public void insertCustomDataLog(@SuppressWarnings("rawtypes") Map params) throws Exception;	
	public void updateDataDimension(@SuppressWarnings("rawtypes") Map params) throws Exception;	
	public void updateDelete(@SuppressWarnings("rawtypes") Map params) throws Exception;	
	public int selectCustomSqlByName(String name) throws Exception;	
	public List<CustomDataLog> selectSqlContent(int id)throws Exception;	
	public int selectCustomSqlCount(@SuppressWarnings("rawtypes") Map params) throws Exception;
	public List<CustomDataLog> selectCustomSql(@SuppressWarnings("rawtypes") Map params) throws Exception;
}
