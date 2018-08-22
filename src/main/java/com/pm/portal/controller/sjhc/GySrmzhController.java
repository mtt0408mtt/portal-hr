package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.GySrmzhClient;
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
@RequestMapping(value = "/gySrmzh")
public class GySrmzhController {
    private final GySrmzhClient gySrmzhClient;

    @Autowired
    public GySrmzhController(GySrmzhClient gySrmzhClient) {
        this.gySrmzhClient = gySrmzhClient;
    }

    @RequestMapping(value = "/getCodeCN", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object codeCN(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String data = gySrmzhClient.codeCN(vars);
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
}
