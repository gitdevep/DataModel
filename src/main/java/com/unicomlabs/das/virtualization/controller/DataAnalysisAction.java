package com.unicomlabs.das.virtualization.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.unicomlabs.das.virtualization.utils.WcsSessionConstant;
import com.unicomlabs.das.virtualization.page.table.PageTableHandler;
import com.unicomlabs.das.virtualization.page.table.PageTableRequest;
import com.unicomlabs.das.virtualization.page.table.PageTableResponse;
import com.unicomlabs.das.virtualization.page.table.PageTableHandler.CountHandler;
import com.unicomlabs.das.virtualization.page.table.PageTableHandler.ListHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.unicomlabs.das.virtualization.utils.Page;
import com.unicomlabs.das.virtualization.utils.TargetColumnExpression;
import com.unicomlabs.das.virtualization.utils.ConditionalExpression;
import com.unicomlabs.das.virtualization.utils.CustomColumns;
import com.unicomlabs.das.virtualization.utils.CustomDataInVo;
import com.unicomlabs.das.virtualization.utils.CustomSql;
import com.unicomlabs.das.virtualization.service.DataAnalysisService;
import com.unicomlabs.das.virtualization.utils.CustomDataLog;
import com.unicomlabs.das.system.model.User;
import com.unicomlabs.das.system.utils.UserUtil;

/**
 * 数据分析Controller
 * 
 * @author zhaich5
 * @since 2018/6/4
 * @version 1.0
 * 
 */
@Api(tags = "数据定制")
@Controller
@RequestMapping("/dataAnaly/dataAnalysis")
public class DataAnalysisAction {
	
	/**
	 * 日志
	 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataAnalysisService dataAnalysisService;
	@Autowired 
	private SqlSessionFactory sqlSessionFactory;
	
	/**
     * 获取用户自定义表信息列表
     * @param request
     * @return
     * @throws BizException
     */
	@RequestMapping("/getCustomDBNameList.do")
	public @ResponseBody List<Map<String, Object>> getCustomDBNameList(HttpServletRequest request) throws Exception {
		LOG.info("开始获取用户自定义表信息列表");
		User user = UserUtil.getCurrentUser();
		long userAccount = user.getId();
		List<Map<String, Object>> list = null;
		try {
			list = dataAnalysisService.selectCustomSqlDBName(userAccount);
		} catch (Exception e) {
			LOG.error("获取用户自定义表信息列表异常", e);
		}
		LOG.info("获取用户自定义表信息列表结束");
		return list;
	}
	@RequestMapping("/getCustomTableList.do")
	public @ResponseBody List<Map<String, Object>> getCustomTableList(
			HttpServletRequest request) throws Exception {
		LOG.info("开始获取用户自定义表信息列表");
		String dbName = request.getParameter("dbName");
		
		List<Map<String, Object>> list = null;
		
		try {
			list = dataAnalysisService.queryOrgCustomTableList(dbName);
		} catch (Exception e) {
			LOG.error("获取用户自定义表信息列表异常", e);
		}
		
		LOG.info("获取用户自定义表信息列表结束");
		return list;
	}
	@RequestMapping("/getUser.do")
	public @ResponseBody String getUser(
			HttpServletRequest request) throws Exception {
		LOG.info("开始获取用户自定义表信息列表");
		User user = UserUtil.getCurrentUser();
		String userAccount = user.getUsername();
		
		
		LOG.info("获取用户自定义表信息列表结束");
		return userAccount;
	}
	
