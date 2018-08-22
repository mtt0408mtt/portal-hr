package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgTmkcClient;
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
@RequestMapping(value = "/sjJgTmkc")
public class SjJgTmkcController {
    private final SjJgTmkcClient sjJgTmkcClient;

    @Autowired
    public SjJgTmkcController(SjJgTmkcClient sjJgTmkcClient) {
        this.sjJgTmkcClient = sjJgTmkcClient;
    }

    @RequestMapping(value = "/open", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object open(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgTmkcClient.open(vars);
            if ("fail".equals(data)) {
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

    @RequestMapping(value = "/openCancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object openCancel(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            result.put("message", sjJgTmkcClient.openCancel(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
}
