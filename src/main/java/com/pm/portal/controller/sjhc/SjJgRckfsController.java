package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgRckfsClient;
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
@RequestMapping(value = "/sjJgRckfs")
public class SjJgRckfsController {
    private final SjJgRckfsClient sjJgRckfsClient;

    @Autowired
    public SjJgRckfsController(SjJgRckfsClient sjJgRckfsClient) {
        this.sjJgRckfsClient = sjJgRckfsClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            String data = sjJgRckfsClient.list(vars);
            if ("sjJgRckfsFail".equals(data)) {
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

    @RequestMapping(value = "/list_b", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list2(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("zfpb", "0");
            String data = sjJgRckfsClient.list2(vars);
            if ("sjJgRckfsFailB".equals(data)) {
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
    public Object insert(HttpServletResponse response, String lx, String czfs, String cgbs) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("lx", lx);
            vars.put("czfs", czfs);
            vars.put("cgbs", cgbs);
            vars.put("zfpb", "0");
            result.put("message", sjJgRckfsClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer id, String lx, String czfs, String cgbs) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("lx", lx);
            vars.put("czfs", czfs);
            vars.put("cgbs", cgbs);
            result.put("message", sjJgRckfsClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object disable(HttpServletResponse response, Integer id, String zfpb) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("zfpb", "1".equals(zfpb) ? "0" : "1");
            result.put("message", sjJgRckfsClient.disable(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgRckfsCancelFail");
        }
        return result;
    }
}