	/**
	 * 获取表的字段信息列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTableColumn.do")
	public @ResponseBody List<CustomColumns> getTableColumn(HttpServletRequest request) 
			throws Exception {
		
		LOG.info("开始获取表的字段信息列表");
		Map<String, Object> map = new HashMap<String, Object>();
		String tableCode = request.getParameter("tableCode");// 表编码
		String dbName = request.getParameter("dbName");
		map.put("dbName", dbName);
		map.put("tableName", tableCode);
		List<CustomColumns> list = null;
			
		try {
			list = dataAnalysisService.queryTableColumnList(map);
		} catch (Exception e) {
			LOG.error("获取表的字段信息列表异常", e);
		}
		
		LOG.info("获取表的字段信息列表结束");
		
		return list;
		
	}
	
    /**
     * 执行定制数据
     * @param request
     * @return map</br>
     * 			map.msg：操作结果信息</br>
     * 			map.dataName：定制数据名称</br>
     * 			map.titleList：定制数据标题列表</br>
     * 			map.bodyList：定制数据数据体列表</br>
     * @throws ParseException 
     * @throws Exception
     */
    @RequestMapping("/executeCustomData.do")
    public @ResponseBody Map<String, Object> executeCustomData(
    		@RequestBody CustomDataInVo inVo, 
    		HttpServletRequest request) 
    		throws Exception {
    	
    	LOG.info("开始执行定制数据");
		//定义返回对象
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> sqlmap = new HashMap<String, Object>();
		int sqlId=0;
		List<Map<String, Object>> bodyList = null;
		int count = 0;
		CustomSql customSql = inVo.getCustomSql();
		User user = UserUtil.getCurrentUser();
		String userAccount = user.getUsername();
		try {
			int currentPage = inVo.getCurrentPage();
			int pageSize = inVo.getPageSize();
			customSql.setLimitSize(pageSize);
			customSql.setStartSize((currentPage-1)*pageSize);
			count = dataAnalysisService.queryCustomSqlCount(customSql);
			bodyList = dataAnalysisService.queryCustomSql(customSql);
			Page page = new Page(bodyList, pageSize,count,currentPage);
			map.put("page", page);
			map.put("msg", "查询成功");
		} catch (Exception e) {
			map.put("msg", "1");
			LOG.error("查询自定义Sql错误", e);
			return map;
		}finally {
            if (bodyList != null) {
            	try {
//            		String sql = sqlSessionFactory.getConfiguration().getMappedStatement("com.unicomlabs.das.virtualization.dao.content.CustomSqlDo.selectByCustomSql").getBoundSql(customSql).getSql();
            		String sql = getSqlContent(customSql);
        			sqlmap.put("name", inVo.getDataName());
        			sqlmap.put("sqlContent", sql);
        			sqlmap.put("tableName", customSql.getTableName());
        			sqlmap.put("status", 1);
        			sqlmap.put("userAccount", userAccount);
        			dataAnalysisService.insertCustomDataLog(sqlmap);
        			sqlId=dataAnalysisService.selectCustomSqlByName(inVo.getDataName());
            	}catch (Exception e) {
                	LOG.error("记录自定义Sql日志出现错误", e);
                }
            }else{
            	try {
//            		String sql = sqlSessionFactory.getConfiguration().getMappedStatement("com.unicomlabs.das.virtualization.dao.content.CustomSqlDo.selectByCustomSql").getBoundSql(customSql).getSql();
            		String sql = getSqlContent(customSql);
        			sqlmap.put("name", inVo.getDataName());
        			sqlmap.put("sqlContent", sql);
        			sqlmap.put("tableName", customSql.getTableName());
        			sqlmap.put("status", 2);
        			sqlmap.put("userAccount", userAccount);
        			dataAnalysisService.insertCustomDataLog(sqlmap);
        			sqlId=dataAnalysisService.selectCustomSqlByName(inVo.getDataName());
                } catch (Exception e) {
                	LOG.error("记录自定义Sql日志出现错误", e);
                }
            }
        }
		map.put("sqlId", sqlId);
		map.put("dataName", inVo.getDataName());
		map.put("titleList", inVo.getCustomSql().getTargetColumnExpressionList());
		LOG.info("执行定制数据结束");
		return map;
    }
    
