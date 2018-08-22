package com.pm.portal.result;

import com.pm.portal.result.CodeMsg;

public class CodeMsg {
	
	
	private int code;
	private String msg;
	
	//通用的错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
	public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "访问太频繁！");
	public static CodeMsg UPLOAD_ERROR = new CodeMsg(500105, "上传失败");
	public static CodeMsg UPLOAD_FILE_EMPTY = new CodeMsg(500105, "文件为空");
	//登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效,请重新登录");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
	
	//reportLisQuery 510XXX
	public static final CodeMsg REPROT_LIS_QUERY_ERROR= new CodeMsg(510101, "查询lis报表服务错误");
	public static final CodeMsg REPROT_LIS_QUERY_CATEGORY_PARAMS_NULL= new CodeMsg(510102, "条码号分类参数不能为空");
	public static final CodeMsg REPROT_LIS_QUERY_WHERE_PARAMS_NULL = new CodeMsg(510103, "时间参数不能为空");
	public static final CodeMsg REPROT_LIS_QUERY_WHERE_STATUS_PARAMS_NULL = new CodeMsg(510104, "条码状态不能为空");
	public static final CodeMsg REPROT_LIS_EXPORT_ERROR= new CodeMsg(510105, "export Excel service is not available");
	public static final CodeMsg REPROT_LIS_SERVER_ERROR= new CodeMsg(510101, "报表服务暂时不可用");
	
	//lis 申请单 520XXX
	public static final CodeMsg LIS_APPLE_ERROR=new CodeMsg(520001,"lis申请单操作失败");
	public static final CodeMsg LIS_SERVER_ERROR=new CodeMsg(520002,"lis服务暂时不可用");
	public static final CodeMsg LIS_DIC_ERROR=new CodeMsg(520003,"查询字典失败");
	public static final CodeMsg LIS_PARAMS_ERROR=new CodeMsg(520004,"lis申请单传参数错误");
	
	
	//mlis 手机单  530XXX
	public static final CodeMsg MLIS_APPLE_ERROR=new CodeMsg(530001,"手机申请单操作失败");
	public static final CodeMsg MLIS_SERVER_ERROR=new CodeMsg(530002,"手机单服务暂时不可用");
	public static final CodeMsg MLIS_DIC_ERROR=new CodeMsg(520003,"查询字典失败");
	public static final CodeMsg MLIS_PARAMS_ERROR=new CodeMsg(520004,"手机申请单传参数错误");
	
	//sjhc 手机单  540XXX
	public static final CodeMsg SJHC_REPORT_SERVER_ERROR=new CodeMsg(540002,"试剂耗材报表服务暂时不可用");
	public static final CodeMsg SJHC_REPORT_QUERY_PARAMS_NULL= new CodeMsg(540002, "请选择左上角参数和月份");
	
	//门户操作
	public static final CodeMsg PORTAL_OPT_SERVER_ERROR = new CodeMsg(550002, "门户操作暂时不可用");
	public static final CodeMsg PORTAL_OPT_PARAMS_ERROR=new CodeMsg(520004,"portal操作传参数错误");
	
	public static final CodeMsg MHADMIN_NOT_EXIST = new CodeMsg(520214, "用户名不存在");
	
	public static final CodeMsg PORTAL_GROUP_IMPOWER = new CodeMsg(520214, "没有权限进入");
	
	public static final CodeMsg MHADMIN_PASSWORD_ERROR = new CodeMsg(520214, "用户密码错误");
	public static final CodeMsg PORTAL_GROUP_ERROR = new CodeMsg(520215, "获取集团机构信息错误");
	
	//IDM操作
	public static final CodeMsg IDM_OPT_PARAMS_ERROR=new CodeMsg(530004,"idm操作传参数错误");
	
	
	private CodeMsg( ) {
	}
			
	private CodeMsg( int code,String msg ) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
	
}
