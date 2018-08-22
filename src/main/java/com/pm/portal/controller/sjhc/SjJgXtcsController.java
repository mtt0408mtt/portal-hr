package com.pm.portal.controller.sjhc;

import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.service.sjhc.SjJgXtcsClient;
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
@RequestMapping(value = "/sjJgXtcs")
public class SjJgXtcsController {
    private final SjJgXtcsClient sjJgXtcsClient;

    @Autowired
    public SjJgXtcsController(SjJgXtcsClient sjJgXtcsClient) {
        this.sjJgXtcsClient = sjJgXtcsClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        vars.put("admin",admin);
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgXtcsClient.list(vars);
            if ("sjJgXtcsFail".equals(data)) {
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
    public Object insert(HttpServletResponse response, String xtcs, String csms, String csz, String mrz, String bz, String cslb, Integer ckbh,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("admin",admin);
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            vars.put("xtcs", xtcs);
            vars.put("csms", csms);
            vars.put("csz", csz);
            vars.put("mrz", mrz);
            vars.put("bz", bz);
            vars.put("cslb", cslb);
            vars.put("ckbh", ckbh);
            result.put("message", sjJgXtcsClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer id, String xtcs, String csms, String csz, String mrz, String bz, String cslb,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("admin",admin);
            vars.put("id", id);
            vars.put("xtcs", xtcs);
            vars.put("csms", csms);
            vars.put("csz", csz);
            vars.put("mrz", mrz);
            vars.put("bz", bz);
            vars.put("cslb", cslb);
            result.put("message", sjJgXtcsClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, Integer id,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("admin",admin);
            vars.put("id", id);
            result.put("message", sjJgXtcsClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgXtcsRemoveFail");
        }
        return result;
    }
}