    @RequestMapping("/executeCustomDataBySql.do")
    public @ResponseBody Map<String, Object> executeCustomDataBySql(HttpServletRequest request)throws Exception {
    	LOG.info("开始执行定制数据");
		//定义返回对象
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		Map<String, Object> sqlmap = new HashMap<String, Object>();
		int sqlId=0;
		List<Map<String, Object>> bodyList = null;
		List<Map<String, Object>> titleList = new ArrayList<Map<String, Object>>();
		int count = 0;
		String sqlContent = request.getParameter("sqlContent");
		String name = request.getParameter("name");
		String currentP = request.getParameter("currentPage");
		String pageS = request.getParameter("pageSize");
		int currentPage = Integer.valueOf(currentP);
		int pageSize = Integer.valueOf(pageS);
		User user = UserUtil.getCurrentUser();
		String userAccount = user.getUsername();
		try {
			contentMap.put("sqlContent", sqlContent);
			contentMap.put("startSize", (currentPage-1)*pageSize);
			contentMap.put("limitSize", pageSize);
			count = dataAnalysisService.selectItemListCount(sqlContent);
			bodyList = dataAnalysisService.selectItemList(contentMap);
			Page page = new Page(bodyList, pageSize,count,currentPage);
			map.put("page", page);
			map.put("msg", "查询成功");
			if(null!=bodyList&&bodyList.size()>0){
				Map<String, Object> map1=bodyList.get(0);
				for(java.util.Map.Entry<String, Object> entry : map1.entrySet()) {
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("asName", entry.getKey());
					map2.put("chineseName", entry.getKey());
					titleList.add(map2);
				}
			}
		} catch (Exception e) {
			map.put("msg", "1");
			LOG.error("查询自定义Sql错误", e);
			return map;
		}finally {
            if (bodyList != null) {
            	try {
        			sqlmap.put("name", name);
        			sqlmap.put("sqlContent", sqlContent);
        			sqlmap.put("tableName", "非定制表");
        			sqlmap.put("status", 1);
        			sqlmap.put("userAccount", userAccount);
        			dataAnalysisService.insertCustomDataLog(sqlmap);
        			sqlId=dataAnalysisService.selectCustomSqlByName(name);
            	}catch (Exception e) {
                	LOG.error("记录自定义Sql日志出现错误", e);
                }
            }else{
            	try {
            		sqlmap.put("name", name);
        			sqlmap.put("sqlContent", sqlContent);
        			sqlmap.put("tableName", "非定制表");
        			sqlmap.put("status", 2);
        			sqlmap.put("userAccount", userAccount);
        			dataAnalysisService.insertCustomDataLog(sqlmap);
        			sqlId=dataAnalysisService.selectCustomSqlByName(name);
                } catch (Exception e) {
                	LOG.error("记录自定义Sql日志出现错误", e);
                	return map;
                }
            }
        }
		map.put("sqlId", sqlId);
		map.put("dataName", name);
		map.put("titleList", titleList);
		LOG.info("执行定制数据结束");
		return map;
    }
    @RequestMapping("/selectSqlContentById.do")
    public @ResponseBody Map<String, Object> selectSqlContentById(HttpServletRequest request) throws Exception {
    	LOG.info("开始执行保存维度信息");
		//定义返回对象
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<CustomDataLog> sql = dataAnalysisService.selectSqlContent(Integer.valueOf(id));
			if(sql.size()>0){
				CustomDataLog sqlLog=sql.get(0);
				map.put("sqlContent", sqlLog.getSqlContent());
				map.put("id", sqlLog.getId());
				map.put("name", sqlLog.getName());
				map.put("msg", "读取成功");
			}
		} catch (Exception e) {
			map.put("msg", "1");
			LOG.error("保存维度信息出错", e);
			return map;
		}
		LOG.info("保存维度信息成功");
		return map;
    }
    @RequestMapping("/executeCustomDataByEdit.do")
    public @ResponseBody Map<String, Object> executeCustomDataByEdit(HttpServletRequest request)throws Exception {
    	LOG.info("开始执行定制数据");
		//定义返回对象
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		List<Map<String, Object>> bodyList = null;
		List<Map<String, Object>> titleList = new ArrayList<Map<String, Object>>();
		int count = 0;
		String sqlContent = request.getParameter("sqlContent");
		String name = request.getParameter("name");
		int currentPage = Integer.valueOf(request.getParameter("currentPage"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		try {
			contentMap.put("sqlContent", sqlContent);
			contentMap.put("startSize", (currentPage-1)*pageSize);
			contentMap.put("limitSize", pageSize);
			count = dataAnalysisService.selectItemListCount(sqlContent);
			bodyList = dataAnalysisService.selectItemList(contentMap);
			Page page = new Page(bodyList, pageSize,count,currentPage);
			map.put("page", page);
			map.put("msg", "查询成功");
			if(null!=bodyList&&bodyList.size()>0){
				Map<String, Object> map1=bodyList.get(0);
				for(java.util.Map.Entry<String, Object> entry : map1.entrySet()) {
					Map<String, Object> map2=new HashMap<String, Object>();
					map2.put("asName", entry.getKey());
					map2.put("chineseName", entry.getKey());
					titleList.add(map2);
				}
			}
		} catch (Exception e) {
			LOG.error("查询自定义Sql错误", e);
			map.put("msg", "1");
			return map;
		}
		map.put("dataName", name);
		map.put("titleList", titleList);
		LOG.info("执行定制数据结束");
		return map;
    }
    
    @RequestMapping("/updateDimension.do")
    public @ResponseBody Map<String, Object> updateDimension(HttpServletRequest request) throws Exception {
    	LOG.info("开始执行保存维度信息");
		//定义返回对象
		String xCode = request.getParameter("xCode");
		String tChineseList = request.getParameter("tChineseList");
		String sqlId = request.getParameter("sqlId");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("dimension", xCode);
			map.put("indexArray", tChineseList);
			map.put("id", sqlId);
			map.put("status", 3);
			dataAnalysisService.updateDataDimension(map);
		} catch (Exception e) {
			LOG.error("保存维度信息出错", e);
		}
		LOG.info("保存维度信息成功");
		return map;
    }
    
    @RequestMapping("/updateDelete.do")
    public @ResponseBody Map<String, Object> updateDelete(HttpServletRequest request) throws Exception {
    	LOG.info("开始执行保存维度信息");
		//定义返回对象
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("id", id);
			map.put("status", 0);
			dataAnalysisService.updateDelete(map);
		} catch (Exception e) {
			LOG.error("保存维度信息出错", e);
		}
		LOG.info("保存维度信息成功");
		return map;
    }
    
    @GetMapping("/findCustomSql.do")
	public @ResponseBody PageTableResponse findCustomSql(PageTableRequest request) {
    	PageTableResponse t = new PageTableHandler(new CountHandler() {
			@Override
			public int count(PageTableRequest request) {
				int count =0;
				try{
					Map<String, Object> map = new HashMap<String, Object>();
            		User user = UserUtil.getCurrentUser();
            		String userAccount = user.getUsername();
					if(userAccount.equals("das")||userAccount.equals("")){
						map.put("userAccount", "");
					}else{
						map.put("userAccount", userAccount);
					}
					count = dataAnalysisService.selectCustomSqlCount(map);
				}catch(Exception e){
					LOG.error("查询定制数据记录失败", e);
				}
				return count;
			}
		}, new ListHandler() {
			@Override
			public List<CustomDataLog> list(PageTableRequest request) {
				List<CustomDataLog> list =new ArrayList<CustomDataLog>();
				Map<String, Object> map = new HashMap<String, Object>();
				int offset=request.getStart();
				int limit=request.getLength();
				try{
					map.put("offset", offset);
					map.put("limit", limit);
            		User user = UserUtil.getCurrentUser();
            		String userAccount = user.getUsername();
					if(userAccount.equals("das")||userAccount.equals("")){
						map.put("userAccount", "");
					}else{
						map.put("userAccount", userAccount);
					}
					list = dataAnalysisService.selectCustomSql(map);
				}catch(Exception e){
					LOG.error("查询定制数据记录失败", e);
				}
				return list;
			}
		}).handle(request);
		return t;
	}
    public String getSqlContent(CustomSql customSql) throws Exception {
    	String sql="SELECT ";
    	if(customSql.getDistinct()){
    		sql+="DISTINCT ";
    	}
    	sql+="0 ";
    	if(customSql.getTargetColumnExpressionList().size()>0){
    		for(int i=0;i<customSql.getTargetColumnExpressionList().size();i++){
    			TargetColumnExpression t = customSql.getTargetColumnExpressionList().get(i);
    			sql+=" , "+t.getColumnExpression() + " AS " + t.getAsName();
    		}
    	}
    	sql+=" FROM "+customSql.getDbName()+"."+customSql.getTableName()+" WHERE 1=1 ";
    	if(customSql.getConditionalExpressionList().size()>0){
    		for(int i=0;i<customSql.getConditionalExpressionList().size();i++){
    			ConditionalExpression c = customSql.getConditionalExpressionList().get(i);
    			sql+=c.getMultipleCondition()+" "+c.getName()+" "+c.getPredicate()+" "+c.getValue();
    		}
    	}
    	if(customSql.getGroup()!=null){
    		if(customSql.getGroup().getByNameList()!=null && customSql.getGroup().getByNameList().size()>0){
    			sql+=" GROUP BY ";
    			for(int i=0;i<customSql.getGroup().getByNameList().size();i++){
    				sql+=customSql.getGroup().getByNameList().get(i);
    				if(i!=customSql.getGroup().getByNameList().size()-1){
    					sql+=" , ";
    				}
    			}
    		}
    		if(customSql.getGroup().getConditionalExpressionList()!=null && customSql.getGroup().getConditionalExpressionList().size()>0){
    			sql+=" HAVING 1=1 ";
    			for(int i=0;i<customSql.getGroup().getConditionalExpressionList().size();i++){
    				ConditionalExpression c = customSql.getGroup().getConditionalExpressionList().get(i);
    				sql+=c.getMultipleCondition() +" "+ c.getName() +" "+ c.getPredicate() +" "+ c.getValue();
    			}
    		}
    	}
    	if(customSql.getOrderList()!=null && customSql.getOrderList().size()>0){
    		sql+=" ORDER BY ";
    		for(int i=0;i<customSql.getOrderList().size();i++){
				sql+=customSql.getOrderList().get(i).getByName() +" "+ customSql.getOrderList().get(i).getOrder();
				if(i!=customSql.getOrderList().size()-1){
					sql+=" , ";
				}
			}
    	}
    	sql+=" LIMIT " + customSql.getStartSize() +" , "+ customSql.getLimitSize();
    	return sql;
    }
    /**
     * 格式化条件表达式类型
     * @param customSql
     */
