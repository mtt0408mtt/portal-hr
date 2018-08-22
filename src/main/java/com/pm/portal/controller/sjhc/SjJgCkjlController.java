package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgCkjlClient;
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
@RequestMapping(value = "/sjJgCkjl")
public class SjJgCkjlController {
    private final SjJgCkjlClient sjJgCkjlClient;

    @Autowired
    public SjJgCkjlController(SjJgCkjlClient sjJgCkjlClient) {
        this.sjJgCkjlClient = sjJgCkjlClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            String data = sjJgCkjlClient.list(vars);
            if ("sjJgCkjlFail".equals(data)) {
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
    public Object insert(HttpServletResponse response, Integer ckfs, Integer fromCkbh, Integer toCkbh, Long lyks) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "32110010");
            vars.put("ckfs", ckfs);
            vars.put("fromCkbh", fromCkbh);
            vars.put("lrr", 1);
            vars.put("lrsj", new Date());
            vars.put("djzt", "0");

            vars.put("toCkbh", toCkbh);
            vars.put("lyks", lyks);
            if (lyks != null) vars.put("lyr", 1L);

            String data = sjJgCkjlClient.insert(vars);
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

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, String ckid, String ckbz) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckid", ckid);
            vars.put("ckbz", ckbz);
            vars.put("lrr", 1);
            vars.put("lrsj", new Date());
            result.put("message", sjJgCkjlClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/examine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object examine(HttpServletResponse response, String ckid, String djzt) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckid", ckid);
            vars.put("djzt", djzt);
            vars.put("qrr", 1);
            vars.put("qrsj", new Date());
            result.put("message", sjJgCkjlClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object execute(HttpServletResponse response, String ckid, String djzt) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckid", ckid);
            vars.put("djzt", djzt);
            vars.put("ckr", 1);
            vars.put("cksj", new Date());
            result.put("message", sjJgCkjlClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, String ckid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckid", ckid);
            result.put("message", sjJgCkjlClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgCkjlRemoveFail");
        }
        return result;
    }
}
