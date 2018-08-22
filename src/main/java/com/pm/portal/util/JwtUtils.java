package com.pm.portal.util;

import java.util.Date;


import com.pm.portal.domain.base.PMAIAdmin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
	  public static final String SUBJECT = "xmtclass";

	    public static final long EXPIRE = 1000*60*60*24*7;  //过期时间，毫秒，一周

	    //秘钥
	    public static final  String APPSECRET = "xmt666";

	    /**
	     * 生成jwt
	     * @param user
	     * @return
	     */
	    public static String geneJsonWebToken(PMAIAdmin user){

	        if(user == null || user.getId() == null || user.getNickname() == null
	                	//|| user.getHeadImg()==null
	                ){
	            return null;
	        }
	        String token = Jwts.builder().setSubject(SUBJECT)
	                .claim("id",user.getId())
	                .claim("name",user.getNickname())
	                .claim("img",user.getHeadImg())
	                .setIssuedAt(new Date())//发型时间
	                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE)) //加入过期时间  就变了
	                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();//压缩下

	        return token;
	    }


	    /**
	     * 校验token
	     * @param token
	     * @return
	     */
	    public static Claims checkJWT(String token ){

	        try{
	            final Claims claims =  Jwts.parser().setSigningKey(APPSECRET).
	                    parseClaimsJws(token).getBody();
	            return  claims;

	        }catch (Exception e){ }
	        return null;

	    }
	    
	    
	    //@Test
	    public void testGeneJwt(){

	    	PMAIAdmin user = new PMAIAdmin();
	        user.setId(999L);
	        user.setHeadImg("www.xdclass.net");
	        user.setNickname("xd");

	        String token = JwtUtils.geneJsonWebToken(user);
	        System.out.println(token);

	    }


	    //@Test
	    public void testCheck(){
            //多了个1
	        String token = "1eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6InhkIiwiaW1nIjoid3d3LnhkY2xhc3MubmV0IiwiaWF0IjoxNTI5NzYyMDczLCJleHAiOjE1MzAzNjY4NzN9.PNYfaeVA7UaaeUMOtpvxl9HRoj1ysdbK6weSzMGvfjc";
	        Claims claims = JwtUtils.checkJWT(token);
	        if(claims != null){
	            String name = (String)claims.get("nickName");
	            String img = (String)claims.get("img");
	            Long id =(Long) claims.get("id");
	            System.out.println(name);
	            System.out.println(img);
	            System.out.println(id);
	        }else{
	            System.out.println("非法token");
	        }


	    }
}
