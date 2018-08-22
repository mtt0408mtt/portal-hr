package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.LJgYqlbClient;
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
@RequestMapping(value = "/lJgYqlb")
public class LJgYqlbController {
    private final LJgYqlbClient lJgYqlbClient;

    @Autowired
    public LJgYqlbController(LJgYqlbClient lJgYqlbClient) {
        this.lJgYqlbClient = lJgYqlbClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = lJgYqlbClient.list(vars);
            if ("lJgYqlbFail".equals(data)) {
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
