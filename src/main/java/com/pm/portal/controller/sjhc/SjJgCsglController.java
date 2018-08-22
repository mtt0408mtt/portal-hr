package com.pm.portal.controller.sjhc;

import com.pm.portal.service.sjhc.SjJgCsglClient;
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
@RequestMapping(value = "/sjJgCsgl")
public class SjJgCsglController {
    private final SjJgCsglClient sjJgCsglClient;

    @Autowired
    public SjJgCsglController(SjJgCsglClient sjJgCsglClient) {
        this.sjJgCsglClient = sjJgCsglClient;
    }

    @RequestMapping(value = "/fuzzy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fuzzyList(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            String data = sjJgCsglClient.fuzzyList(vars);
            if ("sjJgCsglFail".equals(data)) {
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

    @RequestMapping(value = "/fuzzy_b", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fuzzyList2(@RequestBody Map<String, Object> vars, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("zfpb", "0");
            String data = sjJgCsglClient.fuzzyList2(vars);
            if ("sjJgCsglFailB".equals(data)) {
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
    public Object insert(HttpServletResponse response, String lx, String csmc, Integer qyxz, String sfsy, String lxdz, String lxdh, String lxr, String pym, String wbm) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("jtbh", "1");
            vars.put("jgid", "1");
            vars.put("lx", lx);
            vars.put("csmc", csmc);
            vars.put("qyxz", qyxz);
            vars.put("sfsy", sfsy);
            vars.put("lxdz", lxdz);
            vars.put("lxdh", lxdh);
            vars.put("lxr", lxr);
            vars.put("pym", pym);
            vars.put("wbm", wbm);
            vars.put("spzt", "1");
            vars.put("zfpb", "0");
            result.put("message", sjJgCsglClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response, Integer id, String lx, String csmc, Integer qyxz, String sfsy, String lxdz, String lxdh, String lxr, String pym, String wbm) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("lx", lx);
            vars.put("csmc", csmc);
            vars.put("qyxz", qyxz);
            vars.put("sfsy", sfsy);
            vars.put("lxdz", lxdz);
            vars.put("lxdh", lxdh);
            vars.put("lxr", lxr);
            vars.put("pym", pym);
            vars.put("wbm", wbm);
            result.put("message", sjJgCsglClient.update(vars));
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
            result.put("message", sjJgCsglClient.disable(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "sjJgCsglCancelFail");
        }
        return result;
    }

    @RequestMapping(value = "/examine", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object examine(HttpServletResponse response, Integer id, String spzt) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            vars.put("spzt", spzt);
            result.put("message", sjJgCsglClient.examine(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
}
