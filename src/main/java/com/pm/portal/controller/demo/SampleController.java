package com.pm.portal.controller.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pm.portal.access.AccessLimit;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.exception.MyException;
import com.pm.portal.exception.MyException2;
import com.pm.portal.redis.MhAdminKey;
import com.pm.portal.redis.RedisService;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.base.MhAdminService;



@Controller
public class SampleController {
	@Autowired
	private MhAdminService adminService;

	@Autowired
	RedisService redisService;
	
//	@Autowired
//	MQSender sender;

	@RequestMapping("/home")
	@ResponseBody
	Result<String> home() {
		return Result.success("hello world");
	}

	@RequestMapping("/hello")
	@ResponseBody
	Result<String> hello() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}

	// 测试thymeleaf
	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "joshua");
		return "hello";
	}

//	@RequestMapping("/")
//	public String index(Model model) {
//		return "index";
//	}

	// 测试db
	@RequestMapping("/db/get")
	@ResponseBody
	@AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Result<MhAdmin> dbGet(MhAdmin other) {
//		if(other==null){
//			return Result.error(CodeMsg.MHADMIN_NOT_EXIST);
//		}
		//int i=1/0;
		throw new MyException2(CodeMsg.SERVER_ERROR); //{"msg":"服务端异常","code":500100,"url":"http://localhost:8081/db/get"}
		//MhAdmin admin = adminService.getByCode("admin");
		//return Result.success(admin);
	}

	// 测试事务
	@RequestMapping("/db/tx")
	@ResponseBody
	public Result<Boolean> dbTx() {
		//adminService.tx();
		return Result.success(true);
	}

	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<MhAdmin> redisGet() {
		MhAdmin admin = redisService.get(MhAdminKey.getById_c, "123456", MhAdmin.class);
		return Result.success(admin);
	}

//	@RequestMapping("/redis/set")
//	@ResponseBody
//	public Result<Boolean> redisSet() {
//		MhAdmin admin = new MhAdmin();
//		admin.setId(123456L);
//		admin.setName("1111");
//		redisService.set(MhAdminKey.getById_c, admin.getId()+"", admin);
//		return Result.success(true);
//	}

//	@RequestMapping("/mq")
//	@ResponseBody
//	public Result<String> mq() {
//		sender.send("hello,imooc");
//		return Result.success("Hello，world");
//	}

}
