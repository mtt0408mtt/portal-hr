package com.pm.portal.redis;


/**
 * 对象缓存永久 更新需要清缓存有多少清多少set多少
 * url 缓存 分页的缓存1两页
 * @author caoba
 *
 */
public class MhAdminKey extends BasePrefix{
	
	public static final int TOKEN_EXPIRE = 3600*24 * 2;

	private MhAdminKey(String prefix) {
		super(0, prefix);//没有过期时间 就是0
	}
	
	private MhAdminKey(int expireSeconds, String prefix) {
		super(TOKEN_EXPIRE, prefix);//设置过期时间
	}
	
	public static MhAdminKey token_c = new MhAdminKey(TOKEN_EXPIRE, "token_c");
	
//	public static MhAdminKey getById = new MhAdminKey("getById");
//	
//	public static MhAdminKey getByCode = new MhAdminKey("getByCode");
	
	public static MhAdminKey getById_c = new MhAdminKey(TOKEN_EXPIRE, "getById_c");
	
	public static MhAdminKey getByCode_c = new MhAdminKey(TOKEN_EXPIRE, "getByCode_c");
	
	
}
