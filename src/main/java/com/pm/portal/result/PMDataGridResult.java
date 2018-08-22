package com.pm.portal.result;

import java.io.Serializable;
import java.util.List;

public class PMDataGridResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private T rows; //fastjson不要出现List<?> 会报�?
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public T getRows() {
		return rows;
	}
	public void setRows(T rows) {
		this.rows = rows;
	}
	
	
}
