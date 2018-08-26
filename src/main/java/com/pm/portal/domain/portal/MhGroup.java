package com.pm.portal.domain.portal;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class MhGroup {
	private String id_;
	private Integer rev_;
	private String name_;
	private String type_;
	
	private List<MhAdmin> users;
	

}
