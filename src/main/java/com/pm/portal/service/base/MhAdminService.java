package com.pm.portal.service.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pm.portal.controller.demo.VPermission;
import com.pm.portal.dao.MhAdminDao;
import com.pm.portal.domain.portal.Area;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.domain.portal.MhAgency;
import com.pm.portal.domain.portal.MhDepartment;
import com.pm.portal.domain.portal.MhGroup;
import com.pm.portal.exception.GlobalException;
import com.pm.portal.redis.MhAdminKey;
import com.pm.portal.redis.RedisService;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.PMDataGridResult;
import com.pm.portal.result.Result;
import com.pm.portal.util.MD5Util;
import com.pm.portal.util.UUIDUtil;
import com.pm.portal.vo.LoginVo;
import com.pm.portal.vo.MhAdminVo;

import lombok.extern.slf4j.Slf4j;

/**
 * SERVICE 掉service 不要掉dao
 * 事务用transaction
 * @author caoba
 *
 */

@Service
@Slf4j
public class MhAdminService {
	
	
	public static final String COOKI_NAME_TOKEN = "token";
	
	public static final String COOKI_AREA = "area";
	

	
	@Autowired
	MhAdminDao adminDao;
	
	@Autowired
	RedisService redisService;
	

	
	public MhAdmin getByCode(String code) {
		//取缓存
		MhAdmin admin = redisService.get(MhAdminKey.getByCode_c, code, MhAdmin.class);
		if(admin != null) {
			return admin;
		}
		//取数据库
		 admin = adminDao.getByCode(code);
		 System.out.println(admin.getId_());
		if(admin != null) {
//			user.setPassword(""); //不请空加密密码 放入Redis
			redisService.set(MhAdminKey.getByCode_c, code, admin); 
		}
		return admin;
	}
	
	
	
	
	// http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
	public Object updatePassword(String token, String code, String formPass) {
		//取user
		MhAdmin admin = getByCode(code);
		if(admin == null) {
			return Result.error(CodeMsg.MHADMIN_NOT_EXIST);
		}
		//更新数据库
		MhAdmin toBeUpdate = new MhAdmin();
		toBeUpdate.setPwd_(MD5Util.formPassToDBPass(formPass, "4q3w2e1r"));
		adminDao.update(toBeUpdate);
		//处理缓存
//		redisService.delete(MhAdminKey.getByCode_c, code);
//		user.setPassword(toBeUpdate.getPassword());
//		redisService.set(PMAIAdminKey.token, token, user);
		return true;
	}


	public MhAdmin getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MhAdmin admin = redisService.get(MhAdminKey.token_c, token, MhAdmin.class);
		
		
		
		//延长有效期
		if(admin != null) {
			addCookie(response, token, admin,false);
			return admin;
		}
		
		return null;
	}
	

	public Object login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
		String code = loginVo.getCode();
		String formPass = loginVo.getPassword();
		
		MhAdmin admin = getByCode(code);
		if(admin == null) {
			return Result.error(CodeMsg.MHADMIN_NOT_EXIST);
		}
		//验证密码
		String dbPass = admin.getPwd_();
		String saltDB = "4q3w2e1r";
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		System.out.println("calcPass,"+calcPass);
		System.out.println("dbPass,"+dbPass);
		if(!calcPass.equals(dbPass)) {
			log.error("PASSWORD_ERROR");
			return Result.error(CodeMsg.MHADMIN_PASSWORD_ERROR);
		}
		
		//权限
//		String[] group = loginVo.getGroup().split(",");
//		String[] agency = loginVo.getAgency().split(",");
//		String[] department = loginVo.getDepartment().split(",");
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("code", code);
//		vars.put("groupId",group[0]);
//		vars.put("agencyId",agency[0]);
//		vars.put("departmentId",department[0]);
		
