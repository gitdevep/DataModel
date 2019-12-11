/**
 * com.chinauicom.research.iotoperation.system.reportforms.entity.QueryCustomReportFormInVo.java
 */
package com.unicomlabs.das.virtualization.utils;

import java.util.List;

/**
 * 自定义SQL实体
 * @author zhaich5
 * @since 2018/5/22
 *
 */
public class CustomSql {
	
	/** 去重复 */
	private boolean distinct = false;
	
	/** 目标列表达式 */
	private List<TargetColumnExpression> targetColumnExpressionList;
	
	/** 表名  */
	private String tableName;
	
	/** 条件表达式 */
	private List<ConditionalExpression> conditionalExpressionList;
	
	/** 分组 */
	private Group group;
	
	/** 排序 */
	private List<Order> orderList;

	public boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public List<TargetColumnExpression> getTargetColumnExpressionList() {
		return targetColumnExpressionList;
	}

	public void setTargetColumnExpressionList(List<TargetColumnExpression> targetColumnExpressionList) {
		this.targetColumnExpressionList = targetColumnExpressionList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ConditionalExpression> getConditionalExpressionList() {
		return conditionalExpressionList;
	}

	public void setConditionalExpressionList(List<ConditionalExpression> conditionalExpressionList) {
		this.conditionalExpressionList = conditionalExpressionList;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	int limitSize;
	int startSize;
	String dbName;
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public int getStartSize() {
		return startSize;
	}

	public void setStartSize(int startSize) {
		this.startSize = startSize;
	}

	public int getLimitSize() {
		return limitSize;
	}

	public void setLimitSize(int limitSize) {
		this.limitSize = limitSize;
	}
	
	
}
