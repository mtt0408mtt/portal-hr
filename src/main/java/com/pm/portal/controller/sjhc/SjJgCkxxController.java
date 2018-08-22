package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgCkxxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/sjJgCkxx")
public class SjJgCkxxController {
    private final SjJgCkxxClient sjJgCkxxClient;

    @Autowired
    public SjJgCkxxController(SjJgCkxxClient sjJgCkxxClient) {
        this.sjJgCkxxClient = sjJgCkxxClient;
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object tree(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            String data = sjJgCkxxClient.tree(vars);
            if ("sjJgCkxxTreeFail".equals(data)) {
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

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("zfpb", "0");
            String data = sjJgCkxxClient.list(vars);
            if ("sjJgCkxxListFail".equals(data)) {
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
    public Object insert(HttpServletResponse response, String cklb, String ckmc, Integer sjck) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("cklb", cklb);
            vars.put("ckmc", ckmc);
            vars.put("zfpb", "0");
            vars.put("sjck", sjck);
            vars.put("pdpb", "0");
            result.put("message", sjJgCkxxClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer ckbh, String ckmc) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckbh", ckbh);
            vars.put("ckmc", ckmc);
            result.put("message", sjJgCkxxClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object disable(HttpServletResponse response, Integer ckbh, String zfpb) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("ckbh", ckbh);
            vars.put("zfpb", "1".equals(zfpb) ? "0" : "1");
            result.put("message", sjJgCkxxClient.disable(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgCkxxCancelFail");
        }
        return result;
    }
}
