package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgRkjlClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sjJgRkjl")
public class SjJgRkjlController {
    private final SjJgRkjlClient sjJgRkjlClient;

    @Autowired
    public SjJgRkjlController(SjJgRkjlClient sjJgRkjlClient) {
        this.sjJgRkjlClient = sjJgRkjlClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgRkjlClient.list(vars);
            if ("sjJgRkjlFail".equals(data)) {
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
    public Object insert(HttpServletResponse response, Integer rkfs, Integer ghcs, Integer toCkbh) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            vars.put("rkfs", rkfs);
            vars.put("ghcs", ghcs);
            vars.put("toCkbh", toCkbh);
            vars.put("lrr", 1);
            vars.put("lrsj", new Date());
            vars.put("djzt", "0");

            String data = sjJgRkjlClient.insert(vars);
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

    @RequestMapping(value = "/add2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert2(HttpServletResponse response, String ddmxJSON, Integer rkfs, Integer toCkbh) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            vars.put("rkfs", rkfs);
            vars.put("toCkbh", toCkbh);
            vars.put("lrr", 1);
            vars.put("lrsj", new Date());
            vars.put("djzt", "0");
            vars.put("ddmxJSON", ddmxJSON);
            result.put("message", sjJgRkjlClient.insert2(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, String rkid, String rkbz, String djzt) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("rkid", rkid);
            vars.put("rkbz", rkbz);
            vars.put("djzt", djzt);
            vars.put("lrr", 1);
            vars.put("lrsj", new Date());
            result.put("message", sjJgRkjlClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/examine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object examine(HttpServletResponse response, String rkid, String djzt) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("rkid", rkid);
            vars.put("djzt", djzt);
            vars.put("qrr", 1);
            vars.put("qrsj", new Date());
            result.put("message", sjJgRkjlClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object execute(HttpServletResponse response, String rkid, String djzt) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("rkid", rkid);
            vars.put("djzt", djzt);
            vars.put("rkr", 1);
            vars.put("rksj", new Date());
            result.put("message", sjJgRkjlClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, String rkid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("rkid", rkid);
            result.put("message", sjJgRkjlClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgRkjlRemoveFail");
        }
        return result;
    }
}
