package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.MhJgKsdmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/mhJgKsdm")
public class MhJgKsdmController {
    private final MhJgKsdmClient mhJgKsdmClient;

    @Autowired
    public MhJgKsdmController(MhJgKsdmClient mhJgKsdmClient) {
        this.mhJgKsdmClient = mhJgKsdmClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("groupId", "1");
            vars.put("agencyId", "32110010");
            String data = mhJgKsdmClient.list(vars);
            if ("mhJgKsdmFail".equals(data)) {
                result.put("message", "fail");
            } else {
                result.put("message", "success");
                result.put("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
}
