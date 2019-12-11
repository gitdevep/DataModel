package com.unicomlabs.das.virtualization.page.table;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页查询参数
 * 
 * @author 小威老师 xiaoweijiagou@163.com
 *
 */
public class PageTableRequest implements Serializable {

	private static final long serialVersionUID = 7328071045193618467L;

	private Integer offset;
	private Integer limit;
	private Integer start;
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	private Integer length;
	private Map<String, Object> params;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