//		Integer haveRoleForGroup = adminDao.haveRoleForGroup(vars);
//		System.out.println("haveRoleForGroup-->"+haveRoleForGroup);
//		if(haveRoleForGroup==null || haveRoleForGroup<1){
//			return Result.error(CodeMsg.PORTAL_GROUP_IMPOWER);
//		}
		
		
//		Area area=new Area();
//		area.setCurrentAgencyId(agency[0]);
//		area.setCurrentDepartmentId(department[0]);
//		area.setCurrentGroupId(group[0]);
//		area.setCurrentAgencyCode(agency[1]);
//        area.setCurrentDepartmentCode(department[1]);  
//        area.setCurrentGroupCode(group[1]);
//		admin.setArea(area);
		
		
		//生成cookie
		String token= UUIDUtil.uuid();
		addCookie(response, token, admin,true);

		return Result.success(token);
	}
	
	private void addCookie(HttpServletResponse response, String token, MhAdmin admin,Boolean flag) {
		//更新admin的登录时间
//		admin.setLastLoginTime(new Date().getTime());
//		adminDao.updateLastLoginTime(admin);
//		user.setPassword("");
//		admin.setSalt("");
		if(flag){
			List<String> scanKeys = redisService.scanKeys("token_c");
			//删除多余的key
			for(int i=0;i<scanKeys.size();i++){
				System.out.println(scanKeys.get(i));
				String key=scanKeys.get(i);
				MhAdmin adm = redisService.get(key, MhAdmin.class);
				if(adm.getId_()==admin.getId_()){
					redisService.delete(key);
				}
				
			}
		}

		
		redisService.set(MhAdminKey.token_c, token, admin);
		Cookie cookieToken = new Cookie(COOKI_NAME_TOKEN, token);
		cookieToken.setMaxAge(MhAdminKey.token_c.expireSeconds()+(int)(Math.random()*100));
		cookieToken.setPath("/");
		
//		System.out.println(admin.getArea().toString());
//		Cookie cookieArea = new Cookie(COOKI_AREA, admin.getArea().toString());
//		cookieArea.setMaxAge(MhAdminKey.token_c.expireSeconds()+(int)(Math.random()*100));
//		cookieArea.setPath("/");
		
		response.addCookie(cookieToken);
//		response.addCookie(cookieArea);
		

	}
	public PMDataGridResult<List<MhAdminVo>> listAdminsVo() {
		PMDataGridResult<List<MhAdminVo>> result = new PMDataGridResult<List<MhAdminVo>>();
		result.setRows(adminDao.listAdminsVo());
		result.setTotal(adminDao.countAdminsVO());
		return result;
	}
	public void quit(HttpServletRequest request,HttpServletResponse response) {

		String paramToken = request.getParameter(MhAdminService.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request, MhAdminService.COOKI_NAME_TOKEN);
		String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
		if (!StringUtils.isEmpty(token)){
			redisService.delete(MhAdminKey.token_c, token);
		}
				
				
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
        response.addCookie(cookie);
        
//		Cookie cookieArea = new Cookie(COOKI_AREA, null);
//		cookieArea.setMaxAge(0);
//		cookieArea.setPath("/");
//      response.addCookie(cookieArea);
		
	}




	public List<MhGroup> group() {
				
		List<MhGroup> listGroup = adminDao.listGroup();
		for(int i=0;i<listGroup.size();i++){
			
			List<MhAgency> listAgency = adminDao.listAgency(listGroup.get(i).getId());
			listGroup.get(i).setAgency(listAgency);
			for(int j=0;j<listAgency.size();j++){
				List<MhDepartment> listDepartment = adminDao.listDepartment(listAgency.get(j).getId());
				listAgency.get(j).setDepartment(listDepartment);
			}
		}
		return listGroup;
		
		
	}
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(null!=cookies && cookies.length>0){
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookiName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	
	public List<VPermission> listSubs(Map<String, Object> vars){
		
		List<VPermission> listSubs = adminDao.listSubs(vars);
		
		return listSubs;
		
	}

}
