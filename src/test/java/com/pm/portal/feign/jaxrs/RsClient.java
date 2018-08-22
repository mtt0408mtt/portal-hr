package com.pm.portal.feign.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public interface RsClient {

	@GET
	@Path("/hello")
	public String hello();
}