//    private void formatConditionalExpressionType(CustomSql customSql) {
//    	//XXX 有待优化，因为现在只应用到String，Integer，Double类型，所以其他类型未添加 
//    	if (customSql != null) {
//        	//处理条件表达式
//        	if (customSql.getConditionalExpressionList() != null) {
//        		for (ConditionalExpression conditionalExpression : customSql.getConditionalExpressionList()) {
//        			if ("Integer".equals(conditionalExpression.getJavaType())) {
//        				conditionalExpression.setValue(Integer.parseInt((String) conditionalExpression.getValue()));
//        			}else if ("Double".equals(conditionalExpression.getJavaType())) {
//        				conditionalExpression.setValue(Double.parseDouble((String) conditionalExpression.getValue()));
//        			}
//        		}
//        	}
//        	//处理分组条件表达式
//        	if (customSql.getGroup() != null && customSql.getGroup().getConditionalExpressionList() != null) {
//        		for (ConditionalExpression conditionalExpression : customSql.getGroup().getConditionalExpressionList()) {
//        			if ("Integer".equals(conditionalExpression.getJavaType())) {
//        				conditionalExpression.setValue(Integer.parseInt((String) conditionalExpression.getValue()));
//        			}else if ("Double".equals(conditionalExpression.getJavaType())) {
//        				conditionalExpression.setValue(Double.parseDouble((String) conditionalExpression.getValue()));
//        			}
//        		}
//        	}
//    	}
//
//    }
	
}
