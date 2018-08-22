package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgWpxxCsjgClient;
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
@RequestMapping(value = "/sjJgWpxxCsjg")
public class SjJgWpxxCsjgController {
    private final SjJgWpxxCsjgClient sjJgWpxxCsjgClient;

    @Autowired
    public SjJgWpxxCsjgController(SjJgWpxxCsjgClient sjJgWpxxCsjgClient) {
        this.sjJgWpxxCsjgClient = sjJgWpxxCsjgClient;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object list(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            String data = sjJgWpxxCsjgClient.list(vars);
            if ("sjJgWpxxCsjgFail".equals(data)) {
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
    public Object insert(HttpServletResponse response, Integer wpbh, Integer csid, String jhjg, String pfjg, String xsjg) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("wpbh", wpbh);
            vars.put("csid", csid);
            vars.put("jhjg", jhjg);
            vars.put("pfjg", pfjg);
            vars.put("xsjg", xsjg);
            vars.put("zfpb", "0");
            result.put("message", sjJgWpxxCsjgClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer id, Integer csid, String jhjg, String pfjg, String xsjg) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("csid", csid);
            vars.put("jhjg", jhjg);
            vars.put("pfjg", pfjg);
            vars.put("xsjg", xsjg);
            result.put("message", sjJgWpxxCsjgClient.update(vars));
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
            result.put("message", sjJgWpxxCsjgClient.disable(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgWpxxCsjgCancelFail");
        }
        return result;
    }
}
