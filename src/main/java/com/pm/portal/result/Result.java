package com.pm.portal.result;


public class Result<T> {
	private int code;
	private String msg;
	private T data; 

	public Result(T data) {
		this.code=0;
		this.msg="success";
		this.data=data;

	}
	public Result() {
		this.code=0;
		this.msg="success";


	}
	public Result(CodeMsg cm) {
		if(cm ==null){
			return;
		}
		this.code=cm.getCode();
		this.msg=cm.getMsg();
	}
	/**
	 * 成功的时候
	 * @return
	 */
	public static <T> Result<T> success(T data){
		return new Result<T>(data);
	}
	/**
	 * 成功的时候 没有数据
	 * @return
	 */	
	public static <T> Result<T> ok(){
		return new Result<T>();
	}
	
	/**
	 * 失败的时候
	 * @return
	 */
	public static <T> Result<T> error(CodeMsg cm){
		return new Result<T>(cm);
	}

	public T getData() {
		return data;
	}


	public int getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}

}
