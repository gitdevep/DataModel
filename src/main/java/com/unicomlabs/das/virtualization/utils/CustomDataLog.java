package com.unicomlabs.das.virtualization.utils;

public class CustomDataLog {
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSqlContent() {
		return sqlContent;
	}
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getIndexArray() {
		return indexArray;
	}
	public void setIndexArray(String indexArray) {
		this.indexArray = indexArray;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	String name;
	String sqlContent;
	String tableName;
	String dimension;
	String indexArray;
	String status;
	String userAccount;
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}
