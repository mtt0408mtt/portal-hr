package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgRkmxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sjJgRkmx")
public class SjJgRkmxController {
    private final SjJgRkmxClient sjJgRkmxClient;

    @Autowired
    public SjJgRkmxController(SjJgRkmxClient sjJgRkmxClient) {
        this.sjJgRkmxClient = sjJgRkmxClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String data = sjJgRkmxClient.list(vars);
            if ("sjJgRkmxFail".equals(data)) {
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

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(HttpServletResponse response, String rkid, Integer wpbh, BigDecimal rksl, Integer wpdw, String wpph, Date scrq, Date sxrq, Integer kwbm, String wpgg, String satConditions, String quality) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("rkid", rkid);
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            vars.put("wpbh", wpbh);
            vars.put("rksl", rksl);
            vars.put("wpdw", wpdw);
            vars.put("wpph", wpph);
            vars.put("scrq", scrq);
            vars.put("sxrq", sxrq);
            vars.put("kwbm", kwbm);
            vars.put("wpgg", wpgg);
            vars.put("satConditions", satConditions);
            vars.put("quality", quality);
            result.put("message", sjJgRkmxClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, String id, Integer wpbh, BigDecimal rksl, Integer wpdw, String wpph, Date scrq, Date sxrq, Integer kwbm, String wpgg, String satConditions, String quality) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("wpbh", wpbh);
            vars.put("rksl", rksl);
            vars.put("wpdw", wpdw);
            vars.put("wpph", wpph);
            vars.put("scrq", scrq);
            vars.put("sxrq", sxrq);
            vars.put("kwbm", kwbm);
            vars.put("wpgg", wpgg);
            vars.put("satConditions", satConditions);
            vars.put("quality", quality);
            result.put("message", sjJgRkmxClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            result.put("message", sjJgRkmxClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgRkmxRemoveFail");
        }
        return result;
    }
}
